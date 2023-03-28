package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import commons.Board;

public class BoardCreationCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Label titleLabel;
    @FXML
    private Label boardTitleLabel;
    @FXML
    private TextField boardTitleTextField;
    @FXML
    private Label boardKeyLabel;
    @FXML
    private TextField boardKeyTextField;
    @FXML
    private Label colorLabel;
    @FXML
    private TextField colorTextField;
    @FXML
    private Label passwordRequireLabel;
    @FXML
    private RadioButton passwordRequiredRadioButton;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button createBoardButton;
    @FXML
    private Button stopCreatingBoardButton;

    /**
     * board creator controller constructor
     * 
     * @param server   server
     * @param mainCtrl main controller
     */
    @Inject
    public BoardCreationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * create board button handler
     */
    @FXML
    public void createBoardButtonClickHandler() {
        String boardTitle = boardTitleTextField.getText();
        Board newBoard = new Board(boardTitle);
        String boardKey = boardKeyTextField.getText();
        if(boardKey.length() == 0)
        {
            // pop to enter the board key and try again
        }
        newBoard.setBoardkey(boardKey);
        String colorValue = colorTextField.getText();
        if (colorValue.length() == 0) {
            // color gets defaulted to white
            colorValue = "ffffff";
        }
        if (colorValue.length() != 6 && colorValue.length() != 0) {
            // show error
            // make a pop up to say that your color hexcode doesn't have enough digits
        } else {
            boolean okay = true;
            for (int i = 0; i < 6; i++) {
                if (!(colorValue.charAt(i) <= 102 &&
                        colorValue.charAt(i) >= 97) ||
                        (colorValue.charAt(i) <= 57 &&
                                colorValue.charAt(i) >= 48)) {
                    // show pop up that shows your color is not well formated
                    okay = false;
                }
            }
            if (okay == true) {
                // color assigned to the board
                newBoard.setColor(Integer.parseInt(colorValue, 16));
            }
        }
        
        if (passwordRequiredRadioButton.isSelected()) {
            String passwordValue = passwordTextField.getText();
            newBoard.setPassword(passwordValue);
        }
        server.addBoard(newBoard);
        mainCtrl.saveBoardByKey(newBoard.getBoardkey());
        mainCtrl.showSelect();
    }

    /**
     * Method to stop creating a new board and shows the selection
     */
    @FXML
    public void stopCreatingBoardButtonHandler() {
        // go back to the selection scene scene
        mainCtrl.showSelect();
    }

    /**
     * refresh method
     */
    public void refresh() {
    }
}
