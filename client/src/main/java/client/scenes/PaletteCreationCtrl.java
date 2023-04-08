package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Palette;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.List;


public class PaletteCreationCtrl {

    @FXML
    private TextField name;

    @FXML
    private ColorPicker background;

    @FXML
    private ColorPicker font;
    @FXML
    public Label nameExists;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public int boardId;

    /**
     * constructor
     * @param server
     * @param mainCtrl
     */
    @Inject
    public PaletteCreationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * refresh
     */
    public void refresh(){
        nameExists.setVisible(false);
    }

    /**
     * creates a new palette for the board
     * @param event
     */
    public void create(MouseEvent event){
        String n = name.getText();
        List<Palette> palettes = server.getPalettesFromBoard(boardId);
        for(Palette pal : palettes){
            if(pal.getName().equals(n)){
                nameExists.setVisible(true);
                return;
            }
        }
        Palette p = new Palette(name.getText(), MainCtrl.colorParseToInt(background.getValue()),
                MainCtrl.colorParseToInt(font.getValue()));
        server.addPaletteToBoard(boardId, p);
        name.setText("");
        mainCtrl.showCustomization(boardId);
    }

    /**
     * cancel button
     * @param event
     */
    public void cancel(MouseEvent event){
        name.setText("");
        mainCtrl.showCustomization(boardId);
    }
}
