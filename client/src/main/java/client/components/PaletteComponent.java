package client.components;

import client.scenes.CustomizationCtrl;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Palette;
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

    private CustomizationCtrl customizationCtrl;
    private int boardId;
    private ServerUtils server;

    /**
     * constructor
     * @param customizationCtrl
     * @param boardId
     */
    public PaletteComponent(CustomizationCtrl customizationCtrl, int boardId){
        super();
        this.customizationCtrl = customizationCtrl;
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

    public void setData(Palette p){
        name.setText(p.getName());
        background.setValue(MainCtrl.colorParseToFXColor(p.getbColor()));
        font.setValue(MainCtrl.colorParseToFXColor(p.getfColor()));
//        if(p.isIsdefault()){
//            setDef.setVisible(false);
//        }
//        else{
//            setDef.setVisible(true);
//        }
    }



}
