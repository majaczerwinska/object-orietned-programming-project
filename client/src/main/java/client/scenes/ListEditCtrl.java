package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ListEditCtrl {

    public int boardId;
    public int listId;


    @FXML
    private TextField name;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public ListEditCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     *
     */
    public void setName(){
        name.setText(server.getCardList(listId).getName());
    }

    /**
     * renames your list
     */
    public void rename(){
        String newName = name.getText();
        server.editList(boardId, listId, newName);
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * takes you back to the board overview
     */
    public void cancel(){
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardId);
    }


}
