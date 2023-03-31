package client.scenes;

import client.components.PaletteComponent;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.CardList;
import commons.Palette;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class CustomizationCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public int boardId;

    private Color listB = MainCtrl.colorParseToFXColor(11776947);
    private Color listF = MainCtrl.colorParseToFXColor(0) ;
    private Color boardB = MainCtrl.colorParseToFXColor(2580);
    private Color boardF = MainCtrl.colorParseToFXColor(13421772);

    private Color listBt;
    private Color listFt;

    @FXML
    private ColorPicker bb;
    @FXML
    private ColorPicker bf;
    @FXML
    private ColorPicker lb;
    @FXML
    private ColorPicker lf;

//    @FXML
//    private ListView<Palette> palettes;
//    private ObservableList<Palette> items = FXCollections.observableArrayList();

    @FXML
    private VBox palettes;

    /**
     * constructor
     * @param server
     * @param mainCtrl
     */
    @Inject
    public CustomizationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * takes you back to the board overview
     * @param event
     */
    public void back(ActionEvent event){
        mainCtrl.showBoardOverview(boardId);
    }

    /**
     * changes (for now) board and lists background colors
     * @param event
     */
    public void color(MouseEvent event){
        Board board = server.getBoard(boardId);
        board.setbColor(mainCtrl.colorParseToInt(bb.getValue()));
        board.setfColor(mainCtrl.colorParseToInt(bf.getValue()));
        server.editBoard(boardId,board);
        colourlist();
        listBt = lb.getValue();
        listFt = lf.getValue();

        mainCtrl.showBoardOverview(boardId);
        mainCtrl.colorBF(boardId, MainCtrl.colorParseToInt(bf.getValue()));

    }

    /**
     * applies the changes?
     */
    public void colourlist(){
        List<CardList> cardLists = server.getCardListsFromBoard(boardId);
        for(CardList list : cardLists){
            list.setbColor(mainCtrl.colorParseToInt(lb.getValue()));
            list.setfColor(mainCtrl.colorParseToInt(lf.getValue()));
            server.editCardListColour(list.getId(), list);
            //mainCtrl.colorLF(boardId, list.getId(), MainCtrl.colorParseToInt(lf.getValue()));
        }
    }

    /**
     * refreshes the page to set values for color pickers
     */
    public void refresh(){
        clearPalette();
        Board b = server.getBoard(boardId);
        List<CardList> l = server.getCardListsFromBoard(boardId);
        bb.setValue(MainCtrl.colorParseToFXColor(b.getbColor()));
        bf.setValue(MainCtrl.colorParseToFXColor(b.getfColor()));
        System.out.println("board color: " + bb.getValue());
        System.out.println("board f color: " + bf.getValue());
        if(l.isEmpty()){
            if(listBt == null){
                lb.setValue(listB);
                lf.setValue(listF);
            }
            else{
                lb.setValue(listBt);
                lf.setValue(listFt);
            }
        }
        else{
            lb.setValue(MainCtrl.colorParseToFXColor(l.get(0).getbColor()));
            lf.setValue(MainCtrl.colorParseToFXColor(l.get(0).getfColor()));
        }
        List<Palette> p = server.getPalettesFromBoard(boardId);
        for(Palette pal : p){
            PaletteComponent component = new PaletteComponent(this, boardId);
            component.setData(pal);
            palettes.getChildren().add(component);
        }
//        palettes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        ObservableList<Palette> boardList = FXCollections.observableList(getPalettes());
//        palettes.setItems(boardList);
    }

    /**
     * resets color pickers for a board to default
     * @param event
     */
    @FXML
    public void resetBoard(MouseEvent event){
        bb.setValue(boardB);
        bf.setValue(boardF);
    }
    public void clearPalette(){
        palettes.getChildren().clear();
    }

    /**
     * resets color pickers for lists to default
     * @param event
     */
    @FXML
    public void resetList(MouseEvent event){
        lb.setValue(listB);
        lf.setValue(listF);
    }

//    public List<Palette> getPalettes(){
//        return server.getPalettesFromBoard(boardId);
//    }
////    @FXML
////    public void addPalette(MouseEvent event){
////        Palette p = new Palette()
////    }


}
