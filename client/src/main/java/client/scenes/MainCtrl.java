/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.components.CardComponent;
import client.components.CardListComponent;
import commons.Card;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MainCtrl {

    private Stage primaryStage;

    private LandingCtrl landingCtrl;
    private Scene landing;

    private CardCtrl cardCtrl;
    private Scene card;

    private PublicBoardCtrl publicBoardCtrl;
    private Scene publicBoard;

    private BoardSelectCtrl boardSelectCtrl;
    private Scene boardSelect;

    private PopupJoinCtrl popupJoinCtrl;
    private Scene popupJoin;

    private TagManagerCtrl tagManagerCtrl;
    private Scene tagManager;
    private TaskCreatorCtrl taskCreatorCtrl;
    private Scene taskCreator;
    private BoardOverviewCtrl boardOverviewCtrl;
    private Scene boardOverwiew;
    private BoardCreationCtrl boardCreationCtrl;
    private Scene boardCreation;

    private ServerSelectCtrl serverSelectCtrl;
    private Scene serverSelect;

    private EditBoardCtrl editBoardCtrl;
    private Scene editBoard;

    private ListCreationCtrl listCreationCtrl;
    private Scene listCreate;

    private HelpCtrl helpCtrl;
    private Scene helpScene;

    private ListEditCtrl listEditCtrl;
    private Scene listEdit;


    public Map<Integer, CardComponent> cardIdComponentMap;

    public final DataFormat CARD_FORMAT = new DataFormat("application/x-card");

    /**
     * 
     * @param landing
     * @param primaryStage
     * @param card
     * @param publicBoard
     * @param boardSelect
     * @param popup
     * @param tagManager
     * @param listCreate
     * @param select
     * @param boardOverview
     * @param editBoard
     * @param boardCreation
     * @param help
     * @param taskCreator
     * @param listEdit
     */
    public void initialize(Stage primaryStage,
                           Pair<LandingCtrl, Parent> landing,
                           Pair<CardCtrl, Parent> card,
                           Pair<PublicBoardCtrl, Parent> publicBoard,
                           Pair<BoardSelectCtrl, Parent> boardSelect,
                           Pair<PopupJoinCtrl, Parent> popup,
                           Pair<TagManagerCtrl, Parent> tagManager,
                           Pair<ListCreationCtrl, Parent> listCreate,
                           Pair<ServerSelectCtrl, Parent> select,
                           Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<BoardCreationCtrl, Parent> boardCreation,
                           Pair<TaskCreatorCtrl, Parent> taskCreator,
                           Pair<ListEditCtrl, Parent> listEdit,
                           Pair<EditBoardCtrl, Parent> editBoard,
                           Pair<HelpCtrl, Parent> help
                           ) {

        this.cardIdComponentMap = new HashMap<>();
        System.setProperty("javafx.dnd.delayedDragCallback", "false");

        this.primaryStage = primaryStage;
        this.landingCtrl = landing.getKey();
        this.landing = new Scene(landing.getValue());

        this.cardCtrl = card.getKey();
        this.card = new Scene(card.getValue());

        this.publicBoardCtrl = publicBoard.getKey();
        this.publicBoard = new Scene(publicBoard.getValue());

        this.boardSelectCtrl = boardSelect.getKey();
        this.boardSelect = new Scene(boardSelect.getValue());

        this.popupJoinCtrl = popup.getKey();
        this.popupJoin = new Scene(popup.getValue());

        this.tagManagerCtrl = tagManager.getKey();
        this.tagManager = new Scene(tagManager.getValue());

        this.listCreationCtrl = listCreate.getKey();
        this.listCreate = new Scene(listCreate.getValue());

        this.taskCreatorCtrl = taskCreator.getKey();
        this.taskCreator = new Scene(taskCreator.getValue());

        this.serverSelectCtrl = select.getKey();
        this.serverSelect = new Scene(select.getValue());

        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverwiew = new Scene(boardOverview.getValue());

        this.boardCreationCtrl = boardCreation.getKey();
        this.boardCreation = new Scene(boardCreation.getValue());

        this.listEditCtrl = listEdit.getKey();
        this.listEdit = new Scene(listEdit.getValue());

        this.editBoardCtrl = editBoard.getKey();
        this.editBoard = new Scene(editBoard.getValue());
        
        this.helpCtrl = help.getKey();
        this.helpScene = new Scene(help.getValue());

        showLanding();
        primaryStage.show();
    }

    /**
     * prompt the user to select a server ip, defaults to localhost:8080
     */
    public void showServerSelect() {
        primaryStage.setTitle("Select Talio Server");
        primaryStage.setScene(serverSelect);
        serverSelectCtrl.refresh();
    }

    /**
     *
     */
    public void showLanding() {
        primaryStage.setTitle("Landing page!!");
        primaryStage.setScene(landing);
    }

    /**
     *
     */
    public void showPublicBoard() {
        primaryStage.setTitle("Public Board :)");
        primaryStage.setScene(publicBoard);
        primaryStage.show();
        publicBoardCtrl.refresh();
    }

    /**
     * shows board selection scene
     */
    public void showSelect() {
        primaryStage.setTitle("Board selection");
        primaryStage.setScene(boardSelect);
        primaryStage.show();
        boardSelectCtrl.refresh();
    }

    /**
     * shows a popup
     */
    public void showPopup() {
        primaryStage.setTitle("Something went wrong");
        primaryStage.setScene(popupJoin);
        primaryStage.show();
        popupJoinCtrl.refresh();
    }


    /**
     * Shows the tag manager scene
     * @param boardID the board id for which to show the tag manager
     */
    public void showTagManager(int boardID) {
        primaryStage.setTitle("Tag Manager :)");
        primaryStage.setScene(tagManager);
        tagManagerCtrl.boardId = boardID;
        //Later combine these methods into one refresh method
        tagManagerCtrl.setLabelBoard();
        tagManagerCtrl.refresh();
        primaryStage.show();
    }


    /**
     *
     * @param cardComponent card component instance
     * @return int card id
     */
    public Integer cardComponentToCardId(CardComponent cardComponent) {
        for (int i : this.cardIdComponentMap.keySet()) {
            if (this.cardIdComponentMap.get(i).equals(cardComponent)) {
                return i;
            }
        }
        return null;
    }

//    /**
//     *
//     * @param hbox card component hbox
//     * @return int card id
//     */
//    public Integer cardComponentToCardId(HBox hbox) {
//        for (int i : this.cardIdComponentMap.keySet()) {
//            if (this.cardIdComponentMap.get(i).equals(cardComponent)) {
//                return i;
//            }
//        }
//        return null;
//    }

    /**
     * Method that parses fxColor to int
     *
     * @param fxColor the color to be parsed
     * @return return the color awtColor
     */
    public static int colorParseToInt(Color fxColor){
        return ((int)(fxColor.getRed() * 255) << 16)
                | ((int)(fxColor.getGreen() * 255) << 8)
                | (int)(fxColor.getBlue() * 255);
    }

    /**
     * Method that parses int to fxColor
     * @param intColor the color to be parsed
     * @return return the color fxColor
     */
    public static Color colorParseToFXColor(int intColor){
        return Color.rgb(
                (intColor >> 16) & 0xFF, // red component
                (intColor >> 8) & 0xFF, // green component
                intColor & 0xFF // blue component
        );
    }


    /**
     * shows a scene where you can create a new list and add it to the public board
     * @param boardId - board id
     */
    public void showListCreate(int boardId){
        primaryStage.setTitle("List creation");
        primaryStage.setScene(listCreate);
        listCreationCtrl.boardID = boardId;
        primaryStage.show();
    }


    /**
     * Shows the task creator scene
     */
    public void showTaskCreator() {
        primaryStage.setTitle("Task creator :)");
        primaryStage.setScene(taskCreator);
        primaryStage.show();
    }

    /**
     * Edit board with given boardID
     * @param boardID the boardID of the board
     */
    public void showEditBoard(int boardID) {
        primaryStage.setTitle("Show edit board :)");
        primaryStage.setScene(editBoard);
        editBoardCtrl.boardId = boardID;
        primaryStage.show();
        editBoardCtrl.openScene(boardID);
    }

    /**
     * Shows board overview
     * @param boardID the id of the board to join
     */
    public void showBoardOverview(int boardID){
        primaryStage.setTitle("Board overview :)");
        boardOverviewCtrl.boardID = boardID;
        primaryStage.setScene(boardOverwiew);
//        boardOverviewCtrl.refreshName(boardID);

        primaryStage.show();
        //We later have to combine all these methods we call into one refresh method in boardOverviewCtrl
        boardOverviewCtrl.setBoardName();
        boardOverviewCtrl.setColor();
        boardOverviewCtrl.refreshListViewTags();
        boardOverviewCtrl.refresh(null);
    }


    /**
     * show card overview
     * @param cardID card id
     * @param boardID board id
     */
    public  void showCard(int cardID, int boardID){
        primaryStage.setTitle("Card overview :)");
        primaryStage.setScene(card);
        cardCtrl.cardID = cardID;
        cardCtrl.boardID = boardID;

        primaryStage.show();
        cardCtrl.setInfo();
        cardCtrl.refresh();


    }

    /**
     * Shows board creation scene
     */
    public void showBoardCreation(){
        primaryStage.setTitle("Board creation overview :)");
        primaryStage.setScene(boardCreation);
        primaryStage.show();
    }

    /**
     * Shows the help page
     */
    public void showHelpPage() {
        primaryStage.setTitle("Help Page");
        primaryStage.setScene(helpScene);
        primaryStage.show();
    }

    /**
     * call the select ctrl savekey method to update after creating a board
     * @param boardkey the board key to be saved to the client
     */
    public void saveBoardByKey(String boardkey) {
        boardSelectCtrl.saveBoardKey(boardkey);
        boardSelectCtrl.refresh();
    }

    /**
     * shows scene for editing the list
     * @param listId
     * @param boardId
     */
    public void showListEdit(int listId, int boardId){
        primaryStage.setTitle("List edit");
        primaryStage.setScene(listEdit);
        listEditCtrl.listId = listId;
        listEditCtrl.boardId = boardId;
        primaryStage.show();
    }
        /**
     * refresh board overview scene with newly polled data from the database
     */
    public void refreshBoardOverview()  {
        boardOverviewCtrl.refresh(null);
    }

    /**
     * add event listener for the enter key, intermediate function
     * @param listID the list mouse is currently in
     */
    public void addEnterKeyListener(int listID) {
        boardOverviewCtrl.addEnterKeyListener(listID);
    }

    /**
     * refresh board after 200 milliseconds
     * waits for database to update before pulling data again and showing in client
     */
    public void timeoutBoardRefresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh(null);
        });
        try {
            future.get(200, TimeUnit.MILLISECONDS); // set a timeout of 5 seconds
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true); // cancel the task if it takes too long
            // handle the timeout exception here
            e.printStackTrace();
            System.out.println("time out exception in main controller");
        }
        executor.shutdown();
    }


    /**
     * refresh board overview after custom timeout
     * @param mil the timeout in milliseconds
     */
    public void timeoutBoardRefresh(int mil) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh(null);
        });
        try {
            future.get(mil, TimeUnit.MILLISECONDS); // set a timeout of 5 seconds
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true); // cancel the task if it takes too long
            // handle the timeout exception here
            e.printStackTrace();
            System.out.println("time out exception in main controller");
        }
        executor.shutdown();
    }

    /**
     * Creates a card
     * @param listID the id of the list the card is added to
     * @return the card that was created
     */
    public Card createCard(int listID) {
        return boardOverviewCtrl.createCard(listID);
    }
    /**
     * refresh a specific list
     * @param listID the lists id
     * @param component the cardlist component
     */
    public void refreshListView(int listID, CardListComponent component) {
        boardOverviewCtrl.refreshList(listID, component);
    }

}
