package client.components;

import client.scenes.MainCtrl;
import commons.Card;
import commons.CardList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

//TODO
public class CardListComponent extends VBox{

    private int listID;

    private CardList self;

    private MainCtrl mainCtrl;
    @FXML
    private VBox vboxCards;

    @FXML
    private Label labelTitle;



    /**
     * The constructor for the component
     */
    public CardListComponent() {
        super();
        mainCtrl = new MainCtrl();
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
     * updates list
     * @param cardList new cardlist data
     */
    public void setData(CardList cardList){
        labelTitle.setText(cardList.getName());
        listID = cardList.getId();
        self = cardList;
    }

    @FXML
    public void addEnterKeyListener() {
        mainCtrl.addEnterKeyListener();
    }
}
