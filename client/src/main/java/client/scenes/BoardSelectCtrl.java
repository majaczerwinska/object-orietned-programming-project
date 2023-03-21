package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

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


    @Inject
    public BoardSelectCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

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

    @FXML
    public void join(MouseEvent mouseEvent){
       Board board = server.getBoard(Integer.parseInt(boardKey.getText()));
       if(board == null){
            mainCtrl.showPopup();
       }

    }

    @FXML
    public void goCreate(MouseEvent mouseEvent){
        mainCtrl.showLanding();
    }

    @FXML
    public void back(MouseEvent mouseEvent){
        mainCtrl.showLanding();
    }


}
