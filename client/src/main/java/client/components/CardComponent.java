package client.components;


import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Card;
import commons.Tag;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//TODO
public class CardComponent extends HBox implements Initializable {


    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;




    private int cardID;
    public int boardID;

    private int cardListID;

    private Card self;

    @FXML
    private TextField tfTitle;


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
     * @param mainCtrl the main controller instance
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

    /**
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param rb
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
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
        tfTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField has received focus
                tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");
            } else {
                // TextField has lost focus
                tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                        "-fx-alignment: center");
            }
        });
//        setOnMouseEntered(event -> {
//            toggleTextFieldToText(tfTitle);
//        });
//        setOnMouseExited(event -> {
//            toggleTextFieldToText(tfTitle);
//        });
        //tfDescription.setOnKeyTyped(event -> updateCard());
        setOnMouseEntered(event ->
            {tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");});
        setOnMouseExited(event ->
            {tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                    "-fx-alignment: center");});
        setOnMouseClicked(this::onElementClick);
    }


    /**
     * test method
     */
    public void addtesttagtocard() {
        serverUtils.addTagToCard(boardID,0,cardID);
    }


    /**
     * get list of colors for a specific tag
     * //todo here
     */
    public void getTagColors() {
        List<Tag> tags = serverUtils.getTagsForCard(cardID);
        System.out.println(tags);
    }

    /**
     * //todo make this set the border to every color of the tags in this card
     * @param pane the cards hbox
     * @param colors the list of javafx color elements
     */
    public void setMulticolouredBorder(Pane pane, List<Color> colors) {
        BorderStrokeStyle style = BorderStrokeStyle.SOLID;
        double borderWidth = 3;

        List<BorderStroke> borders = new ArrayList<>();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            borders.add(new BorderStroke(colors.get(i % colors.size()), style, null, new BorderWidths(borderWidth)));
        }

        pane.setBorder(new Border((BorderStroke) borders));
    }

    /**
     *
     * @return the cards title text field
     */
    public TextField getTfTitle() {
        return this.tfTitle;
    }

    /**
     * double clicking a card sends to cardoverview
     * @param event mouse event
     */
    @FXML
    public void onElementClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            joinCard();
        }
    }


    /**
     * open card overview
     */
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

    /**
     * delete a card from a list
     * listener for the [x] button in each card in board overview
     */
    public void deleteCard() {
        Platform.runLater(() -> {
            // UI update code here
            System.out.println("deleting card (CardComponent.deleteCard(self)) " + self);
            serverUtils.deleteCard(self, cardListID);
            Platform.runLater(() -> {
                mainCtrl.refreshBoardOverview();
            });
            mainCtrl.timeoutBoardRefresh(400);
            mainCtrl.timeoutBoardRefresh(700);
        });

    }


//    public void toggleTextFieldToText(Node node) {
//        if (node instanceof TextField) {
//            System.out.println("toggling from textfield to text");
//            TextField textField = (TextField) node;
//            Text text = new Text(textField.getText());
//            text.setFont(textField.getFont());
//            text.setStyle(textField.getStyle());
//            text.setLayoutX(textField.getLayoutX());
//            text.setLayoutY(textField.getLayoutY());
//            textField.getParent().getChildrenUnmodifiable().set(textField.getParent()
//            .getChildrenUnmodifiable().indexOf(textField), text);
//        } else if (node instanceof Text) {
//            System.out.println("toggling from text to textfield");
//            Text text = (Text) node;
//            TextField textField = new TextField(text.getText());
//            textField.setFont(text.getFont());
//            textField.setStyle(text.getStyle());
//            textField.setLayoutX(text.getLayoutX());
//            textField.setLayoutY(text.getLayoutY());
//            text.getParent().getChildrenUnmodifiable().set(text.getParent()
//            .getChildrenUnmodifiable().indexOf(text), textField);
//            textField.requestFocus();
//        }
//    }


    /**
     * mark a card as completed
     * listener for the checkbox
     * //todo make it work
     */
    public void markAsCompleted() {
        //tfTitle.setStyle("-fx-strikethrough: true");

        descriptionLabel.setStyle("-fx-strikethrough: true");
    }

    /**
     * unmark a card as completed
     * listener for the checkbox
     * //todo make it work
     */
    public void unMarkCompleted() {
        //tfTitle.setStyle("-fx-strikethrough: false");
        descriptionLabel.setStyle("-fx-strikethrough: false");
    }

    /**
     * set caret to title field of card
     * todo make it work
     */
    public void setFocusToTitleField() {
        tfTitle.requestFocus();
    }
}
