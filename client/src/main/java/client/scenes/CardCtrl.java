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

    private int id;

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
     *
     */
    @FXML
    protected void doit() {
        String title = text.getText();
        Card card = new Card(title);
        server.addCard(card);
    }

    /**
     * Prepares the scene with the right tags to chose out of and the current name and description.
     * @param id the id of the card entered when opening this scene
     */
    @FXML
    private void openScene(int id) {
        this.id = id;
        // TODO when the scene is started the id of the card should be provided so that we know which card we are working with.

        Card card = server.getCard(id);

        tags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> items = FXCollections.observableArrayList();
        MultipleSelectionModel<String> selectionModel = tags.getSelectionModel();

        // TODO get all the created tags from database and instantiate them in a list view, also get the tags of this card and select them in listview/
//        for (int i = 0; i < ALLTAGS.size(); i++) {
//            items.add(ALLTAGS.get(i).getTitle());
//            if (TAGSOFCARD.contains(ALLTAGS.get(i))) selectionModel.select(i);
//        }
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
        Card c = server.getCard(id);
        List<Task> l = c.getTasks();
        l.add(new Task(newTask.getText()));
        c.setTasks(l);
        server.editCard(id, c);
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

        //TODO get a list of all tags from database, compare them with the listview and add them to a list if they are checked on.
//        for (Tag t : ALLTAGS) {
//            if (tags.contains(t.getTitle())) {
//                tagList.add(t);
//            }
//        }

        addNotSelectedTasks(taskList);

        Card card = new Card(name);
        card.setDescription(description);
        card.setTasks(taskList);
        card.setTags(tagList);
        card.setId(this.id);

        if (name.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name and description");
            alert.showAndWait();
        }
        else {
            server.editCard(id, card);
        }
        // TODO call the scene again so all values will be replaced with there new values. REFRESH
    }

    /**
     * Get all the text of not selected checkboxes and add those tasks to the new taskList
     * @param taskList The list of tasks that will become the new list of tasks of the card after saving.
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
}
