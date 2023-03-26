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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


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
    private TextArea taDescription;
    @FXML
    private TextField tfColor;


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
        ObservableList<Tag> tagList = FXCollections.observableList(tags);
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
        String description = taDescription.getText();
        int color = tryColorParse(tfColor.getText());
        Tag tag = new Tag(title, description, color);
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
        String description = taDescription.getText();
        int color = tryColorParse(tfColor.getText());
        Tag newTag = new Tag(title, description, color);
        server.editTag(oldTag, newTag);
        refresh();
    }

    /**
     * Method that tries to parse the color into an int
     * @param color the color retrieved from the text field
     * @return return the color as an int or the default color if it wasn't able to parse
     */
    public static int tryColorParse(String color){
        try {
            return Integer.parseInt(color);
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Format");
            alert.setHeaderText("Color field must be a number");
            alert.setContentText("Setting the color to default");
            alert.showAndWait();
            return 0xffffff;
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
            taDescription.setText(tag.getDescription());
            tfColor.setText(Integer.toString(tag.getColor()));
        }
    }

    /**
     * Clears all text fields
     */
    public void clearTextFields(){
        tfTitle.clear();
        taDescription.clear();
        tfColor.clear();
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
