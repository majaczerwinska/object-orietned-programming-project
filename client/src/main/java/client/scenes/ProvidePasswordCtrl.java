package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.prefs.Preferences;

public class ProvidePasswordCtrl {
    private ServerUtils server;
    private MainCtrl mainCtrl;
    @FXML
    private Button cancel;
    @FXML
    private Button unlock;
    @FXML
    private TextField password;
    @FXML
    private Label warning;
    public int boardID;
    private Preferences pref;
    @Inject
    public ProvidePasswordCtrl(ServerUtils server,MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pref = Preferences.userRoot().node("locking");
    }

    public void cancel(){
        mainCtrl.closeLocker();
    }

    public void unlock(){
        String pass = password.getText();
        if(!pass.equals(server.getBoard(boardID).getPassword())){
            warning.setText("Wrong password!");
            return;
        }
        pref.put(String.valueOf(boardID),pass);
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }



}
