package client.scenes;

import javax.inject.Inject;
import client.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class HelpCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Label title;
    // title of the help page


    /**
     * help controller constructor method
     * @param mainCtrl main controller
     * @param server server utils functions
     */
    @Inject
    public HelpCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * refresh method
     */
    public void refresh() {
    }
}
