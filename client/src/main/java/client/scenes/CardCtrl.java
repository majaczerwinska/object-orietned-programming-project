package client.scenes;
import client.components.SubTaskComponent;
import client.utils.*;
import com.google.inject.Inject;
import commons.Card;
import commons.Tag;
import commons.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class CardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label label;
    @FXML
    private Label warning;
    @FXML
    private Button exit;
    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private ColorPicker palette;
    @FXML
    private TextField text;
    @FXML
    private TextArea area;
    @FXML
    private VBox vbox;

    @FXML
    private ListView<Tag> taglist;
    @FXML
    private Button removetag;
    @FXML
    private ListView<Tag> boardtags;
    @FXML
    private Accordion accordion;


    @FXML
    private TextField newTask;
    public int cardID;
    public int boardID;


    /**
     *
     * @param server -
     * @param mainCtrl -
     */
    @Inject
    public CardCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * displays tasks in vbox
     */
    public void displayTasks(){
        List<Task> tasks = server.getTasksFromCard(cardID);
        for (Task task : tasks) {
            SubTaskComponent taskComponent = new SubTaskComponent(cardID, this);
            taskComponent.setData(task);
            vbox.getChildren().add(taskComponent);
        }
    }

    /**
     * Clears the card overview
     */
    public void clearCard(){
        vbox.getChildren().clear();
        taglist.getItems().clear();
    }

    /**
     * exit card overview back to board overview
     */
    public void exit(){
        System.out.println(boardID + "cardexit");
        mainCtrl.showBoardOverview(boardID, true);
    }

    /**
     * create a new task in the cardoverview
     * @param event the keyboard event for [enter] key, tasks are added by pressing enter
     */
    public void createTask(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            if(newTask.getText().equals("")){
                label.setText("Required field!");
                return;
            }
            String name = newTask.getText();
            Task task = new Task(name);
            server.addTask(task,cardID);
            newTask.setText("");
            label.setText("");
            refresh();
        }
    }

    /**
     * refresh task list
     */
    public void refresh(){
        clearCard();
        displayTasks();
        showTags();
        showDropDown();
        escShortcut();
    }

    private void escShortcut() {
        Scene scene = taglist.getScene();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                exit();
            }
        });
    }

    /**
     * launch card overview scene util
     */
    public void setInfo(){
        text.setText( server.getCard(cardID).getTitle());
        palette.setValue(MainCtrl.colorParseToFXColor(server.getCard(cardID).getColor()));
        area.setText(server.getCard(cardID).getDescription());
    }

    /**
     * update method for when card info is changed
     */
    public void updateCard(){
        if(!text.getText().equals("")){
            Card card= new Card(text.getText());
            card.setDescription(area.getText());
            card.setColor(MainCtrl.colorParseToInt(palette.getValue()));
            server.editCard(boardID, cardID,card);
            warning.setText("");
        }
    }

    /**
     * make sure name field is not null before saving
     */
    public void checkforNullName(){
        if(text.getText().equals("")){
            warning.setText("Name required!");
        }
        else{
            warning.setText("");
        }
    }

    /**
     * Shows the tags in this board that are not added to the card yet
     */
    public void showDropDown(){
        boardtags.setItems(getTagListOfBoard(boardID));
        boardtags.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            public void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty || tag == null || tag.getTitle() == null) {
                    setText(null);
                } else {
                    setText(tag.getTitle());
                    String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
                    setStyle("-fx-control-inner-background: " + hexColor);
                }
            }
        });
    }

    /**
     * Shows the tags in this board that are not added to the card yet
     */
    public void showTags(){
        taglist.setItems(getTagListOfCard(cardID));
        taglist.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            public void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);

                if (empty || tag == null || tag.getTitle() == null) {
                    setText(null);
                } else {
                    setText(tag.getTitle());
                    String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
                    setStyle("-fx-control-inner-background: " + hexColor);
                }
            }
        });
    }


    /**
     * Gets the list of tags and puts it in an observable list
     * @param boardID the id of the board
     * @return an observable list of tags
     */
    public ObservableList<Tag> getTagListOfBoard(int boardID){
        List<Tag> tags = server.getTagsFromBoard(boardID);
        List<Tag> t = new ArrayList<>();
        Set<Tag> tagsofcard = server.getTagsForCard(cardID);
        for(Tag tag : tags){
            if(tagsofcard!=null && !tagsofcard.contains(tag)){
                t.add(tag);
            }
        }
        ObservableList<Tag> tagList = FXCollections.observableArrayList(t);
        return tagList;
    }

    /**
     * Gets the list of tags and puts it in an observable list
     * @param cardID the id of the board
     * @return an observable list of tags
     */
    public ObservableList<Tag> getTagListOfCard(int cardID){
        Set<Tag> tags = server.getTagsForCard(cardID);
        List<Tag> t = new ArrayList<>();
        for(Tag tag : tags){
                t.add(tag);
        }
        ObservableList<Tag> tagList = FXCollections.observableArrayList(t);
        return tagList;
    }

    /**
     * Shows the selected tag in the text field/area
     * @param event - clicking the mouse
     */
    public void addTagToCard(MouseEvent event){
        Tag tag = boardtags.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2 && tag!=null ){
            server.addTagToCard(boardID, tag.getId(), cardID);
            showTags();
            showDropDown();
        }
    }

    /**
     * Remove a tag from a card
     */
    public void removeTagFromCard(){
        Tag tag = taglist.getSelectionModel().getSelectedItem();
        if(tag!=null){
            server.removeTagFromCard(tag.getId(), cardID);
            showDropDown();
            taglist.getItems().clear();
            showTags();
        }
    }

}
