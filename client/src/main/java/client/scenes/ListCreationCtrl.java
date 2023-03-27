package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.CardList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ListCreationCtrl {

    @FXML
    private TextField name;
    @FXML
    private Label warning;
    @FXML
    private Button createbutton;
    @FXML
    private Button backbutton;
    @FXML
    private ColorPicker palette;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int boardID;

    /**
     *Creates a list
     * @param server the server
     * @param mainCtrl the controller
     */
    @Inject
    public ListCreationCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    public void create(ActionEvent event){
        if(name.getText().equals("")) {
            warning.setText("Required field!");
        }
        else{
            CardList list = new CardList(name.getText());
            list.setColor(MainCtrl.colorParseToInt(palette.getValue()));
            server.createList(boardID, list);
            mainCtrl.showBoardOverview(boardID);
        }

    }


    public void cancel(ActionEvent event){
        mainCtrl.showBoardOverview(boardID);
    }


}
