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

    /**
     * Constructor
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public ProvidePasswordCtrl(ServerUtils server,MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pref = Preferences.userRoot().node("locking");
    }

    /**
     * cancels the popup
     */
    public void cancel(){
        clear();
        mainCtrl.closeLocker();

    }

    /**
     * unlocks the board for write mode
     */
    public void unlock(){
        String pass = password.getText();
        if(!pass.equals(server.getBoard(boardID).getPassword())){
            warning.setText("Wrong password!");
            return;
        }
        pref.put(String.valueOf(boardID),pass);
        clear();
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }

    public void clear(){
        password.setText("");
        warning.setText("");

    }



}
