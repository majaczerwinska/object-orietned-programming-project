package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Card;
import commons.CardList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.IOException;

//TODO
public class CardListComponent extends VBox{

    private int listID;

    private CardList self;

    private MainCtrl mainCtrl;
    private ServerUtils server;
    @FXML
    private VBox vboxCards;
    @FXML
    private VBox vboxscene;

    @FXML
    private Label labelTitle;



    /**
     * The constructor for the component
     * @param mainCtrl the instance of maincontroller
     */
    public CardListComponent(MainCtrl mainCtrl) {
        super();
        server = new ServerUtils();
        this.mainCtrl = mainCtrl;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/CardListComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardListComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setOnDragOver(event ->{

                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();

        });
        setOnDragEntered(event -> {
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    this.setTitle("Color.GREEN");
                }
                event.consume();

        });
        setOnDragExited(event -> {

                this.setTitle("Color.BLACK");
                event.consume();

        });

    }

    public void OnDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Card c = server.getCard(Integer.parseInt(db.getString()));
            System.out.println(c);
            System.out.println(listID);
            server.changeListOfCard(listID,c);
            success = true;
        }
        event.setDropCompleted(success);
        Platform.runLater(()->{
                mainCtrl.refreshBoardOverview();
        });

        event.consume();

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

    /**
     * add event listener for enter key typed when mouse enters
     */
    @FXML
    public void addEnterKeyListener() {
        System.out.println("add key listener called in card list component");
        mainCtrl.addEnterKeyListener(listID);
    }
}
