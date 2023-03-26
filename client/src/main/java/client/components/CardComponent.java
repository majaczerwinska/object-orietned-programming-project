package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//TODO
public class CardComponent extends HBox implements Initializable {


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
    private Label descriptionLabel;

    @FXML
    private HBox hboxTags;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox cardFrame;

    @FXML
    private CheckBox checkMark;

    /**
     * The constructor for the component
     * @param mainCtrl the one instance of main controller
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
        //tfDescription.setOnKeyTyped(event -> updateCard());
        setOnMouseEntered(event -> {
                tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");
            });
        setOnMouseExited(event -> {
            tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-alignment: center");
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTitle.requestFocus();
        checkMark.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Checkbox is checked");
                markAsCompleted();
            } else {
                System.out.println("Checkbox is unchecked");
                unMarkCompleted();
            }
        });
        descriptionLabel.setOnMouseEntered(event -> {
            descriptionLabel.setStyle("-fx-underline: true");

        });
        descriptionLabel.setOnMouseExited(event -> {
            descriptionLabel.setStyle("-fx-underline: false");
        });
//        setOnMouseEntered(event -> {
//            toggleTextFieldToText(tfTitle);
//        });
//        setOnMouseExited(event -> {
//            toggleTextFieldToText(tfTitle);
//        });
    }

    /**
     * Update card if any text field is updated
     * //todo, still not verified that it works
     */
    public void updateCard() {
        System.out.println("Text update card" + self);
        self.setTitle(tfTitle.getText());
        //self.setDescription(tfDescription.getText());
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
            descriptionLabel.setText("View description...");
        } else{
            descriptionLabel.setText("");
        }
    }

    public void deleteCard() {
        System.out.println("deleting card (CardComponent.deleteCard(self)) " + self);
        serverUtils.deleteCard(self, cardListID);
        mainCtrl.refreshBoardOverview();
        mainCtrl.timeoutBoardRefresh(400);
        mainCtrl.timeoutBoardRefresh(700);
    }


    public void toggleTextFieldToText(Node node) {
        if (node instanceof TextField) {
            System.out.println("toggling from textfield to text");
            TextField textField = (TextField) node;
            Text text = new Text(textField.getText());
            text.setFont(textField.getFont());
            text.setStyle(textField.getStyle());
            text.setLayoutX(textField.getLayoutX());
            text.setLayoutY(textField.getLayoutY());
            textField.getParent().getChildrenUnmodifiable().set(textField.getParent().getChildrenUnmodifiable().indexOf(textField), text);
        } else if (node instanceof Text) {
            System.out.println("toggling from text to textfield");
            Text text = (Text) node;
            TextField textField = new TextField(text.getText());
            textField.setFont(text.getFont());
            textField.setStyle(text.getStyle());
            textField.setLayoutX(text.getLayoutX());
            textField.setLayoutY(text.getLayoutY());
            text.getParent().getChildrenUnmodifiable().set(text.getParent().getChildrenUnmodifiable().indexOf(text), textField);
            textField.requestFocus();
        }
    }


    public void markAsCompleted() {
        //tfTitle.setStyle("-fx-strikethrough: true");

        descriptionLabel.setStyle("-fx-strikethrough: true");
    }

    public void unMarkCompleted() {
        //tfTitle.setStyle("-fx-strikethrough: false");
        descriptionLabel.setStyle("-fx-strikethrough: false");
    }
}
