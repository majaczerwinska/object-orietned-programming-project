package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

//TODO
public class CardListComponent extends VBox{

    @FXML
    private VBox vboxCards;

    @FXML
    private Label labelTitle;

    private int listId;
    private int boardId;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * constructor for cardListComponent
     * @param listId
     * @param boardId
     * @param server
     * @param mainCtrl
     */
    public CardListComponent(int listId,int boardId,ServerUtils server, MainCtrl mainCtrl) {
        super();
        this.listId=listId;
        this.boardId=boardId;
        this.mainCtrl = mainCtrl;
        this.server = server;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/CardListComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardListComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Gets the vbox where the cards will be presented
     * @return return the vbox
     */
    public VBox getVboxCards() {
        return vboxCards;
    }

    /**
     * Sets the title of the cardList
     * @param title the title of the cardList
     */
    public void setTitle(String title){
        labelTitle.setText(title);
    }

    /**
     * takes you to scene for editing the list's name
     * @param mouseEvent - click
     */
    public void editTitle(MouseEvent mouseEvent){
        mainCtrl.showListEdit(listId,boardId);
    }
}
