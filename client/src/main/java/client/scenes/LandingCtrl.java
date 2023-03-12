package client.scenes;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class LandingCtrl {

    public void onPublicClickEvent(MouseEvent mouseEvent) {
        System.out.println("public button was clicked!!!");
    }

    public void onTeamActionEvent(ActionEvent actionEvent) {
        System.out.println("team action event :)");
    }
}
