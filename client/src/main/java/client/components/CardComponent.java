package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

//TODO
public class CardComponent extends HBox{


    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;

    private int cardID;
    public int boardID;

    private Card self;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfDescription;

    @FXML
    private HBox hboxTags;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox cardFrame;


    public CardComponent(MainCtrl mainCtrl) {
        super();
        serverUtils = new ServerUtils();
        this.mainCtrl = mainCtrl;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/CardComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

//        setOnKeyPressed(event -> updateCard());
        tfTitle.setOnKeyTyped(event -> updateCard());
        tfDescription.setOnKeyTyped(event -> updateCard());
        setOnMouseEntered(event ->
            {tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");});
        setOnMouseExited(event ->
            {tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-alignment: center");});
        setOnMouseClicked(event -> onElementClick(event));
    }

    @FXML
    public void onElementClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            joinCard();
        }
    }


    public void joinCard() {
        if (self==null) {
            System.out.println("selected card appears to be null, in joiCoard() of CardComponentCtrl");
            return;
        }
        System.out.println("joining card #" + cardID);
        System.out.println(self);
        mainCtrl.showCard(cardID, boardID);
    }

    /**
     * Update card if any text field is updated
     */
    public void updateCard() {
        System.out.println("Text update card" + self);
        self.setTitle(tfTitle.getText());
        self.setDescription(tfDescription.getText());
        serverUtils.editCard(cardID,self);
        System.out.println("exits method"+ self);
    }

    /**
     * updates card
     * @param card new card data
     */
    public void setData(Card card){
        tfTitle.setText(card.getTitle());
        cardID = card.getId();
        self = card;
        if(card.hasDescription()){
            tfDescription.setText("Has description");
        } else{
            tfDescription.setText("No description");
        }
    }
}
