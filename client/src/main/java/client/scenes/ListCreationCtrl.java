package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.CardList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ListCreationCtrl {

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
    public ListCreationCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Creates a new cardList with a given title and adds it to the public board
     * @param mouseEvent - click
     */
    public void create(MouseEvent mouseEvent){
        CardList list = new CardList(name.getText());
        server.createList(0, list);
    }

    /**
     * takes you back to the overview
     * @param mouseEvent - click
     */
    public void cancel(MouseEvent mouseEvent){
        mainCtrl.showPublicBoard();
    }
}
