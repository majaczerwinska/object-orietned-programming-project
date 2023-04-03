package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class BoardSelectCtrl {

    private final String saveID = "boardSave";
    private Preferences prefs;


    @FXML
    private ListView<Board> list;
    private ObservableList<Board> items = FXCollections.observableArrayList();

//    @FXML
//    private TextField newName;
    @FXML
    private Button create;

    @FXML
    private Button btnRemove;
    @FXML
    private TextField boardKey;
    @FXML
    private Button join;

    @FXML
    private Button back;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public BoardSelectCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.prefs = Preferences.userRoot().node(this.getClass().getName());
    }


    /**
     * adds a board to the list of known boards
     * @param key the board's key
     */
    public void saveBoardKey(String key) {
        if (key == null || key.length() < 1) {
            System.out.println("Invalid key saved");
            return;
        }
        String keys = prefs.get(saveID, "public");
        if(keys.contains(key)){
            System.out.println(key + "this key is already saved");
            return;
        }
        keys += "," + key;
        prefs.put(saveID, keys);
    }


    /**
     * Get the list of saved board keys from preferences
     * @return a list of strings
     */
    public List<String> getBoardKeys() {
        String keys = prefs.get(saveID, "public");
        List<String> keyList = new ArrayList<>(List.of(keys.split(",")));
        System.out.println(keyList);
        return keyList;
    }


    /**
     * Get board instances from database using saved board keys
     * @return the list of board elements that exist in the database
     */
    public List<Board> getBoards() {
        List<Board> b = new ArrayList<>();
        List<String> keys = getBoardKeys();
        System.out.println("Getting board keys from save, results = "+keys);
        for (String key : keys) {
            System.out.println("Getting board instance from server for key="+key);
            Board res = server.getBoardByKey(key);
            if (res != null) {
                b.add(res);
            } else {
                removeBoardKey(key);
            }
        }
        return b;
    }


    /**
     * Remove a board key from the save
     * @param key key to remove
     */
    public void removeBoardKey(String key) {
        if (key == null || key.length() < 1) {
            System.out.println("Invalid key removal");
            return;
        }
        String keys = prefs.get(saveID, "public");
        if (!keys.contains(",")) {
            prefs.put(saveID, "public");
            return;
        }
        System.out.println("removing key. before: " + keys);
        keys = keys.replace(key+",", "");
        keys = keys.replace(","+key, "");
        System.out.println("after: " + keys);
        prefs.put(saveID, keys);
    }


    /**
     * updates contents of a list of joined boards
     */
    public void refresh() {
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//            try{
//                items.clear();
//                List<Board> boards = server.getBoards();
//                for(int i = 0; i<boards.size(); i++){
//                    items.add(boards.get(i));
//                }
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
        ObservableList<Board> boardList = FXCollections.observableList(getBoards());
        list.setItems(boardList);


    }

//    @FXML
//    public void create(MouseEvent mouseEvent){
//        Board board = new Board(newName.getText());
//        server.addBoard(board);
//        refresh();
//    }

    /**
     * !not tested!
     * checks if the board you want to join exists, if yes saves and joins it,
     * otherwise throws exception and popup window
     */
    public void saveBoardButton(){
        String key = boardKey.getText();
        try {
            Board board = server.getBoardByKey(key);
            if (board == null) {
                System.out.println("Tried to add & join board with key '"+key+"' but server returned null");
                mainCtrl.showPopup();
                return;
            }
            saveBoardKey(board.getBoardkey());
            System.out.println("saved board key " + key + " and entering board. details:\n"+board);
//            mainCtrl.showBoardOverview(board.id);
        } catch (Exception e) {
            e.printStackTrace();
            mainCtrl.showPopup();
        }
        refresh();
    }

    /**
     * (should) take you to board creation scene
     */
    public void goCreate(){
        mainCtrl.showBoardCreation();
    }

    /**
     * takes you back to the landing page
     */
    public void back(){
        mainCtrl.showServerSelect();
    }

    /**
     * event handler for clicking
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
        if (selection==null) {
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
    public void removeBoard(){
        String boardKey = list.getSelectionModel().getSelectedItem().getBoardkey();
        removeBoardKey(boardKey);
        refresh();
    }
}
