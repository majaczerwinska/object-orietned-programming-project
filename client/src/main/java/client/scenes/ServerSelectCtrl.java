package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class ServerSelectCtrl {

    private Preferences prefs;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    // the key for the CSV String saved in preferences.
    private final String ipID = "savedIPs";

    @FXML
    private TextField ipField;


    @FXML
    private Text connectionStatus;

    @FXML
    private Text selectedServer;

    @FXML
    private Button testConnection;

    @FXML
    private Button addServer;

    @FXML
    private Button enterServer;

    @FXML
    private ListView<String> servers;


    /**
     *
     * @param server -
     * @param mainCtrl -
     */
    @Inject
    public ServerSelectCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.prefs = Preferences.userRoot().node(this.getClass().getName());
    }



    public void setServer(String ip) {
        ServerUtils.SERVER = ip;
    }

    /**
     * load the saved servers from a file
     */
    public void refresh() {

    }


    public void saveIP(String ip) {
        if (ip == null || ip.length() < 1) {
            System.out.println("Invalid ip saved");
            return;
        }
        String ips = prefs.get(ipID, "http://localhost:8080");
        ips += "," + ip;
        prefs.put(ipID, ips);
    }

    public List<String> getIPs() {
        String ips = prefs.get(ipID, "http://localhost:8080");
        List<String> ipList = new ArrayList<>(List.of(ips.split(",")));
        System.out.println(ipList);
        return ipList;
    }

    private void setConnectionStatus(int status) {
        switch (status) {
            case 0: // unknown
                connectionStatus.setText("[ Unknown ]");
                connectionStatus.setStyle("-fx-text-fill: black;");
                break;
            case 1: // Connection Successful
                connectionStatus.setText("[ Successful ]");
                connectionStatus.setStyle("-fx-text-fill: green;");
                break;
            case 2: // Failed
                connectionStatus.setText("[ Failed ]");
                connectionStatus.setStyle("-fx-text-fill: red;");
                break;
        }
    }
}
