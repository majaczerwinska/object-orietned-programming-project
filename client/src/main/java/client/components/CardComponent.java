package client.components;

import client.utils.ServerUtils;
import commons.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

//TODO
public class CardComponent extends HBox{


    private ServerUtils serverUtils;

    private int cardID;

    private Card self;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextArea taDescription;

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
    public CardComponent() {
        super();
        serverUtils = new ServerUtils();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/CardComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Update card if any text field is updated
     * //todo, still not verified that it works
     */
    public void updateCard() {
        self.setTitle(tfTitle.getText());
        self.setDescription(taDescription.getText());
        serverUtils.editCard(cardID,self);
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
            taDescription.setText("Has description");
        } else{
            taDescription.setText("No description");
        }
    }
}
