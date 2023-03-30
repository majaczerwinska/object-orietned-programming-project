package client.scenes;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static client.scenes.MainCtrl.colorParseToFXColor;
import static client.scenes.MainCtrl.colorParseToInt;
public class TagManagerCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    public int boardId = 0;
    @FXML
    private Label labelBoard;
    @FXML
    private ListView<Tag> tagListView;
    @FXML
    private TextField tfTitle;
    @FXML
    private ColorPicker colorPicker;


    /**
     *
     * @param server -
     * @param mainCtrl -
     */
    @Inject
    public TagManagerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (server.testConnection(ServerUtils.SERVER) != 200) {
            System.out.println("No server to connect to, halting tag init function");
            return;
        }
        System.out.println("Initialize called");
        setLabelBoard();
        refresh();
    }

    /**
     * Creates an observable list with all tags
     * @param boardId the board id we are in
     * @return an observable list with all tags
     */
    public ObservableList<Tag> getTagList(int boardId){
        List<Tag> tags = server.getTagsFromBoard(boardId);
        List<Tag> t = new ArrayList<>();
        for(Tag tag : tags){
            t.add(tag);
        }
        ObservableList<Tag> tagList = FXCollections.observableList(t);
        return tagList;
    }

    /**
     * Refreshes the list overview
     */
    public void refresh(){
        tagListView.setItems(getTagList(boardId));
        tagListView.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
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
     * Sets label to show the board you are currently viewing with tag manager
     */
    public void setLabelBoard(){
        System.out.println("Setting board label in tag manager with board #" + boardId);
        Board board = server.getBoard(boardId);
        labelBoard.setText("Tags for " + board.getName());
    }

    /**
     * Adds a tag to database
     * @param actionEvent the event when clicking the "add tag" button
     */
    public void addTag(ActionEvent actionEvent){
        String title = tfTitle.getText();
        Color fxColor = colorPicker.getValue();
        int intColor = colorParseToInt(fxColor);
        Tag tag = new Tag(title, intColor);
        server.addTag(tag, boardId);
        refresh();
    }

    /**
     * deletes the selected tag
     * @param actionEvent the event when clicking the "delete" button
     */
    public void deleteTag(ActionEvent actionEvent){
        Tag tag = tagListView.getSelectionModel().getSelectedItem();
        if(tag!=null){
            server.deleteTag(tag, boardId);
            System.out.println("deleting" + tag);
            refresh();
        }

    }

    /**
     * Edits the selected tag
     * @param actionEvent the event when clicking the "edit" button
     */
    public void editTag(ActionEvent actionEvent){
        Tag oldTag = tagListView.getSelectionModel().getSelectedItem();
        String title = tfTitle.getText();
        Color fxColor = colorPicker.getValue();
        int intColor = colorParseToInt(fxColor);
        Tag newTag = new Tag(title, intColor);
        if(oldTag!=null){
            server.editTag(oldTag, newTag);
            refresh();
        }

    }

    /**
     * Shows the selected tag in the text field/area
     */
    public void showSelectedItem(){
        Tag tag = tagListView.getSelectionModel().getSelectedItem();
        if(tag!=null){
            tfTitle.setText(tag.getTitle());
            colorPicker.setValue(colorParseToFXColor(tag.getColor()));
        }
    }

    /**
     * Shows the selected tag in the text field/area
     * @param mouseEvent the event when clicking a tag in the listview
     */
    public void showSelectedItem(MouseEvent mouseEvent){
        Tag tag = tagListView.getSelectionModel().getSelectedItem();
        if(tag!=null){
            tfTitle.setText(tag.getTitle());
            colorPicker.setValue(colorParseToFXColor(tag.getColor()));
        }
    }


    /**
     * Goes back to previous scene
     * @param actionEvent the event when clicking the back button
     */
    public void backButton(ActionEvent actionEvent){
        System.out.println("going back to board with id #"+boardId);
        mainCtrl.showBoardOverview(boardId);
    }
}
