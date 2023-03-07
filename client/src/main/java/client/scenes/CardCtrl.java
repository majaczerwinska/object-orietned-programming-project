package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.util.Random;

public class CardCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField text;
    @FXML
    private Button button;
    @Inject
    public CardCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl  =mainCtrl;
        this.server = server;
    }



    @FXML
    protected void doit() {
        String title = text.getText();
        Random random = new Random();
        int id = random.nextInt();
        Card card = new Card(id,title);
        server.addCard(card);


    }
}