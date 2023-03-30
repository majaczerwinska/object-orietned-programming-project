package client.components;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


import java.io.IOException;

public class PaletteComponent extends HBox {

    @FXML
    public Label name;
    @FXML
    private ColorPicker background;
    @FXML
    private ColorPicker font;
    @FXML
    private Button delete;
    @FXML
    private Button setDef;

    private MainCtrl mainCtrl;
    private int boardId;
    private ServerUtils server;

    /**
     * constructor
     * @param mainCtrl
     * @param boardId
     */
    public PaletteComponent(MainCtrl mainCtrl, int boardId){
        super();
        this.mainCtrl = mainCtrl;
        this.boardId = boardId;
        server = new ServerUtils();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/PaletteComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(PaletteComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
