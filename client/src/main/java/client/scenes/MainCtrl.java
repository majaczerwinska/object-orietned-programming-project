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

    private BoardCreationCtrl boardCreationCtrl;
    private Scene boardCreation;

    private LandingCtrl landingCtrl;
    private Scene landing;

    private CardCtrl cardCtrl;
    private Scene cardCreator;

    private PublicBoardCtrl publicBoardCtrl;
    private Scene publicBoard;

    private TagManagerCtrl tagManagerCtrl;
    private Scene tagManager;

    /**
     *
     * @param primaryStage -
     * @param landing -
     * @param publicBoard -
     * @param card -
     * @param tagManager -
     */
    public void initialize(Stage primaryStage, 
                            Pair<LandingCtrl, Parent> landing,
                            Pair<CardCtrl, Parent> card,
                            Pair<PublicBoardCtrl, Parent> publicBoard,
                            Pair<TagManagerCtrl, Parent> tagManager,
                            Pair<BoardCreationCtrl, Parent> boardCreation) {
        this.primaryStage = primaryStage;
        this.landingCtrl = landing.getKey();
        this.landing = new Scene(landing.getValue());

        this.cardCtrl = card.getKey();
        this.cardCreator = new Scene(card.getValue());

        this.publicBoardCtrl = publicBoard.getKey();
        this.publicBoard = new Scene(publicBoard.getValue());

        this.tagManagerCtrl = tagManager.getKey();
        this.tagManager = new Scene(tagManager.getValue());

        this.boardCreationCtrl = boardCreation.getKey();
        this.boardCreation = new Scene(boardCreation.getValue());

        showLanding();
        primaryStage.show();
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
     * Shows the tag manager scene
     */
    public void showTagManager() {
        primaryStage.setTitle("Tag Manager :)");
        primaryStage.setScene(tagManager);
        primaryStage.show();
    }

    /**
     * Shows the board creation menu
     */
    public void showBoardCreation() {
        primaryStage.setTitle("Board Creation");
        primaryStage.setScene(boardCreation);
        primaryStage.show();
        boardCreationCtrl.refresh();
    }
}