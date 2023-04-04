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
        board.setbColor(mainCtrl.colorParseToInt(bb.getValue()));
        board.setfColor(mainCtrl.colorParseToInt(bf.getValue()));
        server.editBoard(boardId,board);
        listBt = lb.getValue();
        listFt = lf.getValue();
        colourlist();
        websocketClient.sendMessage("/app/update/list/"+boardId, "Done updating card in component");



        mainCtrl.showBoardOverview(boardId);
       // mainCtrl.colorBF(boardId, MainCtrl.colorParseToInt(bf.getValue()));

    }

    /**
     * applies the changes?
     */
    public void colourlist(){
        List<CardList> cardLists = server.getCardListsFromBoard(boardId);
        if (listBt == null) {
            for(CardList list : cardLists){
                list.setbColor(MainCtrl.colorParseToInt(listB));
                System.out.println("extracted value from color picker after setting lbcolor: " + lb.getValue());
                list.setfColor(MainCtrl.colorParseToInt(listF));
                System.out.println("extracted value from color picker: " + lf.getValue());
                server.editCardListColour(list.getId(), list);
                //mainCtrl.colorLF(boardId, list.getId(), MainCtrl.colorParseToInt(lf.getValue()));
            }
        }
        else{
            for(CardList list : cardLists) {
                list.setbColor(MainCtrl.colorParseToInt(listBt));
                System.out.println("extracted value from color picker after setting lbcolor: " + lb.getValue());
                list.setfColor(MainCtrl.colorParseToInt(listFt));
                System.out.println("extracted value from color picker: " + lf.getValue());
                server.editCardListColour(list.getId(), list);
                //mainCtrl.colorLF(boardId, list.getId(), MainCtrl.colorParseToInt(lf.getValue()));
            }
        }
    }

    /**
     * refreshes the page to set values for color pickers
     */
    public void refresh(){
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
//                listBt = listB;
//                listFt = listF;
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
        displayPalettes();
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

//    public List<Palette> getPalettes(){
//        return server.getPalettesFromBoard(boardId);
//    }
////    @FXML
////    public void addPalette(MouseEvent event){
////        Palette p = new Palette()
////    }

    /**
     * adds new palette of colors for cards
     * @param mouseEvent
     */
    public void addPalette(MouseEvent mouseEvent){
        Palette p = new Palette("new palette", 0, 0);
        server.addPaletteToBoard(boardId, p);
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



}
