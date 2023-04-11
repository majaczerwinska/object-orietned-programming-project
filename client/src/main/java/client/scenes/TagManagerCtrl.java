package client.scenes;
import client.utils.ServerUtils;
import client.utils.WebsocketClient;
import com.google.inject.Inject;
import commons.Board;
import commons.Tag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import static client.scenes.MainCtrl.colorParseToFXColor;
import static client.scenes.MainCtrl.colorParseToInt;
public class TagManagerCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final WebsocketClient websocketClient;
    public int boardId = 1;
    private ObservableList<Tag> tagList;

    @FXML
    private Label labelBoard;

    @FXML
    private ListView<Tag> tagListView;

    @FXML
    private TextField tfTitle;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label warningLabel;

    @FXML
    private Label tagTitleLabel;

    @FXML
    private Label tagColorLabel;

    @FXML
    private Button addTagButton;

    @FXML
    private Button editTagButton;

    @FXML
    private Button deleteTagButton;

    @FXML
    private Button goBackButton;

    /**
     *
     * @param server -
     * @param mainCtrl -
     * @param websocketClient -
     */
    @Inject
    public TagManagerCtrl(ServerUtils server, MainCtrl mainCtrl, WebsocketClient websocketClient) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.websocketClient = websocketClient;

    }


    /**
     * Creates stomp session
     */
    public void setStompSession(){
        websocketClient.setStompSession(ServerUtils.SERVER);
        System.out.println("StompSession created in tag manager");
    }

    /**
     * Disconnects the stomp session
     */
    public void disconnectStompSession(){
        websocketClient.disconnect();
    }

    /**
     * Subscribes to endpoint that listens to all updates of tags from specific board
     */
    public void subscribe(){
        websocketClient.registerForMessages("/topic/tags/"+boardId, String.class, update -> {
            System.out.println("payload: " + update);
            refresh();
        });
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
        tagList = FXCollections.observableList(t);
        return tagList;
    }

    /**
     * Refreshes the list overview
     */
    public void refresh(){
        Platform.runLater(new Runnable() {
            @Override public void run() {
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
        // checks if the tag has a name
        if(title.length() == 0)
        {
            // shows the warning text and doesn't create the tag
            warningLabel.setText("You cannot create \na tag without a name");
            refresh();
            return;
        }
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
            System.out.println("deleting" + tag + "from board#"+boardId);
            server.deleteTag(tag, boardId);
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
        // checks if the edited tag has a name
        if(title.length() == 0)
        {
            // shows the warning text and doesn't change the tag
            warningLabel.setText("You cannot have a\ntag without a name");
            refresh();
            return;
        }
        Color fxColor = colorPicker.getValue();
        int intColor = colorParseToInt(fxColor);
        Tag newTag = new Tag(title, intColor);
        if(oldTag!=null){
            server.editTag(boardId, oldTag.getId(), newTag);
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
        websocketClient.unsubscribe("/topic/tags/"+boardId);
    }

    /**
     * board label getter
     * @return Label
     */
    @FXML
    public Label getLabelBoard() {
        return labelBoard;
    }

    /**
     * title textfield getter
     * @return TextField
     */
    @FXML
    public TextField getTfTitle() {
        return tfTitle;
    }

    /**
     * warning label getter
     * @return Label
     */
    @FXML
    public Label getWarningLabel() {
        return warningLabel;
    }

    /**
     * tag title label getter
     * @return Label
     */
    @FXML
    public Label getTagTitleLabel() {
        return tagTitleLabel;
    }

    /**
     * tag color label getter
     * @return Label
     */
    @FXML
    public Label getTagColorLabel() {
        return tagColorLabel;
    }

    /**
     * add tag button getter
     * @return Button
     */
    @FXML
    public Button getAddTagButton() {
        return addTagButton;
    }

    /**
     * edit tag button getter
     * @return Button
     */
    @FXML
    public Button getEditTagButton() {
        return editTagButton;
    }

    /**
     * delete tag button getter
     * @return Button
     */
    @FXML
    public Button getDeleteTagButton() {
        return deleteTagButton;
    }

    /**
     * go back button button getter
     * @return Button
     */
    @FXML
    public Button getGoBackButton() {
        return goBackButton;
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        tagListView.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }

}
