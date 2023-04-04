package client.services;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class ServerSelectService {

    /**
     * switch for connection messages
     * @param status response code
     * @param connectionStatus text label
     */
    public void setConnectionStatus(int status, Label connectionStatus) {
        switch (status) {
            case 1: // unknown
                connectionStatus.setText("Unknown");
                connectionStatus.setTextFill(Color.BLACK);
                break;
            case 0: // loading
                connectionStatus.setText("Loading...");
                connectionStatus.setTextFill(Color.BLACK);
                break;
            case 200: // Connection Successful
                connectionStatus.setText("200 Successful");
                connectionStatus.setTextFill(Color.GREEN);
                break;
            case 301:
                connectionStatus.setText("301 Moved Permanently");
                connectionStatus.setTextFill(Color.CORAL);
                break;
            case 404: // Not found
                connectionStatus.setText("404 Not Found");
                connectionStatus.setTextFill(Color.RED);
                break;
            case -1: // Timeout
                connectionStatus.setText("Server not found \n(408 timeout)");
                connectionStatus.setTextFill(Color.ORANGERED);
                break;
            case -2: // not a talio server
                connectionStatus.setText("Not a talio server");
                connectionStatus.setTextFill(Color.GOLDENROD);
                break;
        }
    }
}
