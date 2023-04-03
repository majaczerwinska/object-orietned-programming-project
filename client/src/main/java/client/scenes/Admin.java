package client.scenes;

import client.utils.ServerUtils;
import commons.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.*;

public class Admin {

    private MainCtrl mainCtrl;

    private ServerUtils server;

    private String ip;

    private List<Board> boardElementList;


    @FXML
    private Text titleText;


    @FXML
    private TextArea sqlOutput;


    @FXML
    private TextField boardName;


    @FXML
    private TextField boardKey;


    @FXML
    private TextField boardPassword;


    @FXML
    private ListView<String> boardList;


    @FXML
    private TextField sqlQuery;


    @FXML
    private Button sqlSend;


    @FXML
    private Button saveChangesButton;


    @FXML
    private Button deleteBoardButton;


    @FXML
    private Button backButton;


    @FXML
    private Text saveText;


    @FXML
    private Button enterBoard;


    @Inject
    public Admin(ServerUtils serverUtils, MainCtrl mainCtrl) {
        this.server = serverUtils;
        this.mainCtrl = mainCtrl;
    }

    public void refresh(String ip) {
        this.ip = ip;
        titleText.setText("Boards for Server: "+ip);
        this.boardElementList = server.getBoards();
        List<String> boardTitlesString = new ArrayList<>();
        for (Board b : this.boardElementList) {
            String s = "Board #"+b.getId();
            s+="\nName: "+b.getName();
            s+="\nKey: " +b.getBoardkey();
            boardTitlesString.add(s);
        }
        ObservableList<String> boardObl = FXCollections.observableList(boardTitlesString);
        boardList.setItems(boardObl);
        boardList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        saveText.setText("");
        saveText.setFill(Color.BLACK);
    }

    public void sendQuery() {
        String query = sqlQuery.getText();
    }


    public void updateTextFields() {
        String item = boardList.getSelectionModel().getSelectedItem();
        Board board = getBoardFromString(item);
        boardName.setText(board.getName());
        boardKey.setText(board.getBoardkey());
        boardPassword.setText(board.getPassword());
    }

    public void saveTextFields() {
        String name = boardName.getText();
        String key = boardKey.getText();
        String password = boardPassword.getText();
        Board b = getBoardFromString(boardList.getSelectionModel().getSelectedItem());
        if (b!= null) {
            if (b.getBoardkey().equals("public")){
                refresh(this.ip);
                saveText.setText("You cannot edit the public board!");
                saveText.setFill(Color.INDIANRED);
                return;
            }
            b.setName(name);
            b.setPassword(password);
            b.setBoardkey(key);
            server.editBoard(b.getId(), b);
            refresh(this.ip);
            saveText.setText("Saved!");
            saveText.setFill(Color.DARKGREEN);
        } else {
            System.out.println("Tried to save null board");
        }
    }

    public void deleteBoard() {
        Board b = getBoardFromString(boardList.getSelectionModel().getSelectedItem());
        if (b.getBoardkey().equals("public")){
            saveText.setText("You cannot edit the public board!");
            saveText.setFill(Color.INDIANRED);
            refresh(this.ip);
            return;
        }
        server.deleteBoard(b.getId());
        this.refresh(this.ip);
        saveText.setText("Deleted board "+b.getName());
        saveText.setFill(Color.BLUEVIOLET);
    }

    private Board getBoardFromString(String item) {
        for (Board b : this.boardElementList) {
            String s = "Board #"+b.getId();
            s+="\nName: "+b.getName();
            s+="\nKey: " +b.getBoardkey();
            if (s.equals(item)) return b;
        }
        return null;
    }

    public void goBack() {
        mainCtrl.showServerSelect();
    }


    public void enterBoard() {
        Board b = getBoardFromString(boardList.getSelectionModel().getSelectedItem());
        if (b==null) {
            this.refresh(this.ip);
            saveText.setText("Invalid board selected");
            saveText.setFill(Color.ORANGERED);
            return;
        }
        try {
            System.out.println("joining board #" + b.getId());
            System.out.println(b);
            mainCtrl.showBoardOverview(b.getId(), true);
            mainCtrl.subscribeToBoard(b.getId());
            mainCtrl.subscribeToTagsFromBoard(b.getId());
        } catch (Exception e) {
            refresh(this.ip);
            saveText.setText("Could not join: "+e.getMessage());
            saveText.setFill(Color.INDIANRED);
            e.printStackTrace();
        }
    }
}
