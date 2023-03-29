package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import commons.Board;

public class BoardCreationCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Label titleLabel;
    @FXML
    private ColorPicker palette;
    @FXML
    private Label warningname;
    @FXML
    private Label warningkey;
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
        if(boardTitle.equals("")){
            warningname.setText("\"Required field!\"");
            return;
        }

        String boardKey = boardKeyTextField.getText();
        if(boardKey.length() == 0)
        {
            warningkey.setText("Required field!");
            return;

        }
        Board newBoard = new Board(boardTitle);
        newBoard.setBoardkey(boardKey);
        //newBoard.setbColor(MainCtrl.colorParseToInt(palette.getValue()));
        newBoard.setbColor(mainCtrl.colorParseToInt(palette.getValue()));

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
