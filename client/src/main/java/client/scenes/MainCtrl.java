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


    /**
     *
     * @param primaryStage
     * @param landing
     * @param card
     * @param publicBoard
     * @param boardSelect
     * @param popup
     * @param tagManager
     * @param select
     * @param taskCreator
     * @param boardOverview
     * @param boardCreation
     */
    public void initialize(Stage primaryStage,
                           Pair<LandingCtrl, Parent> landing,
                           Pair<CardCtrl, Parent> card,
                           Pair<PublicBoardCtrl, Parent> publicBoard,
                           Pair<BoardSelectCtrl, Parent> boardSelect,
                           Pair<PopupJoinCtrl, Parent> popup,
                           Pair<TagManagerCtrl, Parent> tagManager,
                           Pair<ServerSelectCtrl, Parent> select,
                           Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<BoardCreationCtrl, Parent> boardCreation,
                           Pair<TaskCreatorCtrl, Parent> taskCreator) {
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
     */
    public void showTagManager() {
        primaryStage.setTitle("Tag Manager :)");
        primaryStage.setScene(tagManager);
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
     */
    public  void showBoardOverwiew(){
        primaryStage.setTitle("Board overview :)");
        primaryStage.setScene(boardOverwiew);
        primaryStage.show();
    }

    /**
     * Shows card overview
     */
    public  void showCard(){
        primaryStage.setTitle("Card overview :)");
        primaryStage.setScene(card);
        primaryStage.show();
    }

    /**
     * Shows board creation scene
     */
    public void showBoardCreation(){
        primaryStage.setTitle("Board creation overview :)");
        primaryStage.setScene(boardCreation);
        primaryStage.show();
    }
}