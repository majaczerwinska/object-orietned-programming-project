package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.inject.Inject;
import java.util.prefs.Preferences;

public class EditPasswordCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int boardID;
    private Preferences pref;
    @FXML
    private Label editPassLabel;
    @FXML
    private Label passLabel;
    @FXML
    private Label verifyLabel;
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

    /**
     * Constructor
     * @param server the server
     * @param mainCtrl the main controller
     */
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

    /**
     * removes the password
     */
    public void removePassword(){
        Board board = server.getBoard(boardID);
        board.setPassword("");
        server.editBoard(boardID, board);
        pref.remove(String.valueOf(boardID));
        clear();
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }

    /**
     * refresh
     */
    public void refresh(){
        Board board = server.getBoard(boardID);
        password1.setText(board.getPassword());
        shortcut();
    }

    /**
     * clear
     */
    public void clear(){
        password1.setText("");
        password2.setText("");
        warning1.setText("");
        warning2.setText("");
    }

    /**
     * title label getter
     * @return Label
     */
    public Label getEditPassLabel() {
        return editPassLabel;
    }

    /**
     * password label getter
     * @return Label
     */
    public Label getPassLabel() {
        return passLabel;
    }

    /**
     * verify password label getter
     * @return Label
     */
    public Label getVerifyLabel() {
        return verifyLabel;
    }

    /**
     * pass textfield getter
     * @return TextField
     */
    public TextField getPasswordTextField() {
        return password1;
    }

    /**
     * verify password textfield getter
     * @return TextField
     */
    public TextField getPasswordVerifyTextField() {
        return password2;
    }

    /**
     * cancel button gette
     * @return Button
     */
    public Button getCancel() {
        return cancel;
    }

    /**
     * edit button getter
     * @return Button
     */
    public Button getEdit() {
        return edit;
    }

    /**
     * remove button getter
     * @return Button
     */
    public Button getRemove() {
        return remove;
    }

    /**
     * warning for password label getter
     * @return Label
     */
    public Label getWarningPassLabel() {
        return warning1;
    }

    /**
     * warning for verification label getter
     * @return Label
     */
    public Label getWarningVerifyLabel() {
        return warning2;
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        cancel.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }
}
