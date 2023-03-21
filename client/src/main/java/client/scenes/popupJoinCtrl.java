package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Stage;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class popupJoinCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @FXML
    private Button back;

    @Inject
    public popupJoinCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    public void refresh(){};

    @FXML
    public void back(MouseEvent mouseEvent){
        System.out.println("test here");
        mainCtrl.showSelect();
    }




}
