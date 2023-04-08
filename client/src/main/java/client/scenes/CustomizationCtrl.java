package client.scenes;

import client.components.PaletteComponent;
import client.utils.ServerUtils;
import client.utils.WebsocketClient;
import com.google.inject.Inject;
import commons.Board;
import commons.CardList;
import commons.Palette;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class CustomizationCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private WebsocketClient websocketClient;

    public int boardId;

    private Color listB = MainCtrl.colorParseToFXColor(3368550);
    private Color listF = MainCtrl.colorParseToFXColor(16777215) ;
    private Color boardB = MainCtrl.colorParseToFXColor(1723725);
    private Color boardF = MainCtrl.colorParseToFXColor(16777215);

    private Color listBt;
    private Color listFt;
    private Color boardBt;
    private Color boardFt;

    @FXML
    private ColorPicker bb;
    @FXML
    private ColorPicker bf;
    @FXML
    private ColorPicker lb;
    @FXML
    private ColorPicker lf;



    @FXML
    private VBox palettes;

    /**
     * constructor
     * @param server
     * @param mainCtrl
     * @param websocketClient
     */
    @Inject
    public CustomizationCtrl(ServerUtils server, MainCtrl mainCtrl, WebsocketClient websocketClient) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.websocketClient = websocketClient;
    }

    /**
     * Creates stomp session
     */
    public void setStompSession(){
        websocketClient.setStompSession(ServerUtils.SERVER);
        System.out.println("StompSession created in customization");
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
        board.setbColor(MainCtrl.colorParseToInt(bb.getValue()));
        board.setfColor(MainCtrl.colorParseToInt(bf.getValue()));
        board.setListb(MainCtrl.colorParseToInt(lb.getValue()));
        board.setListt(MainCtrl.colorParseToInt(lf.getValue()));
        server.editBoard(boardId,board);
        colourlist();
        websocketClient.sendMessage("/app/update/list/"+boardId, "Done updating card in component");
        mainCtrl.showBoardOverview(boardId);
       // mainCtrl.colorBF(boardId, MainCtrl.colorParseToInt(bf.getValue()));

    }

    /**
     * applies the changes
     */
    public void colourlist(){
        List<CardList> cardLists = server.getCardListsFromBoard(boardId);
        Board board = server.getBoard(boardId);
        for(CardList list : cardLists){
                list.setbColor(board.getListb());
                list.setfColor(board.getListt());
                server.editCardListColour(list.getId(), list);
                //mainCtrl.colorLF(boardId, list.getId(), MainCtrl.colorParseToInt(lf.getValue()));
            }
    }

    /**
     * refreshes the page to set values for color pickers
     */
    public void refresh() {
        if(boardBt != null){
            bb.setValue(boardBt);
            bf.setValue(boardFt);
            lb.setValue(listBt);
            lf.setValue(listFt);
            boardBt = null;
            boardFt = null;
            listBt = null;
            listFt = null;
            displayPalettes();
            shortcut();
        } else {
            Board b = server.getBoard(boardId);
            bb.setValue(MainCtrl.colorParseToFXColor(b.getbColor()));
            bf.setValue(MainCtrl.colorParseToFXColor(b.getfColor()));
            lb.setValue(MainCtrl.colorParseToFXColor(b.getListb()));
            lf.setValue(MainCtrl.colorParseToFXColor(b.getListt()));
            displayPalettes();
            shortcut();
        }
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

    /**
     * clears
     */
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

    /**
     * adds new palette of colors for cards
     * @param mouseEvent
     */
    public void addPalette(MouseEvent mouseEvent){
        listFt = lf.getValue();
        listBt = lb.getValue();
        boardFt = bf.getValue();
        boardBt = bb.getValue();
        mainCtrl.showPaletteCreation(boardId);
        displayPalettes();
    }

    /**
     * shows palettes from the board
     */
    public void displayPalettes(){
        clearPalette();
        List<Palette> p = server.getPalettesFromBoard(boardId);
        for(Palette pal : p){
            PaletteComponent component = new PaletteComponent(this, boardId);
            component.setData(pal);
            palettes.getChildren().add(component);
        }
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        lb.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }

}
