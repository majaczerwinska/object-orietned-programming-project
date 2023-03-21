package client.scenes;

import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.io.IOException;

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
     *
     * @param actionEvent event details, automatic
     */
    public void onTeamActionEvent(ActionEvent actionEvent) {
        System.out.println("team action event :)");
    }

    /**
     *
     */
    public void refresh() {
    }

    public void onSelectClickEvent(MouseEvent mouseEvent) {
        System.out.println("Select was clicked");
        mainCtrl.showSelect();
    }


}
