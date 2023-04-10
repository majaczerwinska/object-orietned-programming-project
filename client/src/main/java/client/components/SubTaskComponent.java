package client.components;

import client.scenes.CardCtrl;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubTaskComponent extends HBox implements Initializable {
    private int taskId;
    private int cardId;
    private CardCtrl cardCtrl;
    private Task self;
    @FXML
    private CheckBox checkbox;
    @FXML
    private TextField textField;
    @FXML
    private Button delete;
    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * constructor for a subtask component
     * @param cardId the card id the tasks are for
     * @param cardCtrl the controller instance
     */
    public SubTaskComponent(int cardId, CardCtrl cardCtrl) {
        super();
        this.cardCtrl = cardCtrl;
        server = new ServerUtils();
        mainCtrl = new MainCtrl();
        this.cardId = cardId;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/SubTaskComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(SubTaskComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        textField.setOnKeyTyped(event -> updateNameTask());
        checkbox.setOnAction(event -> updateCheckTask());
        delete.setOnAction(event -> delete());

        textField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-alignment: center");

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
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            String originalValue = "";
            if (newValue) {
                // TextField has received focus
                originalValue = textField.getText();
            } else {
                // TextField has lost focus
                System.out.println("originalValue = " + originalValue + "\nnew value = " + textField.getText());
                if (!textField.getText().equals(originalValue)) {
                    cardCtrl.sendMessage("/app/update/cardOverview/" + cardId, "Subtask edited");
                    originalValue = textField.getText();
                }
            }
        });
    }

    /**
     * updates a task when name is changed
     */
    public void updateNameTask(){
        if(textField.getText().equals("")) return;
        self.setName(textField.getText());
        server.editTask(taskId,self);
    }

    /**
     * updates a task when checked
     */
    public void updateCheckTask(){
        if(textField.getText().equals("")) return;
        self.setChecked(checkbox.isSelected());
        server.editTask(taskId,self);
        cardCtrl.sendMessage("/app/update/cardOverview/" + cardId, "Subtask checked");
    }

    /**
     * updates task
     * @param task new task data
     */
    public void setData(Task task){
        textField.setText(task.getName());
        taskId = task.getId();
        self = task;
        checkbox.setSelected(task.isChecked());
    }

    /**
     * Disables write-mode
     */
    public void disable(){
        textField.setEditable(false);
        delete.setOnAction(e ->{
            return;
        });
        checkbox.setDisable(true);
    }

    /**
     * Enables write-mode
     */
    public void enable(){
        textField.setEditable(true);
        delete.setOnAction(e ->{
            delete();
        });
        checkbox.setDisable(false);
    }

    /**
     * delete a subtask
     */
    public void delete(){
        System.out.println("Deleting task with id="+taskId);
        System.out.println("On card id="+cardId);
        server.deleteTask(taskId, cardId);
        cardCtrl.refresh();
        cardCtrl.sendMessage("/app/update/cardOverview/" + cardId, "Subtask deleted");
    }







}
