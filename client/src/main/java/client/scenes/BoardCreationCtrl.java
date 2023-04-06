package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import commons.Board;
import javafx.scene.input.KeyCode;

public class BoardCreationCtrl {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Label titleLabel;

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
    private Button stopCreatingBoardButton;

    @FXML
    private Button createBoardButton;

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

        // check if the user has entered a board name
        String boardTitle = boardTitleTextField.getText();
        if(boardTitle.equals("")){
            warningname.setText("\"Required field!\"");
            return;
        }

        // checks if the user entered a board key
        String boardKey = boardKeyTextField.getText();
        if(boardKey.length() == 0)
        {
            warningkey.setText("\"Required field!\"");
            return;
        }

        // checks if the user entered a key that is already in the database
        if(server.getBoardByKey(boardKey) != null) {
            warningkey.setText("There already exists a \nboard with this key!");
            return;
        }

        if (boardKey.contains(",")) {
            warningkey.setText("Board key cannot contain\nspecial characters");
            return;
        }

        // creates the board
        Board newBoard = new Board(boardTitle);
        newBoard.setBoardkey(boardKey);

        // add the board to the database and go back to select board page
        server.addBoard(newBoard);
        mainCtrl.saveBoardByKey(newBoard.getBoardkey());
        refresh();
        mainCtrl.closeLocker();
        mainCtrl.showSelect();
    }

    /**
     * Method to stop creating a new board and shows the selection
     */
    @FXML
    public void stopCreatingBoardButtonHandler() {
        // go back to the selection scene
        refresh();
        mainCtrl.closeLocker();
        mainCtrl.showSelect();
    }

    /**
     * refresh method
     */
    public void refresh() {
        warningname.setText("");
        warningkey.setText("");
        boardTitleTextField.setText("");
        boardKeyTextField.setText("");
        shortcut();
    }

    /**
     * title label getter
     * @return label
     */
    public Label getTitleLabel() {
        return titleLabel;
    }

    /**
     * warning name label getter
     * @return label
     */
    public Label getWarningNameLabel() {
        return warningname;
    }

    /**
     * warning key label getter
     * @return label
     */
    public Label getWarningKeyLabel() {
        return warningkey;
    }

    /**
     * board title label getter
     * @return label
     */
    public Label getBoardTitleLabel() {
        return boardTitleLabel;
    }

    /**
     * board key label getter
     * @return label
     */
    public Label getBoardKeyLabel() {
        return boardKeyLabel;
    }

    /**
     * board title text field getter
     * @return textfield
     */
    public TextField getBoardTitleTextField() {
        return boardTitleTextField;
    }

    /**
     * board key text field getter
     * @return textfield
     */
    public TextField getBoardKeyTextField() {
        return boardKeyTextField;
    }

    /**
     * stop creating board button getter
     * @return button
     */
    public Button getStopCreatingBoardButton() {
        return stopCreatingBoardButton;
    }

    /**
     * create board button getter
     * @return button
     */
    public Button getCreateBoardButton() {
        return createBoardButton;
    }

    /**
     * title string getter
     * @return String
     */
    public String getTitleLabelString() {
        String label = titleLabel.getText();
        return label;
    }

    /**
     * warning name string getter
     * @return String
     */
    public String getWarningNameLabelString() {
        String label = warningname.getText();
        return label;
    }

    /**
     * warning key string getter
     * @return String
     */
    public String getWarningKeyLabelString() {
        String label = warningkey.getText();
        return label;
    }

    /**
     * board title label getter
     * @return String
     */
    public String getBoardTitleLabelString() {
        String label = boardTitleLabel.getText();
        return label;
    }

    /**
     * board key label getter
     * @return String
     */
    public String getBoardKeyLabelString() {
        String label = boardKeyLabel.getText();
        return label;
    }

    /**
     * board title text field string getter
     * @return String
     */
    public String getBoardTitleTextFieldString() {
        String label = boardTitleTextField.getText();
        return label;
    }

    /**
     * board key text field getter
     * @return String
     */
    public String getBoardKeyTextFieldString() {
        String title = boardKeyTextField.getText();
        return title;
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        titleLabel.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }

}
