package client.scenes;


import client.utils.*;
import com.google.inject.Inject;
import commons.Card;
import commons.Tag;
import commons.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.List;
//import javafx.scene.shape.Circle;


public class CardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField name;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML
    private TextArea description;
    @FXML
    private ListView<String> tags;
    @FXML
    private Text text;
    @FXML
    private VBox vbox;

    @FXML
    private TextArea newTask;

    private int cardId;
    private int boardId;

//    @FXML
//    private Circle bigBlueButton;

    @FXML
    protected void onBigBlueButtonClick() {
//        bigBlueButton.
    }
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
     * Prepares the scene with the right tags to chose out of and the current name and description.
     * @param cardId the id of the card entered when opening this scene
     */
    @FXML
    private void openScene(int cardId, int boardId) {
        this.cardId = cardId;
        this.boardId = boardId;
        List<Tag> allTags = server.getTagsFromBoard(this.boardId);


        Card card = server.getCard(cardId);

        tags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> items = FXCollections.observableArrayList();
        MultipleSelectionModel<String> selectionModel = tags.getSelectionModel();


        for (int i = 0; i < allTags.size(); i++) {
            items.add(allTags.get(i).getTitle());
            if (card.getTags().contains(allTags.get(i))) selectionModel.select(i);
        }
        tags.setItems(items);



        this.name.setText(card.getTitle());
        this.description.setText(card.getDescription());



        for (Task t : card.getTasks()) {
            TextField textField = new TextField(t.getName());
            CheckBox checkBox = new CheckBox();
            HBox hbox = new HBox();
            hbox.getChildren().add(textField);
            hbox.getChildren().add(checkBox);
            vbox.getChildren().add(hbox);
        }
    }

    /**
     * When this method is called the window that has the button in it will be left.
     */
    @FXML
    private void exitButton(){
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**
     * Adds a task to the database
     */
    @FXML
    private void addTask(){
        Card c = server.getCard(cardId);
        List<Task> l = c.getTasks();
        l.add(new Task(newTask.getText()));
        c.setTasks(l);
        server.editCard(cardId, c);
    }




    /**
     * Saves the card and all changes made to it to the database.
     */
    @FXML
    private void save() {
        String name = this.name.getText();
        String description = this.description.getText();
        ObservableList<String> tags = this.tags.getSelectionModel().getSelectedItems();
        List<Tag> tagList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        List<Tag> allTags = server.getTagsFromBoard(boardId);


        for (Tag t : allTags) {
            if (tags.contains(t.getTitle())) {
                tagList.add(t);
            }
        }

        addNotSelectedTasks(taskList);

        Card card = new Card(name);
        card.setDescription(description);
        card.setTasks(taskList);
        card.setTags(tagList);
        card.setId(this.cardId);

        if (name.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name and description");
            alert.showAndWait();
        }
        else {
            server.editCard(cardId, card);
        }
        this.openScene(cardId, boardId);
    }

    /**
     * Get all the text of not selected checkboxes and add those tasks to the new taskList
     * @param taskList The list of tasks that will
     * become the new list of tasks of the card after saving.
     */
    private void addNotSelectedTasks(List<Task> taskList) {
        for (Node node :vbox.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                TextField textField = null;
                CheckBox checkBox = null;
                for (Node childNode : hbox.getChildren()) {
                    if (childNode instanceof TextField) {
                        textField = (TextField) childNode;
                    } else if (childNode instanceof CheckBox) {
                        checkBox = (CheckBox) childNode;
                    }
                }
                if (!checkBox.isSelected()){
                    taskList.add(new Task(textField.getText()));
                }
            }
        }
    }

    /**
     * Shows task creator
     * @param actionEvent the event of clicking the button
     */
    public void showTask(javafx.event.ActionEvent actionEvent) {
        mainCtrl.showTaskCreator();
    }
}
