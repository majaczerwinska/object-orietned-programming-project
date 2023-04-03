package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    @Inject
    public EnterAdminPassword(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void refresh(String ip) {
        serverText.setText(ip);
        this.ip = ip;
    }

    public void enterAdminPanel() {
        String pwd = password.getText();
        if (pwd.equals("hello")) {
            System.out.println("Opening admin panel for server "+ip);
            mainCtrl.openAdminPanel(ip);
        } else {
            errorMessage.setText("Incorrect password!");
        }
    }

    public void goBack() {
        mainCtrl.showServerSelect();
    }
}
