package client.scenes;

import client.utils.ServerUtils;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class TagPopUpCtrl {
    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private int boardID;

    private int cardID;


    @FXML
    private ListView<Tag> boardtags;
    @FXML
    private ListView<Tag> taglist;

    @FXML
    private Button goBackButton;



    /**
     * help controller constructor method
     * @param mainCtrl main controller
     * @param server server utils functions
     */
    @Inject
    public TagPopUpCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }


    /**
     * refresh method
     */
    public void refresh() {
        showDropDown();
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
     * Shows the tags in this board that are not added to the card yet
     */
    public void showDropDown(){
        boardtags.setItems(getTagListOfBoard(boardID));
        boardtags.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            public void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty || tag == null || tag.getTitle() == null) {
                    setText(null);
                } else {
                    setText(tag.getTitle());
                    String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
                    setStyle("-fx-control-inner-background: " + hexColor);
                }
            }
        });
    }

    /**
     * Shows the selected tag in the text field/area
     * @param event - clicking the mouse
     */
    public void addTagToCard(MouseEvent event){
        Tag tag = boardtags.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2 && tag!=null ){
            server.addTagToCard(boardID, tag.getId(), cardID);
//            showTags();
            showDropDown();
        }
    }


    /**
     * Gets the list of tags and puts it in an observable list
     * @param boardID the id of the board
     * @return an observable list of tags
     */
    public ObservableList<Tag> getTagListOfBoard(int boardID){
        List<Tag> tags = server.getTagsFromBoard(boardID);
        List<Tag> t = new ArrayList<>();
        Set<Tag> tagsofcard = server.getTagsForCard(cardID);
        for(Tag tag : tags){
            if(tagsofcard!=null && !tagsofcard.contains(tag)){
                t.add(tag);
            }
        }
        ObservableList<Tag> tagList = FXCollections.observableArrayList(t);
        return tagList;
    }


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
