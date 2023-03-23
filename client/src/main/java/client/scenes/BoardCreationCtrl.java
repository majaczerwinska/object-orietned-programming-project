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
    private Label errorLabel;

    /**
     * board creator controller constructor
     * @param server server
     * @param mainCtrl main controller
     */
    @Inject
    public BoardCreationCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * hash hex code 
     * @param str string to be turned into a has value
     * @return int
     */
    public int hashHexCode(String str) { 
        // java String#hashCode
        int hash = 0;
        for (var i = 0; i < str.length(); i++) {
           hash = str.charAt(i) + ((hash << 5) - hash);
        }
        return hash;
    } 

    /**
     * create board button handler
     */
    @FXML
    public void createBoardButtonClickHandler() {
        String boardTitle = boardTitleTextField.getText();
        Board newBoard = new Board(boardTitle);
        String boardKey = boardKeyTextField.getText();
        newBoard.setBoardkey(boardKey);
        String colorValue = colorTextField.getText();
        if(colorValue.length() == 0)
        {
            // color gets defaulted to white
            colorValue = "ffffff";
        }
        if(colorValue.length() != 6 && colorValue.length() != 0)
        {
            // show error
            errorLabel.setText("The hexcode has the wrong amount of digits.");
        }
        else {
            boolean okay = true;
            for(int i = 0; i < 6; i++)
            {
                if(!(colorValue.charAt(i) <= 102 && 
                    colorValue.charAt(i) >= 97) || 
                    (colorValue.charAt(i) <= 57 && 
                    colorValue.charAt(i) >= 48))
                {
                    errorLabel.setText("The hexcode has the wrong format.");
                    okay = false;
                }
            }
            if(okay == true) 
            {
                // color assigned to the board
                newBoard.setColor(hashHexCode(colorValue));
            }
        }
        if(passwordRequiredRadioButton.isSelected())
        {
            String passwordValue = passwordTextField.getText();
            newBoard.setPassword(passwordValue);
        }
        //server.addBoard(newBoard);
    }

    /**
     *
     */
    public void refresh() {
    }
}
