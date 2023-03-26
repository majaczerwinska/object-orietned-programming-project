package client.scenes;



import client.components.SubTaskComponent;
import client.utils.*;
import com.google.inject.Inject;

import commons.Card;
import commons.Task;

import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.VBox;

import javafx.scene.control.TextField;



import java.util.List;



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

    /**
     * exit card overview back to board overview
     */
    public void exit(){
        mainCtrl.showBoardOverview(boardID);
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
            server.editCard(cardID,card);
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








}
