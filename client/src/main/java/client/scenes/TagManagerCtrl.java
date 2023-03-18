package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class TagManagerCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Tag> data;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tagListView.setItems(getTagList());
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

    public ObservableList<Tag> getTagList(){
        List<Tag> tags = server.getTags();
        ObservableList<Tag> tagList = FXCollections.observableList(tags);
        return tagList;
    }

}
