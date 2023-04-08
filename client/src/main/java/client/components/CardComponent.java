package client.components;


import client.scenes.BoardOverviewCtrl;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Card;
import commons.Tag;
import commons.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CardComponent extends HBox implements Initializable {


    private ServerUtils server;
    private MainCtrl mainCtrl;




    public int cardID;
    public int boardID;

    private int cardListID;

    public Card self;

    private boolean highlighted;

    private BoardOverviewCtrl boardOverviewCtrl;

    String originalValue = "title..";

    @FXML
    public TextField tfTitle;


    @FXML
    public Label descriptionLabel;

    @FXML
    private HBox hboxTags;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox cardFrame;
    private boolean isLocked;

    @FXML
    private Text text;

    /**
     * The constructor for the component
     * @param mainCtrl the main controller instance
     * @param isLocked
     */
    @SuppressWarnings("checkstyle:JavadocMethod")
    public CardComponent(MainCtrl mainCtrl, boolean isLocked) {
        super();
        server = new ServerUtils();
        this.mainCtrl = mainCtrl;
        this.isLocked = isLocked;

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
//        if(!isLocked){
//           // tfTitle.setOnKeyTyped(event -> updateCard());
//
//        }

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
        if(!isLocked) {
            tfTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    // TextField has received focus
                    mainCtrl.appendStyle(tfTitle,"-fx-border-color: black; -fx-alignment: center");
                } else {
                    // TextField has lost focus
                    System.out.println("originalValue = " + originalValue + "\nnew value = " + tfTitle.getText());
                    if(!tfTitle.getText().equals(originalValue)){
                        boardOverviewCtrl.sendMessage("/app/update/card/"+boardID,
                                "Done updating card in component");
                        originalValue = tfTitle.getText();
                    }
                    mainCtrl.appendStyle(tfTitle,"-fx-background-color: transparent; -fx-border-color: transparent; " +
                            "-fx-alignment: center");
                }
            });
            setOnMouseEntered(event ->
            {mainCtrl.appendStyle(tfTitle,"-fx-border-color: black; -fx-alignment: center");});
            setOnMouseExited(event ->
            {
                tfTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // TextField has received focus
                        mainCtrl.appendStyle(tfTitle,"-fx-border-color: black; -fx-alignment: center");
                    } else {
                        // TextField has lost focus
                        mainCtrl.appendStyle(tfTitle,"-fx-background-color: transparent; -fx-border-color: transparent; " +
                                "-fx-alignment: center");
                    }
                });
            });

            dragging();

        }
        setOnMouseClicked(this::onElementClick);
        descriptionLabel.setOnMouseEntered(event -> {
            descriptionLabel.setStyle("-fx-underline: true");

        });
        descriptionLabel.setOnMouseExited(event -> {
            descriptionLabel.setStyle("-fx-underline: false");
        });

        this.boardOverviewCtrl = mainCtrl.getBoardOverviewCtrl();

        cardFrame.setOnMouseEntered(event -> {
            String style = getStyle();
            style += "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.9), 5, 0, 0, 5);";
            setStyle(style);
            highlighted = true;
            boardOverviewCtrl.highlightedCardComponent= this;
        });
        cardFrame.setOnMouseExited(event -> {
            String style = getStyle();
            style = style.replace("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.9), 5, 0, 0, 5);", "");
            setStyle(style);
            highlighted = false;
            boardOverviewCtrl.highlightedCardComponent = null;
        });
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


                System.out.println("successfull dropping said by cardcomponent");
                boardOverviewCtrl.sendMessage("/app/update/card/"+boardID,
                        "Done dragging card in component");
            }
            event.consume();

        });


    }


    /**
     * get tasks, update card component to show how many are completed
     */
    public void setTaskProgress() {
        List<Task> tasks = server.getTasksFromCard(cardID);
        int completed = 0;
        for (Task t : tasks) {
            if (t.isChecked()) completed++;
        }
        text.setText(completed+"/"+tasks.size());

    }
    /**
     * get list of colors for a specific tag
     */
    public void getTagColors() {
        List<Color> l = new ArrayList<>();
        for(Tag t : server.getTagsForCard(cardID)){
            l.add(MainCtrl.colorParseToFXColor(t.getColor()));
        }
        setMulticolouredBorder(l);
    }

    /**
     * //todo make this set the border to every color of the tags in this card
     * @param colors the list of javafx color elements
     */
    public void setMulticolouredBorder(List<Color> colors) {
        List<Stop> stops = new ArrayList<>();
        double size = colors.size();
        double i = 0;
        for (Color c : colors) {
            double offset1 = i++ / size;
            double offset2 = i / size;
            stops.add(new Stop(offset1, c));
            stops.add(new Stop(offset2, c));
        }
        // Create a custom border with a LinearGradient stroke
        Border border = new Border(
                new BorderStroke(
                        new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops),
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(3)));

        // Set the HBox's border to the custom border
        this.setBorder(border);
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
        mainCtrl.showCard(cardID, boardID, isLocked);
    }

    /**
     * Update card if any text field is updated
     */
    public void updateCard() {
        System.out.println("Update card method called in card component for card=" + self);
        self.setTitle(tfTitle.getText());

        server.editCard(boardID, cardID,self, true);
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
     * Disables the write mode on card
     */
    public void readmode(){
        if(isLocked){
            btnDelete.setOnAction(e->{
                mainCtrl.showWarning(boardID);
                return;
            });
            tfTitle.setOnKeyTyped(event -> {
                return ;
            });
            tfTitle.setEditable(false);


        }
    }


    /**
     * set caret to title field of card
     * todo make it work
     */
    public void setFocusToTitleField() {
        tfTitle.requestFocus();
    }
}
