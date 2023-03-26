package client.scenes;



import client.components.SubTaskComponent;
import client.utils.*;
import com.google.inject.Inject;

import commons.Task;

import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.layout.VBox;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;



import java.util.List;



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
            SubTaskComponent taskComponent = new SubTaskComponent(cardID);
            taskComponent.setData(task);
            vbox.getChildren().add(taskComponent);
        }
    }


    /**
     * Clears the card overview
     */
    public void clearCard(){
        vbox.getChildren().clear();
    }
    
    public void exit(){
        mainCtrl.showBoardOverview(boardID);


    }



}
