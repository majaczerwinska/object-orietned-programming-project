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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//import java.net.URL;
import java.util.List;
//import java.util.ResourceBundle;

public class BoardOverviewCtrl /*implements Initializable*/ {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int boardID = 0;

    @FXML
    private VBox vboxList1;

    @FXML
    private HBox hboxCardLists;
    @FXML
    private Button btnTagManager;

    @FXML
    private ListView<Tag> listViewTags;

    @FXML
    private Button backbtn;

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
     * Goes back to previous scene
     * @param actionEvent the event when clicking the back button
     */
    public void back(ActionEvent actionEvent){
        System.out.println("going back");
        mainCtrl.showSelect();
    }

    /**
     * Shows card creation scene
     * @param actionEvent the event of clicking on a button
     */
    public void showCardAdd(ActionEvent actionEvent){
        System.out.println("card");
        mainCtrl.showCard();
    }
    /**
     * displays cards in vboxes
     * @param vbox the vbox in the list where the cards need to be showed
     * @param listId the list we need to populate with cards
     */
    public void displayCards(VBox vbox, int listId){
        List<Card> cards = server.getCardsFromList(listId);

        for (Card card : cards) {
            CardComponent cardComponent = new CardComponent();
            cardComponent.setData(card);
            vbox.getChildren().add(cardComponent);
        }
    }

    /**
     * displays cards in vboxes
     */
    public void displayLists(){
        List<CardList> cardLists = server.getCardListsFromBoard(boardID);

        for (CardList cardList : cardLists) {
            CardListComponent cardListComponent = new CardListComponent();
            cardListComponent.setTitle(cardList.getName());
            hboxCardLists.getChildren().add(cardListComponent);
            displayCards(cardListComponent.getVboxCards(), cardList.getId());
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
}
