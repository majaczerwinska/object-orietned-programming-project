package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;


import commons.Card;
import commons.CardList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
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
    public Label labelTitle;

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
            String style = getStyle();
            style += "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 13, 0, 0, 6);";
            setStyle(style);
        });
        // undo the highlight. called when a dragged card leaves the bounds of list
        setOnDragExited(event -> {
            String style = getStyle();
            style =style.replace("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 13, 0, 0, 6);", "");
            setStyle(style);
            hideDropLine(this.highlight);
        });

        // main handler, deals with a card being dropped into the list
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
            return 0;
        }
        int listSize = vboxCards.getChildren().size();
        if (y > 75 + (listSize*68) - 19) {
            return listSize + 1;
        }

        return Math.min((int) Math.floor((event.getY() - 85) / (58 + 10)) + 1,listSize);
    }


    /**
     * Event handler for dropping a card in a list
     * @param event the drag event
     */
    public void onDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Card c = server.getCard(Integer.parseInt(db.getString()));
            CardComponent cardComponent = mainCtrl.cardIdComponentMap.get(c.getId());
            Card newcard = server.changeListOfCard(listId,c);

            System.out.println("\n\n\n\n\n\n "+getDroppedPosition(event));
            server.setPosition(newcard,getDroppedPosition(event));
            //newcard.setPosition(getDroppedPosition(event));
            System.out.println("\n\nSetting new card position to " +
                    "p="+getDroppedPosition(event)+" for card="+newcard+"\n\n");
            //server.editCard(newcard.getId(), boardId, newcard);


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


    /**
     * Show the horizontal line indicating where the card will be dropped
     * @param position the position to place the line in
     * @param highlight the highlight (line) element from the cardlistcomponent class
     * @return the highlight component with its new values
     */
    public Line showDragDropLine(int position, Line highlight) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                highlight.setStroke(MainCtrl.colorParseToFXColor(server.getBoard(boardId).getbColor()));
                highlight.setStrokeWidth(2);
                highlight.setStyle("-fx-end-margin: -80");
                hideDropLine(highlight);
                // set the start and end points of the line
                highlight.setStartX(20);
                highlight.setEndX(228);
                highlight.setStartY(position * 58);
                highlight.setEndY(position * 58);

                int p = Math.min(position, vboxCards.getChildren().size());
                vboxCards.getChildren().add(p, highlight);
            }
        });


        return highlight;
    }

    /**
     * hide the highlight line
     * @param l the line element
     */
    public void hideDropLine(Line l) {
        vboxCards.getChildren().remove(l);
    }


    /**
     * Adding a card
     */
    public void addCard() {

        System.out.println(vboxCards.getHeight());
         Card c = mainCtrl.createCard(listId);
        System.out.println(boardId + "carlistcomp");
         mainCtrl.showCard(c.getId(), listId, boardId, false);
    }

    /**
     * Deleting a list
     */
    public void delete(){
        server.deleteCardList(listId,boardId);
        mainCtrl.refreshBoardOverview();
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
     * @param e - click
     */
    public void editTitle(ActionEvent e) {
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

    /**
     * sets font color for list's title
     * @param color - the new color
     */
    @FXML
    public void colorFont(int color){
        String hexColor = String.format("#%06X", (0xFFFFFF & color));
        labelTitle.setStyle("-fx-text-fill: " + hexColor);
    }

    /**
     * Disables the write mode on lists
     */
    public void readonly(){
        addcard.setOnAction(event->{
            mainCtrl.showWarning(boardId);
            return;
        });
        deletebutton.setOnAction(event->{
            mainCtrl.showWarning(boardId);
            return;
        });
        editlist.setOnAction(event->{
            mainCtrl.showWarning(boardId);
            return;
        });

    }


    /**
     * returns the id of the list
     * @return the id of the list
     */
    public int getListId(){
        return this.listId;
    }
}
