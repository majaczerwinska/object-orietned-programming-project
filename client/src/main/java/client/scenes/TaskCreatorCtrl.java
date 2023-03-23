package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TaskCreatorCtrl {
    private ServerUtils server;
    private MainCtrl main;
    @FXML
    private TextField text;
    @Inject
    public TaskCreatorCtrl(ServerUtils server, MainCtrl main){
        this.main= main;
        this.server = server;

    }

    public void create(ActionEvent actionEvent){
        String name = text.getText();
        Task task  =new Task(name);
        server.addTask(task, 102);
        main.showLanding();
    }

    public void cancel(ActionEvent actionEvent){
        main.showLanding();
    }




}
