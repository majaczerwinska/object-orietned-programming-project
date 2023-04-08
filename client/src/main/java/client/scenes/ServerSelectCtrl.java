package client.scenes;

import client.services.ServerSelectService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class ServerSelectCtrl {

    private final ServerSelectService service;
    private final Preferences prefs;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    // the key for the CSV String saved in preferences.
    private final String ipID = "savedIPs";

    public String serverAddress = "http://localhost:8080";

    @FXML
    private Label selectedServerLabel;

    @FXML
    private Label connectionLabel;

    @FXML
    private Label selectTalioServer;

    @FXML
    private TextField ipField;

    @FXML
    private Label ipFieldHeader;

    @FXML
    public Label connectionStatus;

    @FXML
    private Label selectedServer;

    @FXML
    private Button testConnection;

    @FXML
    private Button addServer;

    @FXML
    private Button enterServer;

    @FXML
    private Button goBackButton;

    @FXML
    private Button removeServer;

    @FXML
    private ListView<String> servers;

    @FXML
    private Button enterAsAdmin;

    /**
     * @param service the services for this controller
     * @param server server utils instance
     * @param mainCtrl main ctrl instance
     */
    @Inject
    public ServerSelectCtrl(ServerSelectService service, ServerUtils server, MainCtrl mainCtrl) {
        this.service = service;
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.prefs = Preferences.userRoot().node(this.getClass().getName());
    }

    /**
     * Sets the server to which the client sends the http requests
     * 
     * @param ip ip of the server
     */
    public void setServer(String ip) {
        ServerUtils.SERVER = ip;
    }

    /**
     * load the saved servers from a file
     */
    public void refresh() {
        servers.setItems(getAddressList());
        shortcut();
    }

    /**
     * updates public variable address
     * 
     * @param ip address
     * @return same address
     */
    public String updateAddress(String ip) {
        selectedServer.setText(ip);
        serverAddress = ip;
        return ip;
    }

    /**
     * get observableList instance for address list
     * 
     * @return ObservableList<IP Address Strings>
     */
    public ObservableList<String> getAddressList() {
        List<String> ips = getIPs();
        ObservableList<String> addressList = FXCollections.observableList(ips);
        return addressList;
    }

    /**
     * Event handler
     * Adds the address from the ipField to the list of known addresses
     */
    public void addIP() {
        String address = ipField.getText();
        List<String> knownIPs = getIPs();
        if (knownIPs.contains(address)) {
            ipFieldHeader.setText("That address is already saved!");
            ipFieldHeader.setTextFill(Color.YELLOWGREEN);
            refresh();
            return;
        }
        saveIP(address);
        ipField.setText("");
        ipFieldHeader.setText("Enter domain or IP address");
        refresh();
    }

    /**
     * save another ip to the list of known servers
     * 
     * @param ip the address to be saved
     */
    public void saveIP(String ip) {
        if (ip == null || ip.length() < 1) {
            System.out.println("Invalid ip saved");
            return;
        }
        String ips = prefs.get(ipID, "http://localhost:8080");
        ips += "," + ip;
        prefs.put(ipID, ips);
    }

    /**
     * event handler for the remove address button
     */
    public void onDeleteAddress() {
        String ip = selectedServer.getText();
        System.out.println("removing address : " + ip);
        removeIP(ip);
        refresh();
    }

    /**
     * Selects a server from the list
     */
    public void changeSelectedServer() {
        String address = servers.getSelectionModel().getSelectedItem();
        System.out.println("selected address " + address);
        if (address != null) {
            updateAddress(address);
            service.setConnectionStatus(1, connectionStatus);
        }
        System.out.println("Server " + address + " selected");
    }

    /**
     * removes the ip from the list of known addresses
     * 
     * @param ip the address to remove
     */
    public void removeIP(String ip) {
        if (ip == null || ip.length() < 1) {
            System.out.println("Invalid ip removal");
            return;
        }
        String ips = prefs.get(ipID, "http://localhost:8080");
        if (!ips.contains(",")) {
            prefs.put(ipID, "http://localhost:8080");
            return;
        }
        System.out.println("removing ip. before: " + ips);
        ips = ips.replace(ip + ",", "");
        ips = ips.replace("," + ip, "");
        System.out.println("after: " + ips);
        prefs.put(ipID, ips);
    }

    /**
     * Get the list of known server addresses
     * 
     * @return a list of IP addresses
     */
    public List<String> getIPs() {
        String ips = prefs.get(ipID, "http://localhost:8080");
        List<String> ipList = new ArrayList<>(List.of(ips.split(",")));
        System.out.println(ipList);
        return ipList;
    }



    /**
     * Event handler for the test connection button
     */
    public void onTestConnection() {
        String ip = serverAddress;
        System.out.println("testing connection to server " + ip);
        service.setConnectionStatus(0, connectionStatus);
        int res = server.testConnection(ip);
        System.out.println("Server responded with status code : " + res);
        service.setConnectionStatus(res, connectionStatus);
    }

    /**
     * Enters the server (goes to select board scene)
     */
    public void enterServer(){
        if (!verifyServerConnection()) return;
        mainCtrl.showSelect();
    }

    /**
     * general event listener, calls subsequent action functions
     * 
     * @param event mouse event
     */
    @FXML
    public void handleListClick(MouseEvent event) {
        changeSelectedServer();
        onListElementClick(event);
    }

    /**
     * event handler for clicking
     * 
     * @param event click details
     */
    @FXML
    public void onListElementClick(MouseEvent event) {
        refresh();
        ipField.setText(selectedServer.getText());
        if (event.getClickCount() == 2) {
            enterServer();
        }
    }

    /**
     * event handler for the go back to main menu button
     */

    public void goBackToMainMenuButtonHandler() {
        // refreshes the page and goes back to Landing
        refresh();
        mainCtrl.showLanding();
        // colour of the button is #a81b1b

    }


    /**
     * button listener for opening (not yet) pop up for the admin panel
     */
    public void enterAdminPage() {
        if (!verifyServerConnection()) return;
        mainCtrl.showAdminPasswordEnter(serverAddress);
    }

    private boolean verifyServerConnection() {
        onTestConnection();
        if (!connectionStatus.textProperty().get().equals("200 Successful")) {
            System.out.println(connectionStatus.textProperty().get());
            System.out.println("Tried to enter invalid server ("+serverAddress+"), aborting");
            return false;
        }
        System.out.println("entering server " + serverAddress);
        setServer(serverAddress);
        mainCtrl.setStompSession();
        return true;
    }
    /**
     * selected server label getter
     * @return Label
     */
    public Label getSelectedServerLabel() {
        return selectedServerLabel;
    }

    /**
     * connection label getter
     * @return Label
     */
    public Label getConnectionLabel() {
        return connectionLabel;
    }

    /**
     * select Talio server label getter
     * @return Label
     */
    public Label getSelectTalioServer() {
        return selectTalioServer;
    }

    /**
     * IP address field getter
     * @return TextField
     */
    public TextField getIpField() {
        return ipField;
    }

    /**
     * IP address label getter
     * @return Label
     */
    public Label getIpFieldHeader() {
        return ipFieldHeader;
    }

    /**
     * connection status label getter
     * @return Label
     */
    public Label getConnectionStatus() {
        return connectionStatus;
    }

    /**
     * selected server label getter
     * @return Label
     */
    public Label getSelectedServer() {
        return selectedServer;
    }

    /**
     * test connection button getter
     * @return Button
     */
    public Button getTestConnection() {
        return testConnection;
    }

    /**
     * add server button getter
     * @return Button
     */
    public Button getAddServer() {
        return addServer;
    }

    /**
     * enter server button getter
     * @return Button
     */
    public Button getEnterServer() {
        return enterServer;
    }

    /**
     * go back button getter
     * @return Button
     */
    public Button getGoBackButton() {
        return goBackButton;
    }

    /**
     * remove server button getter
     * @return Button
     */
    public Button getRemoveServer() {
        return removeServer;
    }

    /**
     * list view of servers getter
     * @return ListView
     */
    public ListView<String> getServers() {
        return servers;
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        goBackButton.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }
}
