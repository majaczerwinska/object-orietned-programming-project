package client.components;


import client.scenes.BoardOverviewCtrl;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Card;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

//TODO
public class CardComponent extends HBox implements Initializable {


    private ServerUtils server;
    private MainCtrl mainCtrl;




    public int cardID;
    public int boardID;

    private int cardListID;

    public Card self;

    private boolean highlighted;

    private BoardOverviewCtrl boardOverviewCtrl;


    @FXML
    public TextField tfTitle;


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
        server = new ServerUtils();
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
//        setOnMouseEntered(event -> {
//                tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");
//            });
//        setOnMouseExited(event -> {
//            tfTitle.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
//                    "-fx-alignment: center");
//        });

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

        setOnMouseEntered(event ->
            {tfTitle.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");});
        setOnMouseExited(event ->
            {
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
            });
        setOnMouseClicked(this::onElementClick);
        dragging();


        this.boardOverviewCtrl = mainCtrl.getBoardOverviewCtrl();

        cardFrame.setOnMouseEntered(event -> {
            BorderStroke borderStroke = new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, null, new BorderWidths(2));
            cardFrame.setBorder(new Border(borderStroke));
            highlighted = true;
            boardOverviewCtrl.highlightedCardComponent= this;
        });
        cardFrame.setOnMouseExited(event -> {
            cardFrame.setBorder(null);
            highlighted = false;
            boardOverviewCtrl.highlightedCardComponent = null;
        });


//        this.cardFrame.getScene().setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.DELETE) {
//                if (highlighted) mainCtrl.showCard(cardID, boardID);
//            }
//        });
    }


    /**
     * test method
     */
    public void addtesttagtocard() {
        server.addTagToCard(boardID,0,cardID);
    }

    /**
     * Method for all the dragging
     */
    public void dragging(){
        setOnDragDetected(event-> {
            System.setProperty("javafx.dnd.delayedDragCallback", "false");
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
            // db.setDragView(this.snapshot(null, null)); // show a snapshot of the card while dragging
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(cardID));
            db.setContent(content);
            event.consume();

        });
        setOnDragDone(event ->  {

            if (event.getTransferMode() == TransferMode.MOVE) {


                System.out.println("successfull dropping said by cardcomponent");;
            }
            event.consume();

        });
//        setOnDragOver(event ->{
//            if (event.getGestureSource() != this &&
//                    event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        });
//        setOnDragEntered(event -> {
//            if (event.getGestureSource() != this &&
//                    event.getDragboard().hasString()) {
//                this.setStyle("-fx-background-color: green");
//            }
//            event.consume();
//        });
//        setOnDragExited(event -> {
//
//            this.setStyle("-fx-background-color: white");
//            event.consume();
//
//        });
//        setOnDragDropped(event -> {
//
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.hasString()) {
//                Card c = server.getCard(Integer.parseInt(db.getString()));
//                System.out.println(c);
//                System.out.println(cardListID);
//                server.changeListOfCard(cardListID,c);
//                success = true;
//            }
//            event.setDropCompleted(success);
//            Platform.runLater(()->{
//                mainCtrl.refreshBoardOverview();
//            });
//
//            event.consume();
//
//        });

    }

    /**
     * get list of colors for a specific tag
     * //todo here
     */
    public void getTagColors() {
        Set<Tag> tags = server.getTagsForCard(cardID);
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
     * double-clicking a card sends to card-overview
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
        System.out.println(boardID + "joinCard cardComponent");
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
        System.out.println("Update card method called in card component for card=" + self);
        self.setTitle(tfTitle.getText());

        server.editCard(boardID, cardID,self);
        System.out.println("update card method exits with card="+ self);
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
        // UI update code here
        System.out.println("deleting card on board#"+ boardID + " (CardComponent.deleteCard(self)) " + self);
        server.deleteCard(self, boardID, cardListID);
        mainCtrl.refreshBoardOverview();
    }




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
