package client.scenes;

import client.utils.ServerUtils;
import javafx.application.Platform;

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
     * server selection button handler
     */
    public void showServerSelect() {
        mainCtrl.showServerSelect();
    }



    /**
     * exit app button handler
     */
    public void exitApp() {
        Platform.exit();
    }


}