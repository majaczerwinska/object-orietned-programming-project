package client.scenes;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

//import javafx.scene.shape.Circle;


public class EditBoardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField name;
    @FXML
    private TextField color;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML
    private TextArea description;
    @FXML
    private ListView<String> tags;
    @FXML
    private Text text;


    public int boardId;

//    @FXML
//    private Circle bigBlueButton;

    @FXML
    protected void onBigBlueButtonClick() {
//        bigBlueButton.
    }
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
        color.setText(String.valueOf(board.getbColor()));
    }

    /**
     * When this method is called the window that has the button in it will be left.
     */
    @FXML
    private void exitButton(){
        mainCtrl.showBoardOverview(boardId);
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
        int boardColor = Integer.parseInt(this.color.getText());


        Board board = server.getBoard(boardId);
        board.setName(name);
        board.setbColor(boardColor);

        if (name.isEmpty() || color.getText().isEmpty()) {
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


}
