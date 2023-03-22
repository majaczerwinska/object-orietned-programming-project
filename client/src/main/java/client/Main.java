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
package client;

//import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

//import client.scenes.CardCtrl;
//import com.google.inject.Injector;

//import client.scenes.AddQuoteCtrl;
//import client.scenes.MainCtrl;
//import client.scenes.QuoteOverviewCtrl;

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
//import javafx.fxml.FXMLLoader;

//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.paint.Color;

import javafx.stage.Stage;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     *
     * @param args -
     * @throws URISyntaxException -
     * @throws IOException -
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException -
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {

            var landing = FXML.load(LandingCtrl.class, "client", "scenes", "Landing.fxml");
            var publicBoard = FXML.load(PublicBoardCtrl.class, "client", "scenes", "PublicBoard.fxml");
            var card = FXML.load(CardCtrl.class, "client", "scenes", "CardCreator.fxml");
            var selectBoard = FXML.load(BoardSelectCtrl.class, "client", "scenes", "BoardSelect.fxml");
            var popupJoin = FXML.load(PopupJoinCtrl.class, "client", "scenes", "popupJoin.fxml");
            var listCreate = FXML.load(ListCreationCtrl.class, "client", "scenes", "ListCreate.fxml");

            var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
            var tagManager = FXML.load(TagManagerCtrl.class, "client", "scenes", "TagManager.fxml");

            mainCtrl.initialize(primaryStage, landing, card, publicBoard,
                    selectBoard, popupJoin, tagManager, listCreate);
        } catch (Exception e) {
            System.out.println("very sad exception :(\nin start method of client.main..");
            e.printStackTrace();
        }

//        var overview = FXML.load(QuoteOverviewCtrl.class, "client", "scenes", "QuoteOverview.fxml");
//        var add = FXML.load(AddQuoteCtrl.class, "client", "scenes", "AddQuote.fxml");
//        var card = FXML.load(CardCtrl.class, "client", "scenes", "Card.fxml");
//

    }
}
