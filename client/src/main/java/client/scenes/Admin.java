package client.scenes;

import client.services.AdminService;
import client.utils.ServerUtils;
import commons.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.*;

@Controller
public class Admin {

    private final AdminService service;


    private String token;

    private MainCtrl mainCtrl;

    private ServerUtils server;

    private String ip;

    private List<Board> boardElementList;


    @FXML
    private Text titleText;


    @FXML
    private ScrollPane sqlOutput;


    @FXML
    private VBox vboxTableContent;

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


    /**
     * Constructor for admin panel
     * @param serverUtils injected server instance
     * @param mainCtrl injected mainctrl instance
     * @param adminService the service for generating boards
     */
    @Inject
    public Admin(ServerUtils serverUtils, MainCtrl mainCtrl, AdminService adminService) {
        this.server = serverUtils;
        this.mainCtrl = mainCtrl;
        this.service = adminService;
    }


    /**
     * refresh the admin panel
     * gets boards from server anew
     * resets text fields
     * @param ip the ip of the server connected to
     */
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
        boardName.setText("");
        boardKey.setText("");
        boardPassword.setText("");
    }


    /**
     * update text fields for board info
     * called on click for the list view of boards
     */
    public void updateTextFields() {
        String item = boardList.getSelectionModel().getSelectedItem();
        Board board = service.getBoardFromString(item, this.boardElementList);
        if (board != null) {
            boardName.setText(board.getName());
            boardKey.setText(board.getBoardkey());
            boardPassword.setText(board.getPassword());
        }
    }


    /**
     * use text field info to update the database
     * with new board name, key, and password fields.
     * checks against public board to prevent modiying it
     */
    public void saveTextFields() {
        String name = boardName.getText();
        String key = boardKey.getText();
        String password = boardPassword.getText();
        Board b = service.getBoardFromString(boardList.getSelectionModel().getSelectedItem(), this.boardElementList);
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


    /**
     * sends a delete request to the server for the board currently selcted
     */
    public void deleteBoard() {
        Board b = service.getBoardFromString(boardList.getSelectionModel().getSelectedItem(), this.boardElementList);
        if (b==null) {
            refresh(this.ip);
            saveText.setText("board selection is null");
            saveText.setFill(Color.ORANGERED);
            return;
        }
        if (b.getBoardkey().equals("public")){
            refresh(this.ip);
            saveText.setText("You cannot delete the public board!");
            saveText.setFill(Color.INDIANRED);
            return;
        }
        server.deleteBoard(b.getId());
        this.refresh(this.ip);
        saveText.setText("Deleted board "+b.getName());
        saveText.setFill(Color.BLUEVIOLET);
    }


    /**
     * go back to server select screen
     */
    public void goBack() {
        mainCtrl.showServerSelect();
    }


    /**
     * enter the selected board as an admin,
     * meaning no matter its password state you have it unlocked
     */
    public void enterBoard() {
        Board b = service.getBoardFromString(boardList.getSelectionModel().getSelectedItem(), this.boardElementList);
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


    /**
     * 1. send the sql query from the text field to the server
     * 2. get the response and generate a table out of it
     * 3. display the table within the scrollpane of the admin panel
     * //todo multiple queries at once
     */
    public void sendQuery() {
        String query = sqlQuery.getText();
     //List<List<Map<String, Object>>> res = server.executeSQLQuery(query, this.token);//todo multiple queries at once
        List<Map<String, Object>> outputTable = server.executeSQLQuery(query, this.token);
        vboxTableContent.getChildren().clear();
        if (outputTable == null) {
            Text errorText = new Text("Invalid sql query");
            errorText.setFill(Color.ORANGERED);
            vboxTableContent.getChildren().add(errorText);
            return;
        }

        TableView<Map<String, Object>> table = service.generateTable(outputTable);
        vboxTableContent.getChildren().add(table);
    }

    /**
     * set the token used for secure SQL queries.
     * reset upon server start,
     * sent to the client upon successful authentication
     * @param token string random token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
