package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.inject.Inject;

public class EnterAdminPassword {
    private MainCtrl mainCtrl;
    private ServerUtils server;
    @FXML
    private Button enterButton;

    @FXML
    private Text serverText;

    @FXML
    private Text errorMessage;
    @FXML
    private PasswordField password;

    @FXML
    private Button backButton;
    private String ip;

    /**
     * Constructor method for the admin password pop up
     * @param server injected server utils instance
     * @param mainCtrl main ctrl instance
     */
    @Inject
    public EnterAdminPassword(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * refresh the password field popup,
     * basically setting the ip field
     * @param ip the ip of the server to enter password for,
     *           passed on to the admin panel
     */
    public void refresh(String ip) {
        serverText.setText(ip);
        this.ip = ip;
    }


    /**
     * event handler for the enter button
     * checks the password in the server,
     * enters the admin panel if correct with the token
     * that the server sent in the response confirming authentication
     */
    public void enterAdminPanel() {
        String pwd = password.getText();
        String response = server.checkPassword(pwd);
        if (!response.equals("error")) {
            System.out.println("Opening admin panel for server "+ip);
            mainCtrl.openAdminPanel(ip, response);
        } else {
            errorMessage.setText("Incorrect password!");
            errorMessage.setFill(Color.INDIANRED);
        }
    }


    /**
     * reset error message if user types in the password field
     */
    public void resetErrorMessage() {
        errorMessage.setText("");
    }

    /**
     * back button event handler
     */
    public void goBack() {
        mainCtrl.showServerSelect();
    }
}
