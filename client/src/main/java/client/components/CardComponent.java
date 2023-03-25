package client.components;

import commons.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

//TODO
public class CardComponent extends HBox{

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelHasDescription;

    @FXML
    private HBox hboxTags;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;



    /**
     * The constructor for the component
     */
    public CardComponent() {
        super();
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
     * updates card
     * @param card new card data
     */
    public void setData(Card card){
        labelTitle.setText(card.getTitle());
        if(card.hasDescription()){
            labelHasDescription.setText("Has description");
        } else{
            labelHasDescription.setText("No description");
        }
    }
}
