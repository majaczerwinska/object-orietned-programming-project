package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.CardList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class CustomizationCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public int boardId;

    @FXML
    private ColorPicker bb;
    @FXML
    private ColorPicker bf;
    @FXML
    private ColorPicker lb;
    @FXML
    private ColorPicker lf;

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

        mainCtrl.showBoardOverview(boardId);
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
        }
    }

}
