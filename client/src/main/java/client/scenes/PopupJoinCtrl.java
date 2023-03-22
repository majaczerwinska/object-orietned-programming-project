package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class PopupJoinCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @FXML
    private Button back;

    /**
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public PopupJoinCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     *
     */
    public void refresh(){};

    /**
     * takes you back to board selection
     * @param mouseEvent
     */
    @FXML
    public void back(MouseEvent mouseEvent){
        System.out.println("test here");
        mainCtrl.showSelect();
    }




}
