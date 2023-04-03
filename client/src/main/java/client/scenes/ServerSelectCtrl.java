package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ServerSelectCtrl {

    private Preferences prefs;
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

    /**
     *
     * @param server   -
     * @param mainCtrl -
     */
    @Inject
    public ServerSelectCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
            setConnectionStatus(1);
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

    private void setConnectionStatus(int status) {
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

    /**
     * Event handler for the test connection button
     */
    public void onTestConnection() {
        String ip = serverAddress;
        System.out.println("testing connection to server " + ip);
        setConnectionStatus(0);
        int res = server.testConnection(ip);
        System.out.println("Server responded with status code : " + res);
        setConnectionStatus(res);
    }

    /**
     * Enters the server (goes to select board scene)
     */
    public void enterServer() {
        onTestConnection();
        if (!connectionStatus.textProperty().get().equals("200 Successful")) {
            System.out.println(connectionStatus.textProperty().get());
            System.out.println("Tried to enter invalid server (" + serverAddress + "), aborting");
            return;
        }
        System.out.println("entering server " + serverAddress);
        setServer(serverAddress);
        mainCtrl.setStompSession();
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

}
