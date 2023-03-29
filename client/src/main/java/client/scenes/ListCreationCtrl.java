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

    //public int boardId;

    @FXML
    private TextField name;
    @FXML
    private Label warning;
    @FXML
    private Button createbutton;
    @FXML
    private Button backbutton;
    //@FXML
    //private ColorPicker palette;

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


    /**
     * Creates a new cardList with a given title and adds it to the public board
     * @param event - click
     */
    public void create(ActionEvent event){
        if(name.getText().equals("")) {
            warning.setText("Required field!");
        }
        else{
            CardList list = new CardList(name.getText());
            //list.setColor(MainCtrl.colorParseToInt(palette.getValue()));
            server.createList(boardID, list);
            name.setText("");
            mainCtrl.showBoardOverview(boardID);
        }

    }

    /**
     * takes you back to the overview
     * @param event - click
     */
    public void cancel(ActionEvent event){
        mainCtrl.showBoardOverview(boardID);


    }


}
