package client.components;

import commons.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.io.IOException;

//TODO
public class CardComponent extends HBox{

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

        setOnMouseEntered(event ->
            {tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");});
        setOnMouseExited(event ->
            {tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-alignment: center");});
    }


    /**
     * updates card
     * @param card new card data
     */
    public void setData(Card card){
        tfTitle.setText(card.getTitle());
        if(card.hasDescription()){
            tfDescription.setText("Has description");
        } else{
            tfDescription.setText("No description");
        }
    }
}
