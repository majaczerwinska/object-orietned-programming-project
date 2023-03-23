package client.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SubTaskComponent extends HBox {
    @FXML
    private Label labelTitle;

    /**
     * The constuctor for the component
     */
    public SubTaskComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/SubTaskComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(SubTaskComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String title;

    /**
     * Getter for title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title subtask
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
        labelTitle.setText(this.title);
    }
}
