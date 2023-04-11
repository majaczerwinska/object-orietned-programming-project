package client.scenes;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class EditBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField name;
    @FXML
    private Button save;

    @FXML
    private Label nameLabel;
    @FXML
    private Label titleLabel;

    @FXML
    private Button deleteBoardButton;

    public int boardId;

    /**
     *
     * @param server -
     * @param mainCtrl -
     */
    @Inject
    public EditBoardCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }
    


    /**
     * Start the scene and prepare the listview with all current tags.
     * @param boardId The ID of the board we get all tags from
     */
    @FXML
    public void openScene(int boardId) {
        this.boardId = boardId;
        Board board = server.getBoard(this.boardId);
        name.requestFocus();
        name.setText(board.getName());
        shortcut();
    }

    /**
     * When this method is called the window that has the button in it will be left.
     */
    @FXML
    public void exitButton(){
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * Saves the card and all changes made to it to the database.
     */
    @FXML
    private void editBoard() {
        String name = this.name.getText();

        Board board = server.getBoard(boardId);
        board.setName(name);

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name and a color");
            alert.showAndWait();
        }
        else {
           server.editBoard(this.boardId, board);
        }
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardId);
    }


    /**
     * button handler for delete
     */
    @FXML
    public void handleDeleteButtonClick() {
        if (deleteBoardButton.getText().equals("Are you sure?")){
            deleteBoardButton.setText("Delete Board");
            deleteBoard();
        } else {
            deleteBoardButton.setText("Are you sure?");
        }
    }

    /**
     *
     */
    @FXML
    public void mouseExitCancelDelete() {
        deleteBoardButton.setText("Delete Board");
    }


    /**
     * delete board and go back to board select
     */
    public void deleteBoard() {
        server.deleteBoard(boardId);
        mainCtrl.showSelect();
        mainCtrl.closeLocker();
    }


    /**
     * name textfield getter
     * @return TextField
     */
    @FXML
    public TextField getName() {
        return name;
    }

    /**
     * Getter for the save Button.
     * @return The Button object for the save button.
     */
    @FXML
    public Button getSave() {
        return save;
    }


    /**
     * Getter for the text title label.
     * @return The Label object for the text field.
     */
    @FXML
    public Label getTitleLabel() {
        return titleLabel;
    }

    /**
     * Getter for the nameLabel Label.
     * @return The Label object for the name label.
     */
    @FXML
    public Label getNameLabel() {
        return nameLabel;
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        save.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }


}
