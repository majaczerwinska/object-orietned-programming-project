package client.scenes;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
//import javafx.scene.shape.Circle;


public class CardCreationCtrl {
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
    public CardCreationCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    


    /**
     * Start the scene and prepare the listview with all current tags.
     * @param boardId The ID of the board we get all tags from
     */
    @FXML
    private void openScene(int boardId) {
        this.boardId = boardId;
        List<Tag> allTags = server.getTagsFromBoard(this.boardId);


        // Make listview ready
        tags.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> items = FXCollections.observableArrayList();
        MultipleSelectionModel<String> selectionModel = tags.getSelectionModel();


        for (Tag allTag : allTags) {
            items.add(allTag.getTitle());
        }
        tags.setItems(items);
    }

    /**
     * When this method is called the window that has the button in it will be left.
     */
    @FXML
    private void exitButton(){
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

//    /**
//     * Adds a task to the database
//     */
//    @FXML
//    private void addTask(){
//        Card c = server.getCard(cardId);
//        List<Task> l = c.getTasks();
//        l.add(new Task(newTask.getText()));
//        c.setTasks(l);
//        server.editCard(cardId, c);
//    }




    /**
     * Saves the card and all changes made to it to the database.
     */
    @FXML
    private void addCard() {
        String name = this.name.getText();
        String description = this.description.getText();
        ObservableList<String> tags = this.tags.getSelectionModel().getSelectedItems();
        List<Tag> tagList = new ArrayList<>();
        List<Tag> allTags = server.getTagsFromBoard(boardId);


        for (Tag t : allTags) {
            if (tags.contains(t.getTitle())) {
                tagList.add(t);
            }
        }



        Card card = new Card(name);
        card.setDescription(description);
        card.setTags(tagList);

        if (name.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name and description");
            alert.showAndWait();
        }
        else {
//            server.addCard(card);
        }
        exitButton(); // Exits the scene
    }


}
