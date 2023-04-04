package client.scenes;


import client.services.BoardSelectService;
import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BoardSelectCtrl {

    @FXML
    private ListView<Board> list;
    private ObservableList<Board> items = FXCollections.observableArrayList();

    @FXML
    private Button create;
    @FXML
    public Label warning;

    @FXML
    private Button btnRemove;

    @FXML
    private TextField boardKey;

    @FXML
    private Button join;

    @FXML
    private Button back;

    @FXML
    private Button adminButton;
    private Text doubleClickText;

    @FXML
    private Label joinExistingLabel;

    @FXML
    private Label enterKeyLabel;

    @FXML
    private Label orYouLabel;

    private final BoardSelectService service;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     *
     * @param service the services for this controller
     * @param server
     * @param mainCtrl
     */
    @Inject
    public BoardSelectCtrl(BoardSelectService service, ServerUtils server, MainCtrl mainCtrl) {
        this.service = service;
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * adds a board to the list of known boards
     * 
     * @param key the board's key
     */
    public void saveBoardKey(String key) {
        service.saveBoardKey(key);
    }

    /**
     * updates contents of a list of joined boards
     */
    public void refresh() {
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Board> boardList = FXCollections.observableList(service.getBoards(server));
        list.setItems(boardList);

    }

    /**
     * !not tested!
     * checks if the board you want to join exists, if yes saves and joins it,
     * otherwise throws exception and popup window
     */
    public void saveBoardButton() {
        String key = boardKey.getText();
        if(key.equals("") || key==null){
           warning.setText("The board with given key cannot be found!");
            return;
        }
        try {
            Board board = server.getBoardByKey(key);
            if (board == null) {

                warning.setText("The board with given key cannot be found!");
                return;
            }
            saveBoardKey(board.getBoardkey());
            warning.setText("");
            System.out.println("saved board key " + key + " and entering board. details:\n"+board);

        } catch (Exception e) {
            e.printStackTrace();
            mainCtrl.showPopup();
        }
        refresh();
    }

    /**
     * Takes you to board creation scene
     */
    public void goCreate() {
        mainCtrl.showBoardCreation();
    }

    /**
     * takes you back to the landing page
     */
    public void back() {
        mainCtrl.showServerSelect();
    }

    /**
     * event handler for clicking
     * 
     * @param event click details
     */
    @FXML
    public void onListElementClick(MouseEvent event) {
        refresh();
        if (event.getClickCount() == 2) {
            joinBoard();
        }
    }

    /**
     * joins the board on double click
     */
    public void joinBoard() {
        Board selection = list.getSelectionModel().getSelectedItem();
        if (selection == null) {
            System.out.println("selected board appears to be null, in joinBoard() of BoardSelectCtrl");
            return;
        }
        System.out.println("joining board #" + selection.id);
        System.out.println(selection);
        mainCtrl.showBoardOverview(selection.id);
        mainCtrl.subscribeToBoard(selection.id);
        mainCtrl.subscribeToTagsFromBoard(selection.id);
    }

    /**
     * Removes a board
     */
    public void removeBoard() {
        String boardKey = list.getSelectionModel().getSelectedItem().getBoardkey();
        service.removeBoardKey(boardKey);
        refresh();
    }

    /**
     * create button getter
     * @return Button
     */
    public Button getCreateButton() {
        return create;
    }

    /**
     * remove button getter
     * @return Button
     */
    public Button getRemoveButton() {
        return btnRemove;
    }

    /**
     * board key text field getter
     * @return TextField
     */
    public TextField getBoardKeyTextField() {
        return boardKey;
    }

    /**
     * join button getter
     * @return Button
     */
    public Button getJoinButton() {
        return join;
    }

    /**
     * go back button getter
     * @return Button
     */
    public Button getBackButton() {
        return back;
    }

    /**
     * double-click text getter
     * @return Text
     */
    public Text getDoubleClickText() {
        return doubleClickText;
    }

    /**
     * join existing board label getter
     * @return Label
     */
    public Label getJoinExistingLabel() {
        return joinExistingLabel;
    }

    /**
     * enter the key label getter
     * @return Label
     */
    public Label getEnterKeyLabel() {
        return enterKeyLabel;
    }

    /**
     * Or You Can label getter
     * @return Label
     */
    public Label getOrYouLabel() {
        return orYouLabel;
    }

}
