package client.scenes;

import javax.inject.Inject;

public class HelpCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Label title;
    // title of the help page


    /**
     * help controller constructor method
     * @param mainCtrl main controller
     */
    @Inject
    public HelpCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * go back button handler method
     */
    public void goBackButtonHandler() {
        mainCtrl.showLanding();
    }

    /**
     * refresh method
     */
    public void refresh() {
    }
}
