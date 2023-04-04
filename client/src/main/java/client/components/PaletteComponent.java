package client.components;

import client.scenes.CustomizationCtrl;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Board;
import commons.Palette;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.util.List;

public class PaletteComponent extends HBox {

    @FXML
    public Label name;
    @FXML
    private Rectangle background;
    @FXML
    private Rectangle font;
    @FXML
    private Button delete;
    @FXML
    private Button def;

    private CustomizationCtrl customizationCtrl;
    private int boardId;
    private Palette palette;
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

    /**
     * sets data for sth idk
     * @param p
     */
    public void setData(Palette p){
        this.palette = p;
        name.setText(p.getName());
        background.setFill(MainCtrl.colorParseToFXColor(p.getbColor()));
        font.setFill(MainCtrl.colorParseToFXColor(p.getfColor()));
        if(p.isIsdefault()){
            def.setVisible(false);
        }
        else{
            def.setVisible(true);
        }
    }

    /**
     * delete palette
     * @param event
     */
    public void delete(MouseEvent event){
        server.deletePalette(palette.getId());
        customizationCtrl.refresh();
    }

    /**
     * updates the default palette in DB
     * @param event
     */
    public void setDef(MouseEvent event){
        System.out.println(palette.getName());
        Board board = server.getBoard(boardId);
        List<Palette> palettes = board.getPalettes();
        for(Palette pal : palettes){
            if(pal.equals(palette)){
                pal.setIsdefault(true);
                server.editPalette(pal.getId(), pal);
            }
            else{
                pal.setIsdefault(false);
                server.editPalette(pal.getId(), pal);
            }
        }
        server.editBoard(boardId, board);
        customizationCtrl.refresh();
    }


}
