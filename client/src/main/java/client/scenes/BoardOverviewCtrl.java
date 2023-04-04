package client.scenes;
import client.components.CardComponent;
import client.components.CardListComponent;
import client.utils.ServerUtils;
import client.utils.WebsocketClient;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.CardList;
import commons.Tag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListCell;
//import java.net.URL;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.prefs.Preferences;


public class BoardOverviewCtrl /*implements Initializable*/ {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final WebsocketClient websocketClient;
    public int boardID = 0;
    public CardComponent highlightedCardComponent;

    private boolean isCreatingCard = false;


    @FXML
    private VBox vboxList1;
    @FXML
    private SplitPane splitpane;


    @FXML
    private ScrollPane scrollPaneOverview;

    @FXML
    private VBox vboxTags;

    @FXML
    private HBox hboxCardLists;
    @FXML
    private Button btnTagManager;

    @FXML
    public ListView<Tag> listViewTags;

    @FXML
    private Button backbtn;
    @FXML
    private Button btncustomization;

    @FXML
    private Label boardKey;
    @FXML
    private Label boardKeyL;
    @FXML
    private Label tagL;

    @FXML
    private Button addListButton;

    @FXML Button editBoardButton;

    @FXML
    private Label labelBoardTitle;
    @FXML
    private Button lock;
    private Preferences pref;
    private boolean isLocked;


    /**
     *
     * @param server -
     * @param mainCtrl -
     * @param websocketClient -
     */
    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl, WebsocketClient websocketClient) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.isLocked=false;
        this.pref = Preferences.userRoot().node("locking");
        this.websocketClient = websocketClient;
        System.out.println("Inject called in board overview");
    }

//    /**
//     *
//     * @param location
//     * The location used to resolve relative paths for the root object, or
//     * {@code null} if the location is not known.
//     *
//     * @param resources
//     * The resources used to localize the root object, or {@code null} if
//     * the root object was not localized.
//     */
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        if (server.testConnection(ServerUtils.SERVER) != 200) {
//            System.out.println("No server to connect to, halting tag init function");
//            return;
//        }
//    }

    /**
     * Creates stomp session
     */
    public void setStompSession(){
        websocketClient.setStompSession(ServerUtils.SERVER);
        System.out.println("StompSession created in board overview");
    }

    /**
     * Subscribes to endpoint that listens to all updates of cards and lists from a specific board
     * @param boardID the boarId from the board we want updates from
     */
    public void subscribeToBoard(int boardID) {
        websocketClient.registerForMessages("/topic/boards/" + boardID, String.class, update -> {
            Platform.runLater(() -> {
                System.out.println("payload: " + update);
                if (update.contains("refreshnamecolor")) {
                    setBoardName();
                    setColor();
                } else if(update.contains("Added card")) {
                    String[] words = update.split(" ");
                    Card c = server.getCard(Integer.parseInt(words[2]));
                    System.out.println("refresh with focus to card#" + words[1]);
                    refresh(c, false);
                } else {
                    refresh(null, false);
                }
                //            mainCtrl.timeoutBoardRefresh(1000);
            });
        });
    }

    /**
     * Subscribes to endpoint that listens to all updates of tags from a specific board
     * @param boardID the boarId from the board we want updates from
     */
    public void subscribeToTagsFromBoard(int boardID){
        websocketClient.registerForMessages("/topic/tags/"+boardID, String.class, update -> {
                    System.out.println("payload: "+ update);
                    refreshListViewTags();
        });
    }

    /**
     * Sends message to /app/dest
     * @param dest destination to send message to
     * @param payload message to send
     */
    public void sendMessage(String dest, String payload){
        websocketClient.sendMessage(dest, payload);
    }

    /**
     * Sets board name in the overview
     */
    public void setBoardName(){
        Board board = server.getBoard(boardID);
        labelBoardTitle.setText(board.getName());
    }

    /**
     * Sets the color of the board
     */
    public void setColor(){
        Board board = server.getBoard(boardID);
        scrollPaneOverview.setStyle("-fx-background: " + String.format("rgb(%d, %d, %d)",
                (board.getbColor() >> 16) & 0xFF,
                (board.getbColor() >> 8) & 0xFF, board.getbColor()& 0xFF)+";");
        vboxList1.setStyle("-fx-background-color: " + String.format("rgb(%d, %d, %d)",
                (board.getbColor() >> 16) & 0xFF,
                (board.getbColor() >> 8) & 0xFF, board.getbColor()& 0xFF)+";");
        String hexColor = String.format("#%06X", (0xFFFFFF & board.getfColor()));
        labelBoardTitle.setStyle("-fx-text-fill: " + hexColor+";");
        boardKey.setStyle("-fx-text-fill: " + hexColor+";");
        boardKeyL.setStyle("-fx-text-fill: " + hexColor+";");
        tagL.setStyle("-fx-text-fill: " + hexColor+";");
    }

    /**
     * sets the colour of the button
     */
    public void setLock(){
        Board board = server.getBoard(boardID);
        if(board.getPassword().equals("") || board.getPassword()==null ){
            lock.setText("\uD83D\uDD13");
            isLocked=false;
            lock.setStyle("-fx-background-color: white");
            enable();
            return;
        }else{
            lock.setText("\uD83D\uDD12");
            checkForPref();
            if(pref.get(String.valueOf(boardID),"").equals("")){
                lock.setStyle("-fx-background-color: red");
                isLocked=true;
                disable();
            }
            else{
                isLocked=false;
                enable();
                lock.setStyle("-fx-background-color: green");
            }

        }

    }

    /**
     * Disables the write mode on the board
     */
    public void disable(){

        btnTagManager.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
            return;
        });
        btncustomization.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
            return;
        });
        editBoardButton.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
            return;
        });
        addListButton.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
            return;
        });

    }

    /**
     * Enables the write mode on the board
     */
    public void enable(){

        btnTagManager.setOnAction(event -> {
            showTagManager(event);
        });
        btncustomization.setOnAction(event -> {
            goCustomization(event);
        });
        editBoardButton.setOnAction(event -> {
            showEditBoard(event);
        });
        addListButton.setOnAction(event -> {
            addListScene(event);
        });

    }




    /**
     * Checks if the board is the correct board
     */
    public void checkForPref(){
        for(Board b :server.getBoards()){
            if(!pref.get(String.valueOf(b.getId()),"").equals("") &&
                    !pref.get(String.valueOf(b.getId()),"notfound").equals(b.getPassword())) {
                pref.remove(String.valueOf(b.getId()));
            }
        }
    }


    /**
     * Shows the tag manager scene
     * @param actionEvent the event when clicking the "tag manager" button58
     */
    public void showTagManager(ActionEvent actionEvent){
        System.out.println("showTagManger with id #" + boardID);
        mainCtrl.showTagManager(boardID);
    }

    /**
     * Shows the edit board scene
     * @param actionEvent the event when clicking the "edit board" button
     */
    public void showEditBoard(ActionEvent actionEvent){
        System.out.println("showEditBoard with id #" + boardID);
        mainCtrl.showEditBoard(boardID);
    }

    /**
     * Goes back to previous scene
     * @param actionEvent the event when clicking the back button
     */
    public void back(ActionEvent actionEvent){
        System.out.println("going back");
        mainCtrl.showSelect();
        websocketClient.unsubscribe("/topic/boards/"+boardID);
        websocketClient.unsubscribe("/topic/tags/"+boardID);
    }

//    /**
//     * Shows card creation scene
//     * @param actionEvent the event of clicking on a button
//     */
//    public void showCardAdd(ActionEvent actionEvent){
//        System.out.println("card");
//        mainCtrl.showCard();
//    }
    /**
     * displays cards in vboxes
     * @param vbox the vbox in the list where the cards need to be showed
     * @param listId the list we need to populate with cards
     * @param c card for focus
     */
    public void displayCards(VBox vbox, int listId, Card c){
        //List<Card> cards = server.getCardsFromList(listId);

        List<Card> cards = server.getCardsFromList(listId);
        cards.sort(Comparator.comparing(Card::getPosition));
        System.out.println("\n\n\n\n\n=======================================\n"+cards);
        for (Card card : cards) {
            CardComponent cardComponent = new CardComponent(mainCtrl, isLocked);
            mainCtrl.cardIdComponentMap.remove(card.getId());
            mainCtrl.cardIdComponentMap.remove(cardComponent);
            mainCtrl.cardIdComponentMap.put(card.getId(), cardComponent);
            cardComponent.boardID = boardID;
            cardComponent.setData(card, listId);
            cardComponent.getTagColors();
            if(isLocked) cardComponent.readmode();
            cardComponent.setStyle("-fx-background-color: " +
                    String.format("rgb(%d, %d, %d)", (card.getColor() >> 16) & 0xFF,
                    (card.getColor() >> 8) & 0xFF, card.getColor() & 0xFF)+";" );
            String hexColor = String.format("#%06X", (0xFFFFFF & card.getFcolor()));
            cardComponent.tfTitle.setStyle("-fx-text-fill: " + hexColor);
            cardComponent.descriptionLabel.setStyle("-fx-text-fill: " + hexColor);
            cardComponent.setTaskProgress();

            vbox.getChildren().add(cardComponent);

            if(card.equals(c)){
                cardComponent.tfTitle.requestFocus();
            }
        }
    }




    /**
     * gets the list of cardlists with serverutils
     * @return list of cardlists for a specific board
     */
    public List<CardList> getCardListsFromServer() {
        return server.getCardListsFromBoard(boardID);
    }

    /**
     * get cards for list
     * @param listId the parent list id
     * @return list of card elements
     */
    public List<Card> getCardsOfListFromServer(int listId) {
        return server.getCardsFromList(listId);
    }


    /**
     * displays cards in vboxes
     * @param cardLists card lists
     * @param c - card for focus
     * @param saveCardPositions whether to update the cards' positions in the database.
     *                          only true when called from the client (in drag dropped)
     */
    public void displayLists(List<CardList> cardLists, Card c, Boolean saveCardPositions){

        for (CardList cardList : cardLists) {

            CardListComponent cardListComponent = new CardListComponent(mainCtrl, boardID,cardList.getId());

            cardListComponent.setTitle(cardList.getName());
            cardListComponent.setStyle(String.format("-fx-background-color: rgb(%d, %d, %d);",
                    (cardList.getbColor() >> 16) & 0xFF,
                    (cardList.getbColor() >> 8) & 0xFF, cardList.getbColor()& 0xFF));
            System.out.println("List style: " + cardListComponent.getStyle());
            if(!isLocked) cardListComponent.setOnMouseEntered(event -> addEnterKeyListener(cardList.getId()));
            hboxCardLists.getChildren().add(cardListComponent);
            cardListComponent.setData(cardList);
            String hexColor = String.format("#%06X", (0xFFFFFF & cardList.getfColor()));
            cardListComponent.labelTitle.setStyle("-fx-text-fill: " + hexColor);
            displayCards(cardListComponent.getVboxCards(), cardList.getId(),c);
            if(isLocked) cardListComponent.readonly();
        }

    }


    /**
     * Creates an observable list with all tags
     * @param boardID the board id we are in
     * @return an observable list with all tags
     */
    public ObservableList<Tag> getTagList(int boardID){
        List<Tag> t = server.getTagsFromBoard(boardID);
        List<Tag> tags= new ArrayList<>();
        for(Tag tag : t){
            tags.add(tag);
        }
        ObservableList<Tag> tagList = (ObservableList<Tag>) FXCollections.observableList(tags);
        return tagList;
    }

    /**
     * Refreshes the list overview with the tags
     */
    public void refreshListViewTags(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                listViewTags.requestFocus();
                listViewTags.setItems(getTagList(boardID));
                listViewTags.setCellFactory(param -> new ListCell<Tag>() {
                    @Override
                    protected void updateItem(Tag tag, boolean empty) {
                        super.updateItem(tag, empty);
                        if (empty || tag == null || tag.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(tag.getTitle());
                            String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
                            setStyle("-fx-control-inner-background: " + hexColor);
                        }
                    }
                });
            }
        });

    }

    /**
     * Clears the board overview
     */
    public void clearBoard(){
        hboxCardLists.getChildren().clear();
    }

    /**
     * takes you to a scene to create a new list for the board
     * @param actionEvent - click
     */
    public void addList(ActionEvent actionEvent){
        System.out.println("going to list creation for board "+boardID);
        mainCtrl.showListCreate(boardID);
    }


    /**
     * refreshes the board's key (tag list view and name not anymore)
     * @param boardID
     */
    @FXML
    public void refreshName(int boardID){
        Board b = server.getBoard(boardID);
        //boardName.setText(b.getName());
        boardKey.setText(b.getBoardkey());

//        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        ObservableList<Tag> tagList = FXCollections.observableList(server.getTagsFromBoard(boardID));
//        list.setItems(tagList);
    }

    /**
     * display and copy to clipboard the boardKey
     */
    public void getBoardKey(){
            StringSelection selection = new StringSelection(boardKey.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
    }


    /**
     * Refresh scene from database
     * @param c - card for focus
     * @param saveCardPositions whether to save card position attributes to server
     */
    public void refresh(Card c, Boolean saveCardPositions) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                System.out.println("Refreshing board overview");
                List<CardList> cardLists = getCardListsFromServer();
                clearBoard();
                displayLists(cardLists, c, saveCardPositions);
                shortcut();
            }
        });
    }

    private void shortcut() {
        listViewTags.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                if (highlightedCardComponent != null)
                    mainCtrl.showCard(highlightedCardComponent.cardID, boardID,isLocked);
            } else if (event.getCode() == KeyCode.DELETE) {
                if (highlightedCardComponent != null) highlightedCardComponent.deleteCard();
            } else if (event.getCode() == KeyCode.T) {
                mainCtrl.showTagPopUp(this.boardID);
            } else if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }



    /**
     * refresh a specific list
     * @param listID the list id
     * @param listComponent the frontend component
     */
    public void refreshList(int listID, CardListComponent listComponent) {
        System.out.println("Refreshing list #"+listID);
        listComponent.getVboxCards().getChildren().clear();
    }

//    /**
//     * Refresh scene from database
//     * @param focusCard the card
//     */
//    public void refresh(Card focusCard) {
//        System.out.println("Refreshing board overview");
//        clearBoard();
//        displayListsWithFocus(getCardListsFromServer(), focusCard);
//    }

    /**
     * test method
     */
    public void createTestCard() {
        Card c = new Card("test card ..");
        System.out.println("creating test card "+c);
        server.addCard(c, 0, 0);
        //refresh();
        mainCtrl.timeoutBoardRefresh();
    }

    /**
     * create a new card in a list
     * @param listID the id of the list to create the card in
     * @return the card element
     */
    public Card createCard(int listID) {
        Card c = new Card("title..");
        //int size = server.getListSize(listID) + 1;
       // c.setPosition(99999);
        System.out.println("creating new card "+c+" in list id="+listID);

        c = server.addCard(c, boardID, listID);
        refresh(c, true);
        return c;
    }

    /**
     * event listener of the scrollpane in boardoverview for any key presses
     * checks if the key is the enter key and if it is it adds a card
     * @param event keyboard event
     * @param listID the list currently in
     */
    @FXML
    public void onEnterKeyPressed(KeyEvent event, int listID) {
        System.out.println("On enter key pressed called in boardoverviewcontroller with event "+event);
        if(isLocked) return;
        if (event.getCode() == KeyCode.ENTER && !isCreatingCard) {
            isCreatingCard = true;
            // when the user presses the enter button
            Card createdCardElement = createCard(listID);

        }
    }

    /**
     * event listener for enter key up,
     * allows for cards to be created once more.
     * used to prevent spamming cards
     * @param event keyboard event
     */
    @FXML
    public void onEnterKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && isCreatingCard) {
            isCreatingCard = false;
        }
    }

    /**
     * add the event listener to the scroll pane, specifying which list was last hovered over
     * @param listID the lists id
     */
    public void addEnterKeyListener(int listID) {
        System.out.println("add enter key listener called in Board Overview Controller");
        scrollPaneOverview.setOnKeyPressed(event -> onEnterKeyPressed(event, listID));
    }

    /**
     * Adds a list
     * @param event touching the add list button
     */
    public void addListScene(ActionEvent event){
        mainCtrl.showListCreate(boardID);
    }

    /**
     * clicking the lock button
     */
    public void clickLockInUnlockedBoard(){
        if(boardID==0){
            return;
        }
        if(server.getBoard(boardID).getPassword().equals("")){
            mainCtrl.showLockInUnlockedBoard(boardID);
        } else if (pref.get(String.valueOf(boardID),"notfound").equals("notfound")) {
            mainCtrl.showProvidePassword(boardID);
        } else {
            mainCtrl.showEditPassword(boardID);
        }

    }

    /**
     * takes you to customization scene
     * @param event
     */
    public void goCustomization(ActionEvent event){
        mainCtrl.showCustomization(boardID);
    }

    /**
     * sets color of font for board components
     * @param color - new color
     */
    @FXML
    public void colorFont(int color){
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        labelBoardTitle.setStyle("-fx-text-fill: " + hexColor);
        boardKey.setStyle("-fx-text-fill: " + hexColor);
        boardKeyL.setStyle("-fx-text-fill: " + hexColor);
        tagL.setStyle("-fx-text-fill: " + hexColor);
    }

    /**
     * tag manager button getter
     * @return Button
     */
    public Button getTagManagerButton() {
        return btnTagManager;
    }

    /**
     * back button getter
     * @return Button
     */
    public Button getBackButton() {
        return backbtn;
    }

    /**
     * customization button getter
     * @return Button
     */
    public Button getCustomizationButton() {
        return btncustomization;
    }

    /**
     * board key label getter
     * @return Label
     */
    public Label getBoardKeyLabel() {
        return boardKey;
    }

    /**
     * board label getter
     * @return Label
     */
    public Label getBoardLabel() {
        return boardKeyL;
    }

    /**
     * tag label getter
     * @return Label
     */
    public Label getTagLabel() {
        return tagL;
    }

    /**
     * the add list button getter
     * @return Button
     */
    public Button getAddListButton() {
        return addListButton;
    }

    /**
     * edit board button getter
     * @return Button
     */
    public Button getEditBoardButton() {
        return editBoardButton;
    }

    /**
     * board title label getter
     * @return Label
     */
    public Label getBoardTitleLabel() {
        return labelBoardTitle;
    }

    /**
    * lock button getter
    * @return Button
    */
    public Button getLockButton() {
        return lock;
    }
    
}
