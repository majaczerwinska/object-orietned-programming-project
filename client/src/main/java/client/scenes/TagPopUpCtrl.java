package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.inject.Inject;


public class TagPopUpCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Label title;
    // title of the help page



    @FXML
    private Button goBackButton;
    // button to return to main menu

    /**
     * help controller constructor method
     * @param mainCtrl main controller
     * @param server server utils functions
     */
    @Inject
    public TagPopUpCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * refresh method
     */
    public void refresh() {
    }


}
