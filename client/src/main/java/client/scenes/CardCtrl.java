package client.scenes;
import client.components.SubTaskComponent;
import client.services.CardService;
import client.utils.*;
import com.google.inject.Inject;
import javafx.application.Platform;
import commons.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CardCtrl implements Initializable {
    private final CardService service;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final WebsocketClient websocketClient;
    public boolean isViewed = false;
    private String nameOld = "";
    private String descriptionOld = "";

    @FXML
    private Label label;
    @FXML
    private Label readonly;
    @FXML
    private Label warning;
    @FXML
    private Button exit;
    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private TextField text;
    @FXML
    private TextArea area;
    @FXML
    private VBox vbox;

    @FXML
    private ListView<Tag> taglist;
    @FXML
    private Button removetag;
    @FXML
    private ListView<Tag> boardtags;
    @FXML
    private Accordion accordion;

    @FXML
    private ListView<Palette> palettes;

    @FXML
    private TextField theme;


    @FXML
    private TextField newTask;
    public int cardID;
    public int boardID;
    public boolean isLocked;


    /**
     *
     * @param service the services for this controller
     * @param server server utils instance
     * @param mainCtrl main ctrl instance
     * @param websocketClient the websocket client
     */
    @Inject
    public CardCtrl(CardService service, ServerUtils server, MainCtrl mainCtrl, WebsocketClient websocketClient){
        this.service = service;
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.websocketClient = websocketClient;
    }

    /**
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param rb
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField has received focus
            } else {
                // TextField has lost focus
                System.out.println("originalValue = " + nameOld + "\nnew value = " + text.getText());
                if (!text.getText().equals(nameOld)) {
                    websocketClient.sendMessage("/app/update/cardOverview/" + cardID, "Name edited");
                }
            }
        });
        area.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField has received focus
            } else {
                // TextField has lost focus
                System.out.println("originalValue = " + descriptionOld + "\nnew value = " + area.getText());
                if (!area.getText().equals(descriptionOld)) {
                    websocketClient.sendMessage("/app/update/cardOverview/" + cardID, "Description edited");
                }
            }
        });
    }

    /**
     * Creates stomp session
     */
    public void setStompSession(){
        websocketClient.setStompSession(ServerUtils.SERVER);
        System.out.println("StompSession created in card overview");
    }

    /**
     * Disconnects the stomp session
     */
    public void disconnectStompSession(){
        websocketClient.disconnect();
    }

    /**
     * Subscribes to endpoint that listens to all updates from a specific card
     * @param cardID the cardID from the card we want updates from
     */
    public void subscribeToCardOverview(int cardID){
        websocketClient.registerForMessages("/topic/cardOverview/"+cardID, String.class, update -> {
            Platform.runLater(() -> {
                System.out.println("payload in card overview: "+ update);
                if(update.contains("Subtask")){
                    System.out.println("Refreshing subtasks");
                    vbox.getChildren().clear();
                    displayTasks();
                } else if(update.contains("Pallete")){
                    setThemeText();
                } else if(update.contains("Tag")){
                    taglist.getItems().clear();
                    showTags();
                    showDropDown();
                } else if(update.contains("Name")){
                    nameOld = server.getCard(cardID).getTitle();
                    text.setText(nameOld);
                } else if(update.contains("Description")){
                    descriptionOld = server.getCard(cardID).getDescription();
                    area.setText(descriptionOld);
                }
            });
        });
    }

    /**
     * Subscribes to endpoint that listens to all updates from a board card that can affect a card (tags and palletes)
     * @param boardID the boardID from the card we want updates from
     */
    public void subscribeToCardOverviewBoardUpdates(int boardID){
        websocketClient.registerForMessages("/topic/tags/"+boardID, String.class, update -> {
            System.out.println("payload in card overview: "+ update);
            Platform.runLater(() -> {
                taglist.getItems().clear();
                showTags();
                showDropDown();
            });
        });
        websocketClient.registerForMessages("/topic/palletes/"+boardID, String.class, update -> {
            System.out.println("payload in card overview: "+ update);
            Platform.runLater(() -> {
                showDropDownColors();
            });
        });
    }

    private void unsubscribe(){
        websocketClient.unsubscribe("/topic/cardOverview/"+cardID);
        websocketClient.unsubscribe("/topic/tags/"+boardID);
        websocketClient.unsubscribe("/topic/palletes/"+boardID);
    }

    /**
     * Sends message to /app/dest
     * @param dest destination to send message to
     * @param payload message to send
     */
    public void sendMessage(String dest, String payload){
        websocketClient.sendMessage(dest, payload);
    }

    /**
     * Registers deletion for the card you are viewing
     */
    public void registerForDeleted(){
        server.registerForDeletedCard(cardID, deleted -> {
            System.out.println("Card Deleted! -> show board overview");
            if(isViewed){
                Platform.runLater(() -> {
                    System.out.println("Closing pop up");
                    isViewed = false;
                    mainCtrl.closeLocker();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Card deleted");
                    alert.setHeaderText("This card got deleted!");
                    alert.setContentText("You were viewing a card that someone just deleted");
                    alert.show();
                });
            }

        });
    }

    /**
     * Cancels the future of the polling
     */
    public void stopPollingForDeletedCard(){
        server.stopPollingForDeletedCard();
    }

    /**
     * Shuts down the executor service
     */
    public void stopExecutorService(){
        server.stopExecutorService();
    }

    /**
     * displays tasks in vbox
     */
    public void displayTasks(){
        List<Task> tasks = server.getTasksFromCard(cardID);
        for (Task task : tasks) {
            SubTaskComponent taskComponent = new SubTaskComponent(cardID, this);
            taskComponent.setData(task);
            if(isLocked) taskComponent.disable();
            else taskComponent.enable();

            vbox.getChildren().add(taskComponent);
        }
    }

    /**
     * Disables write-mode
     */
    public void disable(){
        removetag.setOnAction(e ->{
            return;
        });
        boardtags.setOnMouseClicked(e ->{
            return;
        });
        text.setEditable(false);
        area.setEditable(false);
        newTask.setEditable(false);
        readonly.setText("This is read-only mode. You cannot edit.");
    }

    /**
     * Enables write-mode
     */
    public void enable(){
        removetag.setOnAction(e ->{
            removeTagFromCard();
        });
        boardtags.setOnMouseClicked(e ->{
            addTagToCard(e);
        });
        text.setEditable(true);
        area.setEditable(true);
        newTask.setEditable(true);
        readonly.setText("");
    }

    /**
     * Clears the card overview
     */
    public void clearCard(){
        vbox.getChildren().clear();
        taglist.getItems().clear();
        palettes.getItems().clear();
    }

    /**
     * exit card overview back to board overview
     */
    public void exit(){
        System.out.println(boardID + "cardexit");
        updatePallete();
        mainCtrl.closeLocker();
        mainCtrl.showBoardOverview(boardID);
        websocketClient.sendMessage("/app/update/card/"+boardID, "Done updating card in CardOverview");
        isViewed = false;
        server.stopPollingForDeletedCard();
        unsubscribe();
    }

    /**
     * create a new task in the cardoverview
     * @param event the keyboard event for [enter] key, tasks are added by pressing enter
     */
    public void createTask(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            if(newTask.getText().equals("")){
                label.setText("Required field!");
                return;
            }
            String name = newTask.getText();
            Task task = new Task(name);
            server.addTask(task,cardID);
            newTask.setText("");
            label.setText("");
            refresh();
            sendMessage("/app/update/cardOverview/" + cardID, "Subtask added");
        }
    }

    /**
     * refresh task list
     */
    public void refresh(){
        clearCard();
        displayTasks();
        showTags();
        showDropDown();
        showDropDownColors();
        setThemeText();
        escShortcut();
        shortcut();
    }

    private void escShortcut() {
        Scene scene = taglist.getScene();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                exit();
            }
        });
    }

    /**
     * launch card overview scene util
     */
    public void setInfo(){
        nameOld = server.getCard(cardID).getTitle();
        text.setText(nameOld);
        descriptionOld = server.getCard(cardID).getDescription();
        area.setText(descriptionOld);

    }

    /**
     * update method for when card info is changed
     */
    public void updateCard(){
        if(!text.getText().equals("")){
            Card card= new Card(text.getText());
            card.setDescription(area.getText());
            server.editCard(boardID, cardID,card, true);
            warning.setText("");
        }
    }

    /**
     * make sure name field is not null before saving
     */
    public void checkforNullName(){
        if(text.getText().equals("")){
            warning.setText("Name required!");
        }
        else{
            warning.setText("");
        }
    }

    /**
     * Shows the tags in this board that are not added to the card yet
     */
    public void showDropDown(){
        boardtags.setItems(service.getTagListOfBoard(server, boardID, cardID));
        boardtags.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            public void updateItem(Tag tag, boolean empty) {
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
     * Shows the tags in this board that are added to the card
     */
    public void showTags(){
        taglist.setItems(service.getTagListOfCard(server, cardID));
        taglist.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            public void updateItem(Tag tag, boolean empty) {
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
     * Shows the selected tag in the text field/area
     * @param event - clicking the mouse
     */
    public void addTagToCard(MouseEvent event){
        Tag tag = boardtags.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2 && tag!=null ){
            server.addTagToCard(boardID, tag.getId(), cardID);
            websocketClient.sendMessage("/app/update/cardOverview/" + cardID, "Tag added");
            showTags();
            showDropDown();

        }
    }

    /**
     * Remove a tag from a card
     */
    public void removeTagFromCard(){
        Tag tag = taglist.getSelectionModel().getSelectedItem();
        if(tag!=null){
            server.removeTagFromCard(tag.getId(), cardID);
            showDropDown();
            taglist.getItems().clear();
            showTags();
            websocketClient.sendMessage("/app/update/cardOverview/" + cardID, "Tag removed");
        }
    }

    /**
     * shows menu of palettes of colors to choose for the card
     */
    public void showDropDownColors(){
        palettes.setItems(getPalettes(boardID));
        palettes.setCellFactory(param -> new ListCell<Palette>() {
            @Override
            public void updateItem(Palette palette, boolean empty) {
                super.updateItem(palette, empty);
                if (empty || palette == null) {
                    setText(null);
                } else {
                    setText(palette.getName());
//                    String hexColor = String.format("#%06X", (0xFFFFFF & tag.getColor()));
//                    setStyle("-fx-control-inner-background: " + hexColor);
                }
            }
        });
    }

    /**
     * gets palettes from the board
     * @param boardId
     * @return - list of palettes
     */
    public ObservableList<Palette> getPalettes(int boardId){
        List<Palette> p = server.getPalettesFromBoard(boardId);
        ObservableList<Palette> palettes = FXCollections.observableArrayList(p);
        return palettes;
    }

    /**
     * Sets the text field of theme to cards current theme
     */
    private void setThemeText(){
        Card c = server.getCard(cardID);
        System.out.println(c.getPalette()+"-----------------");
        if(c.getPalette().equals("")){
            List<Palette> palettes = server.getPalettesFromBoard(boardID);
            for(Palette pal : palettes){
                if(pal.isIsdefault()){
                    theme.setText(pal.getName());
                    break;
                }
            }
        }
        else{
            theme.setText(c.getPalette());
        }
    }

    private void updatePallete(){
        if(!theme.getText().equals("")){
            List<Palette> palettes = server.getPalettesFromBoard(boardID);
            for(Palette pal : palettes){
                if(pal.getName().equals(theme.getText())){
                    Card card = server.getCard(cardID);
                    card.setColor(pal.getbColor());
                    card.setFcolor(pal.getfColor());
                    card.setPalette(pal.getName());
                    server.editCard(boardID, cardID, card, false);
                    websocketClient.sendMessage("/app/update/cardOverview/" + cardID, "Pallete updated");
                    break;
                }
            }
        }
    }

    /**
     * hopefully will apply changes of selected color to card and save them to DB
     * @param event
     */
    public void choose(MouseEvent event){
        Palette palette = palettes.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2 && palette!=null ){
            showDropDownColors();
        }
        theme.setText(palette.getName());
        updatePallete();
    }

    /**
     * Shortcut for opening the help scene
     */
    private void shortcut() {
        taglist.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SLASH) {
                mainCtrl.showHelpScene();
            }
        });
    }
}
