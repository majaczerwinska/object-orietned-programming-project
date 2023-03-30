package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import javax.inject.Inject;
import java.util.prefs.Preferences;


public class LockInUnlockedBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private Button cancel;
    @FXML
    private Button setpassword;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;
    @FXML
    private Label warning1;
    @FXML
    private Label warning2;

    public int boardID;
    private Preferences pref;


    @Inject
    public LockInUnlockedBoardCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.pref = Preferences.userRoot().node("locking");
    }

    public void cancel(){
        mainCtrl.showBoardOverview(boardID);
    }

    public String verificationOfPassword(){
        if(password1.getText().equals("")){
            warning1.setText("Required field!");
            return null;
        }
        String password = password1.getText();
        if(!password2.getText().equals(password)){
            warning2.setText("You typed a different password!");
            return null;
        }
        return password;
    }

    public void setPassword(){
        String password = verificationOfPassword();
        if(password==null) return;
        Board board = server.getBoard(boardID);
        board.setPassword(password);
        server.editBoard(boardID,board);
        pref.put(String.valueOf(boardID),password);
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);

    }




}
