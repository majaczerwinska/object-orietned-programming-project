package client.scenes;
import client.components.CardComponent;
import client.components.CardListComponent;
import client.services.BoardService;
import client.utils.ServerUtils;
import client.utils.WebsocketClient;
import com.google.inject.Inject;
import commons.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
//import java.net.URL;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Comparator;

import java.util.prefs.Preferences;


public class BoardOverviewCtrl {
    private final BoardService service;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final WebsocketClient websocketClient;
    public int boardID = 1;
    public CardComponent highlightedCardComponent;

    private boolean isCreatingCard = false;


    @FXML
    private VBox vboxList1;
    @FXML
    private SplitPane splitpane;


    @FXML
    public ScrollPane scrollPaneOverview;

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


    public int boardBackgroundColor;

    public int listsBackgroundColor;


    private boolean isAdmin;

    /**
     * @param service
     * @param server          -
     * @param mainCtrl        -
     * @param websocketClient -
     */
    @Inject
    public BoardOverviewCtrl(BoardService service, ServerUtils server,
                             MainCtrl mainCtrl, WebsocketClient websocketClient) {
        this.service = service;
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.isLocked=false;
        this.isAdmin=false;
        this.pref = Preferences.userRoot().node("locking");
        this.websocketClient = websocketClient;
        System.out.println("Inject called in board overview");
    }

    /**
     * Creates stomp session
     */
    public void setStompSession(){
        websocketClient.setStompSession(ServerUtils.SERVER);
        System.out.println("StompSession created in board overview");
    }

    /**
     * Disconnects the stomp session
     */
    public void disconnectStompSession(){
        websocketClient.disconnect();
    }

    /**
     * Subscribes to endpoint that listens to all updates of cards and lists from a specific board
     * @param boardID the boarId from the board we want updates from
     */
    public void subscribeToBoardUpdates(int boardID) {
        websocketClient.registerForMessages("/topic/boards/" + boardID, String.class, update -> {
            Platform.runLater(() -> {
                System.out.println("payload: " + update);
                if (update.contains("refreshnamecolor")) {
                    setBoardName();
                    setColor();
                    setLock();
                } else if(update.contains("Added card")) {
                    String[] words = update.split(" ");
                    Card c = server.getCard(Integer.parseInt(words[2]));
                    System.out.println("refresh with focus to card#" + words[1]);
                    refresh(c);
                } else {
                    refresh(null);
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
                    System.out.println("payload in board: "+ update);
                    refreshListViewTags();
        });
    }

    /**
     * Subscribes to endpoint that listens to the deletion of this board
     *
     * @param boardID the boarId from the board we want to listen for deletion
     */
    public void subscribeToListenForDeletes(int boardID){
        websocketClient.registerForMessages("/topic/boards/delete/"+boardID, String.class, update -> {
            System.out.println("payload in board: "+ update);
                Platform.runLater(() -> {
                    mainCtrl.showSelect();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Board deleted");
                    alert.setHeaderText("This board got deleted!");
                    alert.setContentText("You were viewing a board that someone just deleted!");
                    alert.show();
                });
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
        boardBackgroundColor = board.getbColor();
        String colour = String.format("rgb(%d, %d, %d)",
                (board.getbColor() >> 16) & 0xFF,
                (board.getbColor() >> 8) & 0xFF, board.getbColor()& 0xFF);
        String colourString = "-fx-background: " + colour +";";
        String colourStringList = "-fx-background-color: " + colour +";";
        mainCtrl.appendStyle(scrollPaneOverview, colourString);
        mainCtrl.appendStyle(vboxList1,colourStringList);
        String hexColor = String.format("#%06X", (0xFFFFFF & board.getfColor()));
        mainCtrl.appendStyle(labelBoardTitle,"-fx-text-fill: " + hexColor+";");
        mainCtrl.appendStyle(boardKey,"-fx-text-fill: " + hexColor+";");
        mainCtrl.appendStyle(boardKeyL,"-fx-text-fill: " + hexColor+";");
        mainCtrl.appendStyle(tagL,"-fx-text-fill: " + hexColor+";");
    }

    /**
     * sets the colour of the button
     */
    public void setLock(){
        if(service.checkLock(server, lock, isAdmin, boardID, pref)) {
            enable();
            isLocked=false;
        } else {
            disable();
            isLocked=true;
        }
    }

    /**
     * Disables the write mode on the board
     */
    public void disable(){
        btnTagManager.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
        });
        btncustomization.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
        });
        editBoardButton.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
        });
        addListButton.setOnAction(event -> {
            mainCtrl.showWarning(boardID);
        });
    }

    /**
     * Enables the write mode on the board
     */
    public void enable(){
        btnTagManager.setOnAction(this::showTagManager);
        btncustomization.setOnAction(this::goCustomization);
        editBoardButton.setOnAction(this::showEditBoard);
        addListButton.setOnAction(this::addListScene);
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
        websocketClient.unsubscribe("/topic/boards/delete/"+boardID);
    }


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
        System.out.println("\n=======================================\n"+cards);
        for (Card card : cards) {
            CardComponent cardComponent = new CardComponent(mainCtrl, isLocked);
            mainCtrl.cardIdComponentMap.remove(card.getId());
            mainCtrl.cardIdComponentMap.remove(cardComponent);
            mainCtrl.cardIdComponentMap.put(card.getId(), cardComponent);
            cardComponent.boardID = boardID;
            cardComponent.setData(card, listId);
            cardComponent.setColouredBorder();
            if(isLocked) cardComponent.readmode();
            mainCtrl.appendStyle(cardComponent, "-fx-background-color: " +
                    String.format("rgb(%d, %d, %d)", (card.getColor() >> 16) & 0xFF,
                    (card.getColor() >> 8) & 0xFF, card.getColor() & 0xFF)+";" );
            String hexColor = String.format("#%06X", (0xFFFFFF & card.getFcolor()));
            mainCtrl.appendStyle(cardComponent.tfTitle,"-fx-text-fill: " + hexColor + ";");
            mainCtrl.appendStyle(cardComponent.descriptionLabel,"-fx-text-fill: " + hexColor + ";");
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
     */
    public void displayLists(List<CardList> cardLists, Card c){

        for (CardList cardList : cardLists) {

            CardListComponent cardListComponent = new CardListComponent(mainCtrl, boardID,cardList.getId());

            cardListComponent.setTitle(cardList.getName());
            listsBackgroundColor = cardList.getbColor();
            mainCtrl.appendStyle(cardListComponent,String.format("-fx-background-color: rgb(%d, %d, %d);",
                    (cardList.getbColor() >> 16) & 0xFF,
                    (cardList.getbColor() >> 8) & 0xFF, cardList.getbColor()& 0xFF) + ";");
            System.out.println("List style: " + cardListComponent.getStyle());
            if(!isLocked) cardListComponent.setOnMouseEntered(event -> addEnterKeyListener(cardList.getId()));
            hboxCardLists.getChildren().add(cardListComponent);
            cardListComponent.setData(cardList);
            String hexColor = String.format("#%06X", (0xFFFFFF & cardList.getfColor()));
            mainCtrl.appendStyle(cardListComponent.labelTitle,"-fx-text-fill: " + hexColor + ";");
            displayCards(cardListComponent.getVboxCards(), cardList.getId(),c);
            if(isLocked) cardListComponent.readonly();
        }

    }




    /**
     * Refreshes the list overview with the tags
     */
    public void refreshListViewTags(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                listViewTags.requestFocus();
                listViewTags.setItems(service.getTagList(server, boardID));
                listViewTags.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(Tag tag, boolean empty) {
                        super.updateItem(tag, empty);
                        if (empty || tag == null || tag.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(tag.getTitle());
                            String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
                            setStyle("-fx-control-inner-background: " + hexColor + ";");
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
        boardKey.setText(b.getBoardkey());
        boardKey.setTextFill(Color.BLACK);
    }

    /**
     * display and copy to clipboard the boardKey
     */
    public void getBoardKey(){
        StringSelection selection = new StringSelection(boardKey.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        boardKey.setText("copied!");
        boardKey.setTextFill(Color.GREEN);
    }


    /**
     * Refresh scene from database
     * @param c - card for focus
     */
    public void refresh(Card c) {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                System.out.println("Refreshing board overview");
                List<CardList> cardLists = getCardListsFromServer();
                clearBoard();
                displayLists(cardLists, c);
                initialiseBoardShortcuts();
            }
        });
    }


    ///////////////////////////////////////////////////////////////////////
    //////////////////////    Keyboard shortcuts     //////////////////////


    @SuppressWarnings({"checkstyle:CyclomaticComplexity"})
    private void initialiseBoardShortcuts() {
        listViewTags.getScene().setOnKeyPressed(event -> {
            System.out.println("Key event in board listener: "+ event);
            KeyCombination keyCombinationUP = new KeyCodeCombination(KeyCode.W, KeyCombination.SHIFT_ANY);
            switch (event.getCode()) {
                case E:
                    if (highlightedCardComponent != null)
                        mainCtrl.showCard(highlightedCardComponent.cardID,
                                highlightedCardComponent.cardListID , boardID, isLocked);
                    break;
                case DELETE:
                    if (highlightedCardComponent != null) highlightedCardComponent.deleteCard();
                    break;
                case T:
                    if (highlightedCardComponent != null)
                        mainCtrl.showTagPopUp(this.boardID, highlightedCardComponent.cardID);
                    break;
                case SLASH:
                    mainCtrl.showHelpScene();
                    break;
                case C:
                    if (highlightedCardComponent != null)
                        mainCtrl.showColorPopUp(this.boardID, highlightedCardComponent.cardID);
                    break;


                case W:
                    if (event.getText().equals("W")) {
                        System.out.println("\n\n\nswapping up\n\n\n");
                        if (highlightedCardComponent != null) swapHighlight(true, true);
                        break;
                    }
                case UP:
                case KP_UP:
                case PAGE_UP:
                    System.out.println("\n\n\nmoving highlight up\n\n\n");
                    if (highlightedCardComponent != null) swapHighlight(true, false);
                    break;


                case S:
                    if (event.getText().equals("S")) {
                        if (highlightedCardComponent != null) swapHighlight(false, true);
                        break;
                    }
                case DOWN:
                case KP_DOWN:
                case PAGE_DOWN:
                    if (highlightedCardComponent != null) swapHighlight(false, false);
                    break;
            }
        });
    }

    /**
     * Gets the card for switching the highlight
     * @param moveUp This tells us if the highlight is switched up or down
     * @param swap whether to swap the two cards or not
     */
    public void swapHighlight(boolean moveUp, boolean swap) {
        System.out.println("swap highlight method::::");
        // get card instance from server
        Card card = server.getCard(highlightedCardComponent.cardID);
        int pos = card.getPosition();
        System.out.println(pos);
        // get list instance
        CardList cardList = null;
        for (CardList list : server.getCardListsFromBoard(boardID)) {
            if (list.getCards().contains(card)) cardList = list;
        } if (cardList == null) return;

        // get sorted List of cards
        List<Card> cards = cardList.getCards();
        cards.sort(Comparator.comparing(Card::getPosition));

        int newPosition = Math.min(Math.max(0, pos+((moveUp)?-1:1)), cards.size()-1);
        System.out.println(newPosition);
        if (cards.get(newPosition) == null) return;

        Card newCard = cards.get(newPosition);
        if (newCard == null) return;

        System.out.println("\033[55;49m Swapping card="+card+" with other="+newCard+" to position "+newPosition);

        if (swap) {
            int oldPos = card.getPosition();
            int newPos = newCard.getPosition();
            server.setPosition(card, newPos + ((moveUp)?0:1));
//            System.out.println(oldPos);
//            System.out.println(newPos);
            System.out.println("Card that was switched "+server.getCard(card.getId()).getPosition());
            System.out.println("Card that was switched with "+server.getCard(newCard.getId()).getPosition());
            refresh(null);
        } else {
            switchHighlight(cardList, newCard);
        }
    }

    private void switchHighlight(CardList cardList, Card newCard) {
        for (var t : hboxCardLists.getChildren()) {
            CardListComponent cardListComponent = (CardListComponent) t;
            if (cardListComponent.getListId()== cardList.getId()) {
                for (var p : cardListComponent.getVboxCards().getChildren()) {
                    CardComponent cc = (CardComponent) p;
                    if (cc.cardID== newCard.getId()) {
                        mainCtrl.removeStyle(highlightedCardComponent, "-fx-effect");
                        this.highlightedCardComponent = cc;
                        mainCtrl.appendStyle(highlightedCardComponent, "-fx-effect: dropshadow(gaussian, "+
                                mainCtrl.getRgbaColor(mainCtrl.getShadowColor(
                                        MainCtrl.colorParseToFXColor(listsBackgroundColor)))
                                +", 9, 0, 0, 3);");
                    }
                }
            }
        }
    }


 //////////////////////////

    /**
     * refresh a specific list
     * @param listID the list id
     * @param listComponent the frontend component
     */
    public void refreshList(int listID, CardListComponent listComponent) {
        System.out.println("Refreshing list #"+listID);
        listComponent.getVboxCards().getChildren().clear();
    }

    /**
     * create a new card in a list
     * @param listID the id of the list to create the card in
     * @return the card element
     */
    public Card createCard(int listID) {
        Card c = new Card("Untitled Card");
        List<Palette> palettes = server.getPalettesFromBoard(boardID);
        for(Palette pal : palettes){
            if(pal.isIsdefault()){
                c.setColor(pal.getbColor());
                c.setFcolor(pal.getfColor());
                break;
            }
        }
        System.out.println("creating new card "+c+" in list id="+listID);

        c = server.addCard(c, boardID, listID);
        refresh(c);
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
        if(boardID==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("This is the public board");
            alert.setHeaderText("Board cannot be locked!");
            alert.setContentText("Since this is the public board, it cannot be locked");
            alert.show();
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
        labelBoardTitle.setStyle("-fx-text-fill: " + hexColor + ";");
        boardKey.setStyle("-fx-text-fill: " + hexColor + ";");
        boardKeyL.setStyle("-fx-text-fill: " + hexColor + ";");
        tagL.setStyle("-fx-text-fill: " + hexColor + ";");
    }

    /**
     * tag manager button getter
     * @return Button
     */
    public Button getTagManagerButton() {
        return btnTagManager;
    }

    /**
     * set admin access rights
     * @param isAdmin bool
     */
    public void setAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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
