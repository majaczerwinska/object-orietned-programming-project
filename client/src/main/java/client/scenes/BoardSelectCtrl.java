package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class BoardSelectCtrl{
    @FXML
    private ListView<Board> list;
    private ObservableList<Board> items = FXCollections.observableArrayList();

//    @FXML
//    private TextField newName;
    @FXML
    private Button create;

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
    public BoardSelectCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * updates contents of a list of joined boards
     */
    public void refresh() {
            try{
                items.clear();
                List<Board> boards = server.getBoards();
                for(int i = 0; i<boards.size(); i++){
                    items.add(boards.get(i));
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        list.setItems(items);


    }

//    @FXML
//    public void create(MouseEvent mouseEvent){
//        Board board = new Board(newName.getText());
//        server.addBoard(board);
//        refresh();
//    }

    /**
     * !not finished!
     * checks if the board you want to join exists
     * @param mouseEvent - click
     */
    @FXML
    public void join(MouseEvent mouseEvent){
       Board board = server.getBoard(Integer.parseInt(boardKey.getText()));
       if(board == null){
            mainCtrl.showPopup();
       }

    }

    /**
     * (should) take you to board creation scene
     * @param mouseEvent - click
     */
    @FXML
    public void goCreate(MouseEvent mouseEvent){
        mainCtrl.showBoardCreation();
    }

    /**
     * takes you back to the landing page
     * @param mouseEvent - click
     */
    @FXML
    public void back(MouseEvent mouseEvent){
        mainCtrl.showLanding();
    }


}
