package client.scenes;

import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.paint.Color;

public class PublicBoardCtrl {
    @FXML
    Button publicBoardBackButton;

    @FXML
    public Stage stage;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     *
     * @param server server utils
     * @param mainCtrl main controller
     */
    @Inject
    public PublicBoardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     *
     * @param actionEvent event details, automatic
     */
    public void backButtonAction(ActionEvent actionEvent) {
//        publicBoardBackButton.setText("Clicked !!");
        System.out.println("back button was clicked");
//        stage.setScene();
        mainCtrl.showLanding();
    }

    /**
     *
     */
    public void refresh() {

    }

    /**
     * takes you to the list creation scene
     * @param mouseEvent
     */

    @FXML
    public void addNewList(MouseEvent mouseEvent){
        mainCtrl.showListCreate(0);
    }
}
