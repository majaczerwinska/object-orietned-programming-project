package client.scenes;

import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;

public class LandingCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    /**
     *
     * @param server serverUtils
     * @param mainCtrl main controller
     */
    @Inject
    public LandingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     *
     * @param mouseEvent mouse info, automatic
     */
    public void onPublicClickEvent(MouseEvent mouseEvent) {
        System.out.println("public button was clicked!!!");
        mainCtrl.showPublicBoard();
    }

    /**
     * Shows the tag manager scene
     * @param actionEvent the event when clicking the "tag manager" button
     */
    public void showTagManager(ActionEvent actionEvent){
        System.out.println("showTagManger!!!");
        mainCtrl.showTagManager();
    }

    /**
     * Shows the task creator scene
     * @param actionEvent the event when clicking the "task creator" button
     */
    public void showTaskCreator(ActionEvent actionEvent){
        System.out.println("showTaskCreator!!!");
        mainCtrl.showTaskCreator();
    }

    /**
     *
     * @param actionEvent event details, automatic
     */
    public void onTeamActionEvent(ActionEvent actionEvent) {
        System.out.println("team action event :)");
    }

    public void onPrivateClickEvent() {
        mainCtrl.showBoardCreation();
    }

    /**
     *
     */
    public void refresh() {
    }

    /**
     * takes you to board selection scene
     * @param mouseEvent - click
     */
    public void onSelectClickEvent(MouseEvent mouseEvent) {
        System.out.println("Select was clicked");
        mainCtrl.showSelect();
    }


}
