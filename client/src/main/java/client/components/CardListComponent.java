package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;


import commons.Card;
import commons.CardList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


import java.io.IOException;

//TODO
public class CardListComponent extends VBox{
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

    private Line highlight = new Line();

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
        this.listId = listId;
        this.boardId = boardId;
        this.mainCtrl = mainCtrl;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/CardListComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardListComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        // Event handlers for dragging and dropping:
        ///////////////////////////////////////////

        // check if it is a valid drop target
        setOnDragOver(event -> {
            int position = getDroppedPosition(event);
            this.highlight = showDragDropLine(position, this.highlight);
                    if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
        });

        // event handler for when a dragged card enters a list, highlight where it will be dropped
        setOnDragEntered(event -> {

            setStyle("-fx-background-color: red");

        });
        // undo the highlight. called when a dragged card leaves the bounds of list
        setOnDragExited(event -> {
            setStyle("-fx-background-color: blue");
            hideDropLine(this.highlight);
        });

        // main handler, deals with a card being dropped into the list
        /*
        setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {

                // only accept move drags
                event.acceptTransferModes(TransferMode.MOVE);

                // Get the card being dragged
                int cardID = Integer.parseInt(dragboard.getString());
                Card draggedCard = (Card) server.getCard(cardID);
                CardComponent draggedCardComponent = mainCtrl.cardIdComponentMap.get(draggedCard.getId());


                // Get a reference to the source list and remove the card from it
                CardListComponent source = (CardListComponent) event.getSource();
                VBox sourceList = (VBox) source.getChildren().get(1);
                sourceList.getChildren().remove(draggedCardComponent);


                // Get a reference to the target list and add the card to it
                CardListComponent target = (CardListComponent) event.getGestureTarget();
                VBox targetList = (VBox) target.getChildren().get(1);
                System.out.println("drag drop: "+target +"\n\n" + this + "\n\n" + targetList + "\n\n" + vboxCards);
                targetList.getChildren().add(draggedCardComponent);

                server.changeListOfCard(target.listId, draggedCard);

                System.out.println("getting dropped card from server:");
                System.out.println(server.getCard(cardID));
                //updateCardPositionAttributes();

                success = true;
            } else {
                throw new RuntimeException("Dragging failed!!");
            }

            event.setDropCompleted(success);
            event.consume();


        });
        */

        setOnDragDropped(this::onDragDropped);

    }


    // Drag and drop:


    /**
     * get the position a card should be inserted in when dragged over a list
     * @param event drag event
     * @return int position
     */
    public int getDroppedPosition(DragEvent event) {
        if (event == null) return 0;
        double y = event.getY();
        if (y < 86) {
            return -1;
        }
        int listSize = vboxCards.getChildren().size();
        if (y > 75 + (listSize*68) - 19) {
            return listSize + 1;
        }

        return (int) Math.floor((event.getY() - 85) / (58 + 10)) + 1;
    }


    public void onDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Card c = server.getCard(Integer.parseInt(db.getString()));
            CardComponent cardComponent = mainCtrl.cardIdComponentMap.get(c.getId());
            Card newcard = server.changeListOfCard(listId,c);
            newcard.setPosition(getDroppedPosition(event));
            server.editCard(newcard.getId(), newcard);
            mainCtrl.cardIdComponentMap.remove(c.getId());
            mainCtrl.cardIdComponentMap.put(newcard.getId(), cardComponent);
            success = true;
            hideDropLine(this.highlight);
        }
        event.setDropCompleted(success);
        Platform.runLater(()->{
            mainCtrl.refreshBoardOverview();
            System.out.println(mainCtrl.cardIdComponentMap.toString());
            System.out.println(vboxCards.getChildren());
            //updateCardPositionAttributes();
        });

        event.consume();

    }


    public Line showDragDropLine(int position, Line highlight) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                highlight.setStroke(Color.BLACK);
                highlight.setStrokeWidth(2);
                hideDropLine(highlight);
                // set the start and end points of the line
                highlight.setStartX(20);
                highlight.setEndX(228);
                highlight.setStartY(position * 58);
                highlight.setEndY(position * 58);

                vboxCards.getChildren().add(position, highlight);
            }
        });


        return highlight;
    }

    public void hideDropLine(Line l) {
        vboxCards.getChildren().remove(l);
    }
    /**
     * set the position attribute of every card in the vbox
     * to its position in the vbox
     */
    public void updateCardPositionAttributes() {
        ObservableList<Node> vboxChildren = vboxCards.getChildren();
        for (int i = 0; i < vboxChildren.size(); i++) {
            Node node = vboxChildren.get(i);
            System.out.println(node);
            System.out.println(i);
            Integer id = mainCtrl.cardComponentToCardId((CardComponent) node);
            if (id == null) {
                System.out.println(mainCtrl.cardIdComponentMap);
                throw new RuntimeException("card component to card id returned null in updatecardpositions for node="+node);
            }
            System.out.println("card id="+id);

            Card c = server.getCard(id);
            c.setPosition(i);
            server.editCard(id, c);
        }
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

    /**
     * Adding a card
     */
    public void addCard() {

        System.out.println(vboxCards.getHeight());
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
