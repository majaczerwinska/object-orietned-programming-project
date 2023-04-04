package client.scenes;

import client.utils.ServerUtils;
import commons.Card;
import commons.Palette;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.util.List;


public class ColorPopUpCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private int boardID;

    private int cardID;


    @FXML
    private ListView<Palette> palettes;

    @FXML
    private TextField theme;

    @FXML
    private Button goBackButton;

    @FXML
    private Button saveButton;



    /**
     * help controller constructor method
     * @param mainCtrl main controller
     * @param server server utils functions
     */
    @Inject
    public ColorPopUpCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * refresh method
     */
    public void refresh() {
        palettes.getItems().clear();
        showDropDownColors();
        theme.setText("");
    }

    /**
     * Sets boardID to the ID of the board
     * @param boardID the ID of the board
     * @param cardID the ID of the board
     */
    public void setBoardAndCardID(int boardID, int cardID) {
        this.boardID = boardID;
        this.cardID = cardID;
    }


    /**
     * shows menu of palettes of colors to choose for the card
     */
    public void showDropDownColors(){
        palettes.setItems(getPalettes(boardID));
        palettes.setCellFactory(param -> new ListCell<Palette>() {
            @Override
            public void updateItem(Palette palette, boolean empty) {
                super.updateItem(palette, empty);
                if (empty || palette == null) {
                    setText(null);
                } else {
                    setText(palette.getName());
//                    String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
//                    setStyle("-fx-control-inner-background: " + hexColor);
                }
            }
        });
    }

    /**
     * gets palettes from the board
     * @param boardId
     * @return - list of palettes
     */
    public ObservableList<Palette> getPalettes(int boardId){
        List<Palette> p = server.getPalettesFromBoard(boardId);
        return FXCollections.observableArrayList(p);
    }

    /**
     * hopefully will apply changes of selected color to card and save them to DB
     * @param event
     */
    public void choose(MouseEvent event){
        Palette palette = palettes.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2 && palette!=null ){
            showDropDownColors();
        }
        assert palette != null;
        theme.setText(palette.getName());
    }

    /**
     * saves cards colors to the db
     * @param event
     */
    public void save(MouseEvent event){
        List<Palette> palettes = server.getPalettesFromBoard(boardID);
        if(theme.getText().equals("")){
            for(Palette pal : palettes){
                if(pal.isIsdefault()){
                    theme.setText(pal.getName());
                    Card card = server.getCard(cardID);
                    card.setColor(pal.getbColor());
                    card.setFcolor(pal.getfColor());
                    server.editCard(boardID, cardID, card, false);
                    break;
                }
            }
        }
        else{
            for(Palette pal : palettes){
                if(pal.getName().equals(theme.getText())){
                    Card card = server.getCard(cardID);
                    card.setColor(pal.getbColor());
                    card.setFcolor(pal.getfColor());
                    server.editCard(boardID, cardID, card, false);
                    break;
                }
            }

        }
        back();
    }

    /**
    /**
     * Closes the pop-up
     */
    public void back() {
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
    }


    /**
     * go back button getter
     * @return Button
     */
    @FXML
    public Button getGoBackButton() {
        return goBackButton;
    }





}
