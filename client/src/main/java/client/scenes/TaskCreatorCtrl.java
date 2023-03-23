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

    /**
     * Constructor for the scene
     * @param server the server connecting to the backend
     * @param main main controller
     */
    @Inject
    public TaskCreatorCtrl(ServerUtils server, MainCtrl main){
        this.main= main;
        this.server = server;

    }

    /**
     * Create task method
     * @param actionEvent clicking the create button
     */

    public void create(ActionEvent actionEvent){
        String name = text.getText();
        Task task  =new Task(name);
        server.addTask(task, 102);
        main.showLanding();
    }

    /**
     * Cancels the scene and returns to the previous one
     * @param actionEvent clicking the cancel button
     */

    public void cancel(ActionEvent actionEvent){
        main.showLanding();
    }




}
