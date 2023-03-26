package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SubTaskComponent extends HBox {
    private int taskId;
    private int cardId;
    private Task self;
    @FXML
    private CheckBox checkbox;
    @FXML
    private TextField textField;
    @FXML
    private Button delete;
    private ServerUtils server;
    private MainCtrl mainCtrl;

    public SubTaskComponent(int cardId) {
        super();
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

        textField.setOnKeyTyped(event -> updateTask());
        checkbox.setOnAction(event -> updateTask());
        delete.setOnAction(event -> delete());
        setOnMouseEntered(event ->
        {textField.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-alignment: center");});
        setOnMouseExited(event ->
        {textField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-alignment: center");});

    }

    /**
     * updates a task
     */
    public void updateTask(){
        self.setName(textField.getText());
        self.setChecked(checkbox.isSelected());
        server.editTask(taskId,self);
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

    public void delete(){
        System.out.println(taskId);
        System.out.println(cardId);
        server.deleteTask(taskId, cardId);
    }







}
