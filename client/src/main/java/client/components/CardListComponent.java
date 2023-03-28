package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;


import commons.Card;
import commons.CardList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import commons.CardList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


import java.io.IOException;

//TODO
public class CardListComponent extends VBox{

   // private int listID;

    private CardList self;

    private MainCtrl mainCtrl;
    private ServerUtils server;
    @FXML
    private VBox vboxCards;
    @FXML
    private Button editlist;
    @FXML
    private VBox vboxscene;
    @FXML
    private Button deletebutton;
    @FXML
    private Button addcard;

    @FXML
    private Label labelTitle;







    private int listId;
    private int boardId;


    /**
     * constructor for cardListComponent
     * @param listId
     * @param boardId
     * @param mainCtrl
     */
    public CardListComponent(MainCtrl mainCtrl, int boardId, int listId) {

        super();
        this.boardId = boardId;
       // this.listID = listID;
        server = new ServerUtils();
        this.listId=listId;
        this.boardId=boardId;
        this.mainCtrl = mainCtrl;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/CardListComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardListComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


//        setOnDragOver(event ->{
//
//                if (event.getGestureSource() != this &&
//                        event.getDragboard().hasString()) {
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                }
//
//                event.consume();
//
//        });
//        setOnDragEntered(event -> {
//                if (event.getGestureSource() != this &&
//                        event.getDragboard().hasString()) {
//                    this.setTitle("Color.GREEN");
//                }
//                event.consume();
//
//        });
//        setOnDragExited(event -> {
//
//                this.setTitle("Color.BLACK");
//                event.consume();
//
//        });

    }

    /**
     * Adding a card
     */
    public void addCard() {


         Card c = mainCtrl.createCard(listId);
        System.out.println(boardId + "carlistcomp");
         mainCtrl.showCard(c.getId(), boardId);
    }

    /**
     * Deleting a list
     */
    public void delete(){


        server.deleteCardList(listId,boardId);
        mainCtrl.refreshBoardOverview();
    }


    /**
     * changes the list ot which the card belonds to when its dropped
     * @param event the event of dropping the drag
     */
//    public void onDragDropped(DragEvent event){
//        Dragboard db = event.getDragboard();
//        boolean success = false;
//        if (db.hasString()) {
//            Card c = server.getCard(Integer.parseInt(db.getString()));
//            System.out.println(c);
//            System.out.println(listID);
//            server.changeListOfCard(listID,c);
//            success = true;
//        }
//        event.setDropCompleted(success);
//        Platform.runLater(()->{
//                mainCtrl.refreshBoardOverview();
//        });
//
//        event.consume();
//
//    }
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
    public void editTitle(MouseEvent mouseEvent) {
        mainCtrl.showListEdit(listId, boardId);
    }

    /**
     * updates list
     * @param cardList new cardlist data
     */
    public void setData(CardList cardList){
        labelTitle.setText(cardList.getName());
        listId = cardList.getId();
        self = cardList;
    }

    /**
     * add event listener for enter key typed when mouse enters
     */
    @FXML
    public void addEnterKeyListener() {
        System.out.println("add key listener called in card list component");
        mainCtrl.addEnterKeyListener(listId);
    }

}
