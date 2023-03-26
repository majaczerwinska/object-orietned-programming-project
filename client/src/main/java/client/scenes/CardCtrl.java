package client.scenes;



import client.components.SubTaskComponent;
import client.utils.*;
import com.google.inject.Inject;

import commons.Task;

import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.TextField;



import java.util.List;



public class CardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Label label;
    @FXML
    private Button exit;
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
    }
    
    public void exit(){
        mainCtrl.showBoardOverview(boardID);
    }

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

    public void refresh(){
        clearCard();
        displayTasks();
    }





}
