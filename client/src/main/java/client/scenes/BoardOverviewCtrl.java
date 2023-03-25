package client.scenes;

import client.components.CardComponent;
import client.components.CardListComponent;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.CardList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button backbtn;

    @FXML
    private Button addListButton;

    @FXML
    private Button refreshButton;

    @FXML Button editBoardButton;


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
     * Shows the tag manager scene
     * @param actionEvent the event when clicking the "tag manager" button
     */
    public void showTagManager(ActionEvent actionEvent){
        System.out.println("showTagManger!!!");
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
            cardComponent.setData(card, listId);
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
            cardListComponent.setData(cardList);
            displayCards(cardListComponent.getVboxCards(), cardList.getId());
        }
    }

    /**
     * Clears the board overview
     */
    public void clearBoard(){
        hboxCardLists.getChildren().clear();
    }


    /**
     * Refresh scene from database
     */
    public void refresh() {
        System.out.println("Refreshing board overview");
        clearBoard();
        displayLists();
    }

    public void createTestCard() {
        Card c = new Card("test card ..");
        System.out.println("creating test card "+c);
        server.addCard(c, 0);
    }
}
