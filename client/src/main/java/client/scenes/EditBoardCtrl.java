package client.scenes;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField name;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML
    private Label nameLabel;
    @FXML
    private Label titleLabel;


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

        name.setText(board.getName());
    }

    /**
     * When this method is called the window that has the button in it will be left.
     */
    @FXML
    private void exitButton(){
        mainCtrl.showBoardOverview(boardId);
        mainCtrl.closeLocker();
    }

//    /**
//     * Adds a task to the database
//     */
//    @FXML
//    private void addTask(){
//        Card c = server.getCard(cardId);
//        List<Task> l = c.getTasks();
//        l.add(new Task(newTask.getText()));
//        c.setTasks(l);
//        server.editCard(cardId, c);
//    }




    /**
     * Saves the card and all changes made to it to the database.
     */
    @FXML
    private void editBoard() {
        String name = this.name.getText();
        //int boardColor = Integer.parseInt(this.color.getText());

//        Board board = new Board(name);
//        board.setId(boardId);
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
        mainCtrl.showBoardOverview(boardId);
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
     * Getter for the exit Button.
     * @return The Button object for the exit button.
     */
    @FXML
    public Button getExit() {
        return exit;
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




}
