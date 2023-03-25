package client.scenes;

import client.components.CardComponent;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

//import java.net.URL;
import java.util.List;
//import java.util.ResourceBundle;

public class BoardOverviewCtrl /*implements Initializable*/ {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int listId = 1; //hardcoded for now

    public int boardID = 0;

    @FXML
    private VBox vboxList1;

    @FXML
    private Button btnTagManager;

    @FXML
    private Button backbtn;


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
        mainCtrl.showTagManager();
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
     */
    public void displayCards(){
        List<Card> cards = server.getCardsFromList(listId);

        for (Card card : cards) {
            CardComponent cardComponent = new CardComponent();
            cardComponent.setData(card);
            vboxList1.getChildren().add(cardComponent);
        }
    }
}
