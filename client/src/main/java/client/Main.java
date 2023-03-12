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
import client.scenes.CardCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Main extends Application {

//    private static final Injector INJECTOR = createInjector(new MyModule());
//    private static final MyFXML FXML = new MyFXML(INJECTOR);

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
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException -
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
            Scene scene = new Scene(root, 500, 400, Color.ANTIQUEWHITE);

            stage.setTitle("main scene title goes here");

            stage.setX(400);
            stage.setY(200);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("very sad exception :(\nin start method of client.main..");
            e.printStackTrace();
        }

//        var overview = FXML.load(QuoteOverviewCtrl.class, "client", "scenes", "QuoteOverview.fxml");
//        var add = FXML.load(AddQuoteCtrl.class, "client", "scenes", "AddQuote.fxml");
//        var card = FXML.load(CardCtrl.class, "client", "scenes", "Card.fxml");
//        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
//        mainCtrl.initialize(primaryStage, overview, add, card);

    }
}
