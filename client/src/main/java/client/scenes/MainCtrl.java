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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

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


    private ListCreationCtrl listCreationCtrl;
    private Scene listCreate;

    /**
     *
     * @param primaryStage
     * @param card
     * @param publicBoard
     * @param boardSelect
     * @param popup
     * @param tagManager
     * @param listCreate
     * @param select
     * @param taskCreator
     * @param boardOverview
     * @param boardCreation
     */
    public void initialize(Stage primaryStage,
                           //Pair<LandingCtrl, Parent> landing,
                           Pair<CardCtrl, Parent> card,
                           Pair<PublicBoardCtrl, Parent> publicBoard,
                           Pair<BoardSelectCtrl, Parent> boardSelect,
                           Pair<PopupJoinCtrl, Parent> popup,
                           Pair<TagManagerCtrl, Parent> tagManager,
                           Pair<ListCreationCtrl, Parent> listCreate,
                           Pair<ServerSelectCtrl, Parent> select,
                           Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<BoardCreationCtrl, Parent> boardCreation,
                           Pair<TaskCreatorCtrl, Parent> taskCreator) {
        this.primaryStage = primaryStage;
        //this.landingCtrl = landing.getKey();
        //this.landing = new Scene(landing.getValue());

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

//        showLanding();
        showServerSelect();
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
        landingCtrl.refresh();
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
        primaryStage.show();
    }

    /**
     * shows a scene where you can create a new list and add it to the public board
     */
    public void showListCreate(){
        primaryStage.setTitle("List creation");
        primaryStage.setScene(listCreate);
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
     * Shows board overview
     * @param boardID the id of the board to join
     */
    public void showBoardOverview(int boardID){
        primaryStage.setTitle("Board overview :)");
        primaryStage.setScene(boardOverwiew);
        boardOverviewCtrl.boardID = boardID;
        primaryStage.show();
        boardOverviewCtrl.clearBoard();
        boardOverviewCtrl.displayLists(boardOverviewCtrl.getCardListsFromServer());
    }


    public  void showCard(int cardID, int boardID){
        primaryStage.setTitle("Card overview :)");
        primaryStage.setScene(card);
        cardCtrl.cardID = cardID;
        cardCtrl.boardID = boardID;
        primaryStage.show();
        cardCtrl.clearCard();
        cardCtrl.displayTasks();
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
     * call the select ctrl savekey method to update after creating a board
     * @param boardkey the board key to be saved to the client
     */
    public void saveBoardByKey(String boardkey) {
        boardSelectCtrl.saveBoardKey(boardkey);
        boardSelectCtrl.refresh();
    }

    public void refreshBoardOverview()  {
        boardOverviewCtrl.refresh();
    }

    public void addEnterKeyListener(int listID) {
        boardOverviewCtrl.addEnterKeyListener(listID);
    }

    public void timeoutBoardRefresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh();
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




    public void timeoutBoardRefresh(int mil) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh();
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
}
