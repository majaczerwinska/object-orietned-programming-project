package client.scenes;

import client.components.CardComponent;
import client.components.CardListComponent;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.CardList;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import commons.Card;
import commons.CardList;


import commons.Board;
import commons.Card;
import commons.CardList;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//import java.net.URL;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
//import java.util.ResourceBundle;

public class BoardOverviewCtrl /*implements Initializable*/ {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int boardID = 0;

    private boolean isCreatingCard = false;


    @FXML
    private VBox vboxList1;


    @FXML
    private ScrollPane scrollPaneOverview;

    @FXML
    private VBox vboxTags;

    @FXML
    private HBox hboxCardLists;
    @FXML
    private Button btnTagManager;

    @FXML
    private ListView<Tag> listViewTags;

    @FXML
    private Button backbtn;

    @FXML
    private Label boardKey;
    @FXML
    private Button addListButton;

    @FXML
    private Button refreshButton;

    @FXML Button editBoardButton;

    @FXML
    private Label labelBoardTitle;


    /**
     *
     * @param server -
     * @param mainCtrl -
     */
    @Inject
    public BoardOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
//        Board board = this.server.getBoard(boardID);
//        String title = board.getName();
//        if(title == null){
//            boardName.setText("Board: " + board.getId());
//        }
//        else{
//            boardName.setText(board.getName());
//        }
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
//        displayCards();
//    }

    /**
     * Sets board name in the overview
     */
    public void setBoardName(){
        Board board = server.getBoard(boardID);
        labelBoardTitle.setText(board.getName());
    }

    /**
     * Shows the tag manager scene
     * @param actionEvent the event when clicking the "tag manager" button
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
     * @param cards the list of cards
     */
    public void displayCards(VBox vbox, int listId, List<Card> cards){
        //List<Card> cards = server.getCardsFromList(listId);
        cards.sort(Comparator.comparing(Card::getPosition));
        for (Card card : cards) {
            CardComponent cardComponent = new CardComponent(mainCtrl);

            cardComponent.boardID = boardID;
            cardComponent.setData(card, listId);
            vbox.getChildren().add(cardComponent);
        }
    }

//    /**
//     * displays cards in vboxes
//     * @param vbox the vbox in the list where the cards need to be showed
//     * @param listId the list we need to populate with cards
//     * @param focusCard f
//     */
//    public void displayCardsWithFocus(VBox vbox, int listId, Card focusCard){
//        List<Card> cards = server.getCardsFromList(listId);
//
//        for (Card card : cards) {
//            CardComponent cardComponent = new CardComponent(mainCtrl);
//            System.out.println(card +"<=card, focuscard=>" + focusCard);
//            System.out.println(card.softEquals(focusCard));
//            if (card.softEquals(focusCard)) {
//                System.out.println("Card is getting focus");
//                cardComponent.getTfTitle().requestFocus();
//            }
//            cardComponent.boardID = boardID;
//            cardComponent.setData(card, listId);
//            vbox.getChildren().add(cardComponent);
//        }
//    }

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
     * @param allCards list of lists of cards
     */
    public void displayLists(List<CardList> cardLists, List<List<Card>> allCards){
        int i = 0;
        for (CardList cardList : cardLists) {
            CardListComponent cardListComponent = new CardListComponent(mainCtrl, cardList.getId(),boardID);
            cardListComponent.setTitle(cardList.getName());
            cardListComponent.setOnMouseEntered(event -> addEnterKeyListener(cardList.getId()));
            hboxCardLists.getChildren().add(cardListComponent);
            cardListComponent.setData(cardList);
            displayCards(cardListComponent.getVboxCards(), cardList.getId(), allCards.get(i));
            i++;
        }
    }


    /**
     * Creates an observable list with all tags
     * @param boardID the board id we are in
     * @return an observable list with all tags
     */
    public ObservableList<Tag> getTagList(int boardID){
        List<Tag> tags = server.getTagsFromBoard(boardID);
        ObservableList<Tag> tagList = FXCollections.observableList(tags);
        return tagList;
    }

    /**
     * Refreshes the list overview with the tags
     */
    public void refreshListViewTags(){
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
     * refreshes the board's name field, board's key and tag list view
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
     */
    public void refresh() {
        System.out.println("Refreshing board overview");
        List<CardList> cardLists = getCardListsFromServer();
        List<List<Card>> allCards = new ArrayList<>();
        for (CardList cl : cardLists) {
            allCards.add(getCardsOfListFromServer(cl.getId()));
        }
        clearBoard();
        displayLists(cardLists, allCards);
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
        server.addCard(c, 0);
        refresh();
        mainCtrl.timeoutBoardRefresh();
    }

    /**
     * create a new card in a list
     * @param listID the id of the list to create the card in
     * @return the card element
     */
    public Card createCard(int listID) {
        Card c = new Card("title..");
        System.out.println("creating new card "+c+" in list id="+listID);
        c.setPosition(server.getListSize(listID) + 1);
        server.addCard(c, listID);
        refresh();
        return c;
//        mainCtrl.timeoutBoardRefresh();
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
}
