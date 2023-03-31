package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.prefs.Preferences;

public class EditPasswordCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int boardID;
    private Preferences pref;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;
    @FXML
    private Button cancel;
    @FXML
    private Button edit;
    @FXML
    private Button remove;
    @FXML
    private Label warning1;
    @FXML
    private Label warning2;

    @Inject
    public EditPasswordCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.pref = Preferences.userRoot().node("locking");
    }

    /**
     * cancel method
     */
    public void cancel(){
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }

    /**
     * verifies the password
     * @return the password
     */
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

    /**
     * Sets the password
     */
    public void editPassword(){
        String password = verificationOfPassword();
        if(password==null) return;
        Board board = server.getBoard(boardID);
        board.setPassword(password);
        server.editBoard(boardID,board);
        pref.put(String.valueOf(boardID),password);
        clear();
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }

    public void removePassword(){
        Board board = server.getBoard(boardID);
        board.setPassword("");
        server.editBoard(boardID, board);
        pref.remove(String.valueOf(boardID));
        clear();
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }

    public void refresh(){
        Board board = server.getBoard(boardID);
        password1.setText(board.getPassword());
    }
    public void clear(){
        password1.setText("");
        password2.setText("");
        warning1.setText("");
        warning2.setText("");
    }




}
