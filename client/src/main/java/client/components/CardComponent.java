package client.components;

import client.Main;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

//TODO
public class CardComponent extends HBox{


    private ServerUtils serverUtils;

    private MainCtrl mainCtrl;



    private int cardID;

    private int cardListID;

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

    /**
     * The constructor for the component
     */
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

        TextField titleTextField = (TextField) lookup("#tfTitle");
        titleTextField.requestFocus();

        tfTitle.setOnKeyTyped(event -> updateCard());
        tfDescription.setOnKeyTyped(event -> updateCard());
        setOnMouseEntered(event ->
            {tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");});
        setOnMouseExited(event ->
            {tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-alignment: center");});
    }

    /**
     * Update card if any text field is updated
     * //todo, still not verified that it works
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
     *
     * @param card   new card data
     * @param listId the cards parent list
     */
    public void setData(Card card, int listId){
        tfTitle.setText(card.getTitle());
        cardID = card.getId();
        self = card;
        cardListID = listId;
        if(card.hasDescription()){
            tfDescription.setText("Has description");
        } else{
            tfDescription.setText("No description");
        }
    }

    public void deleteCard() {
        System.out.println("deleting card (CardComponent.deleteCard(self)) " + self);
        serverUtils.deleteCard(self, cardListID);
        mainCtrl.refreshBoardOverview();
        mainCtrl.timeoutBoardRefresh(400);
        mainCtrl.timeoutBoardRefresh(700);
    }


}
