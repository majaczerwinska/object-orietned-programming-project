package client.scenes;

import client.utils.ServerUtils;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;


public class TagPopUpCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private int boardID;

    @FXML
    private Label title;

    @FXML
    private TextField name;

    @FXML
    private Button goBackButton;

    @FXML
    private Button plusButton;

    /**
     * help controller constructor method
     * @param mainCtrl main controller
     * @param server server utils functions
     */
    @Inject
    public TagPopUpCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * refresh method
     */
    public void refresh() {
    }

    /**
     * Sets boardID to the ID of the board
     * @param boardID the ID of the board
     */
    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    /**
     * Adds a tag to the database and empties the textField.
     */
    @FXML
    public void addTag() {
        if (name.getText() != null) {
            Tag tag = new Tag(name.getText());
            server.addTag(tag, boardID);
            name.setText("");
        }
    }

//    /**
//     * Closes the pop-up
//     */
//    public void back() {
//        Stage stage = (Stage) title.getScene().getWindow();
//        stage.close();
//    }

    /**
     * Closes the pop-up
     */
    public void back() {
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }

    /**
     * title label getter
     * @return Label
     */
    @FXML
    public Label getTitle() {
        return title;
    }

    /**
     * tag name text field getter
     * @return TextField
     */
    @FXML
    public TextField getName() {
        return name;
    }

    /**
     * go back button getter
     * @return Button
     */
    @FXML
    public Button getGoBackButton() {
        return goBackButton;
    }

    /**
     * new tag button getter
     * @return Button
     */
    @FXML
    public Button getPlusButton() {
        return plusButton;
    }



}
