/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.components.CardComponent;
import client.components.CardListComponent;
import commons.Card;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.stage.Screen;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MainCtrl {

    private Stage primaryStage;
    private Stage locker;

    private LandingCtrl landingCtrl;
    private Scene landing;

    private CardCtrl cardCtrl;
    private Scene card;

    private PublicBoardCtrl publicBoardCtrl;
    private Scene publicBoard;

    private BoardSelectCtrl boardSelectCtrl;
    private Scene boardSelect;

    private PopupJoinCtrl popupJoinCtrl;
    private Scene popupJoin;

    private TagManagerCtrl tagManagerCtrl;
    private Scene tagManager;
    private TaskCreatorCtrl taskCreatorCtrl;
    private Scene taskCreator;
    private BoardOverviewCtrl boardOverviewCtrl;
    private Scene boardOverwiew;
    private BoardCreationCtrl boardCreationCtrl;
    private Scene boardCreation;

    private ServerSelectCtrl serverSelectCtrl;
    private Scene serverSelect;

    private EditBoardCtrl editBoardCtrl;
    private Scene editBoard;

    private ListCreationCtrl listCreationCtrl;
    private Scene listCreate;

    private HelpCtrl helpCtrl;
    private Scene helpScene;

    private ListEditCtrl listEditCtrl;
    private Scene listEdit;
    private LockInUnlockedBoardCtrl lockInUnlockedBoardCtrl;
    private Scene unlocked;

    private ProvidePasswordCtrl providePasswordCtrl;
    private Scene providePassword;

    private EditPasswordCtrl editPasswordCtrl;
    private Scene editPassword;

    private CustomizationCtrl customizationCtrl;
    private Scene customization;
    private WarningCtrl warningCtrl;
    private Scene warning;

    private TagPopUpCtrl tagPopUpCtrl;
    private Scene tagPopUpScene;


    public Map<Integer, CardComponent> cardIdComponentMap;

    /**
     * @param landing
     * @param primaryStage
     * @param card
     * @param publicBoard
     * @param boardSelect
     * @param popup
     * @param tagManager
     * @param listCreate
     * @param select
     * @param boardOverview
     * @param editBoard
     * @param boardCreation
     * @param help
     * @param tagPopUp
     * @param taskCreator
     * @param listEdit
     * @param unlocked
     * @param locker
     * @param providePassword
     * @param editPassword
     * @param customization
     * @param warning
     */
    public void initialize(Stage primaryStage,
                           Stage locker,
                           Pair<LandingCtrl, Parent> landing,
                           Pair<CardCtrl, Parent> card,
                           Pair<PublicBoardCtrl, Parent> publicBoard,
                           Pair<BoardSelectCtrl, Parent> boardSelect,
                           Pair<PopupJoinCtrl, Parent> popup,
                           Pair<TagManagerCtrl, Parent> tagManager,
                           Pair<ListCreationCtrl, Parent> listCreate,
                           Pair<ServerSelectCtrl, Parent> select,
                           Pair<BoardOverviewCtrl, Parent> boardOverview,
                           Pair<BoardCreationCtrl, Parent> boardCreation,
                           Pair<TaskCreatorCtrl, Parent> taskCreator,
                           Pair<ListEditCtrl, Parent> listEdit,
                           Pair<EditBoardCtrl, Parent> editBoard,
                           Pair<HelpCtrl, Parent> help,
                           Pair<TagPopUpCtrl, Parent> tagPopUp,
                           Pair<LockInUnlockedBoardCtrl, Parent> unlocked,
                           Pair<ProvidePasswordCtrl, Parent> providePassword,
                           Pair<CustomizationCtrl, Parent> customization,
                           Pair<EditPasswordCtrl, Parent> editPassword,
                           Pair<WarningCtrl, Parent> warning
                           ) {

        this.cardIdComponentMap = new HashMap<>();
        System.setProperty("javafx.dnd.delayedDragCallback", "false");

        this.primaryStage = primaryStage;
        this.locker = locker;

        locker.initModality(Modality.APPLICATION_MODAL);
        locker.initOwner(primaryStage);
        this.landingCtrl = landing.getKey();
        this.landing = new Scene(landing.getValue());

        this.cardCtrl = card.getKey();
        this.card = new Scene(card.getValue());

        this.publicBoardCtrl = publicBoard.getKey();
        this.publicBoard = new Scene(publicBoard.getValue());

        this.boardSelectCtrl = boardSelect.getKey();
        this.boardSelect = new Scene(boardSelect.getValue());

        this.popupJoinCtrl = popup.getKey();
        this.popupJoin = new Scene(popup.getValue());

        this.tagManagerCtrl = tagManager.getKey();
        this.tagManager = new Scene(tagManager.getValue());

        this.listCreationCtrl = listCreate.getKey();
        this.listCreate = new Scene(listCreate.getValue());

        this.taskCreatorCtrl = taskCreator.getKey();
        this.taskCreator = new Scene(taskCreator.getValue());

        this.serverSelectCtrl = select.getKey();
        this.serverSelect = new Scene(select.getValue());

        this.boardOverviewCtrl = boardOverview.getKey();
        this.boardOverwiew = new Scene(boardOverview.getValue());

        this.boardCreationCtrl = boardCreation.getKey();
        this.boardCreation = new Scene(boardCreation.getValue());

        this.listEditCtrl = listEdit.getKey();
        this.listEdit = new Scene(listEdit.getValue());

        this.editBoardCtrl = editBoard.getKey();
        this.editBoard = new Scene(editBoard.getValue());

        this.helpCtrl = help.getKey();
        this.helpScene = new Scene(help.getValue());

        this.tagPopUpCtrl = tagPopUp.getKey();
        this.tagPopUpScene = new Scene(tagPopUp.getValue());


        this.lockInUnlockedBoardCtrl = unlocked.getKey();
        this.unlocked = new Scene(unlocked.getValue());

        this.providePasswordCtrl = providePassword.getKey();
        this.providePassword = new Scene(providePassword.getValue());

        this.editPasswordCtrl = editPassword.getKey();
        this.editPassword = new Scene(editPassword.getValue());

        this.warningCtrl = warning.getKey();
        this.warning = new Scene(warning.getValue());

        this.customizationCtrl = customization.getKey();
        this.customization = new Scene(customization.getValue());

        showLanding();
        primaryStage.show();
    }

    /**
     * prompt the user to select a server ip, defaults to localhost:8080
     */
    public void showServerSelect() {
        primaryStage.setTitle("Select Talio Server");
        // styling the page 

        // setting the font family for the text
        serverSelectCtrl.getSelectedServerLabel().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getConnectionLabel().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getSelectTalioServer().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getIpFieldHeader().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getConnectionStatus().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getSelectedServer().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getTestConnection().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getAddServer().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getEnterServer().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getGoBackButton().setStyle("-fx-font-family: Avenir Book;");
        serverSelectCtrl.getRemoveServer().setStyle("-fx-font-family: Avenir Book;");

        // round the corners for the buttons and text fields
        serverSelectCtrl.getIpField().setStyle("-fx-background-radius: 7;");
        serverSelectCtrl.getTestConnection().setStyle("-fx-background-radius: 7;");
        serverSelectCtrl.getAddServer().setStyle("-fx-background-radius: 7;");
        serverSelectCtrl.getEnterServer().setStyle("-fx-background-radius: 7;");
        serverSelectCtrl.getGoBackButton().setStyle("-fx-background-radius: 7;");
        serverSelectCtrl.getRemoveServer().setStyle("-fx-background-radius: 7;");

        primaryStage.setScene(serverSelect);
        primaryStage.setResizable(true);
        serverSelectCtrl.refresh();
    }

    /**
     *
     */
    public void showLanding() {
        primaryStage.setTitle("Landing page!!");
        primaryStage.setScene(landing);
    }

    /**
     * Shows the popup when choosing to lock it
     *
     * @param boardID the id of the board
     */
    public void showLockInUnlockedBoard(int boardID) {
        locker.setTitle("Do you want to lock!!");
        locker.setScene(unlocked);
        lockInUnlockedBoardCtrl.boardID = boardID;
        locker.setResizable(false);
        locker.showAndWait();
    }

    /**
     * Shows the popup for providing a password
     *
     * @param boardID the id of the board
     */
    public void showProvidePassword(int boardID) {
        locker.setTitle("Provide password!!");
        locker.setScene(providePassword);
        providePasswordCtrl.boardID = boardID;
        locker.setResizable(false);
        locker.showAndWait();
    }

    /**
     * Shows the popup for providing a password
     *
     * @param boardID the id of the board
     */
    public void showEditPassword(int boardID) {
        locker.setTitle("Edit password!!");
        locker.setScene(editPassword);
        editPasswordCtrl.boardID = boardID;
        editPasswordCtrl.refresh();
        locker.setResizable(false);
        locker.showAndWait();
    }

    /**
     * Shows the popup for alerting that the board is rtead-only
     * @param boardID the id of the board
     */
    public void showWarning(int boardID) {
        locker.setTitle("Read-only!!");
        locker.setScene(warning);
        warningCtrl.boardID = boardID;
        locker.setResizable(false);
        locker.showAndWait();
    }

    /**
     * closes the pop up of locker
     */
    public void closeLocker() {
        locker.close();
    }


    /**
     *
     */
    public void showPublicBoard() {
        primaryStage.setTitle("Public Board :)");
        primaryStage.setScene(publicBoard);
        primaryStage.show();
        publicBoardCtrl.refresh();
    }

    /**
     * shows board selection scene
     */
    public void showSelect() {
        primaryStage.setTitle("Board selection");
        // styling the board selection page

        // setting the font family for the text
        boardSelectCtrl.getCreateButton().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getRemoveButton().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getJoinButton().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getBackButton().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getDoubleClickText().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getJoinExistingLabel().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getEnterKeyLabel().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getCreateButton().setStyle("-fx-font-family: Avenir Book;");
        boardSelectCtrl.getOrYouLabel().setStyle("-fx-font-family: Avenir Book;");

        // rounding the corners of the buttons and text fields
        boardSelectCtrl.getCreateButton().setStyle("-fx-background-radius: 7;");
        boardSelectCtrl.getRemoveButton().setStyle("-fx-background-radius: 7;");
        boardSelectCtrl.getJoinButton().setStyle("-fx-background-radius: 7;");
        boardSelectCtrl.getBackButton().setStyle("-fx-background-radius: 7;");
        boardSelectCtrl.getBoardKeyTextField().setStyle("-fx-background-radius: 7;");

        primaryStage.setScene(boardSelect);
        boardSelectCtrl.warning.setText("");
        primaryStage.show();
        boardSelectCtrl.refresh();
    }

    /**
     * shows a popup
     */
    public void showPopup() {
        locker.setTitle("Something went wrong");
        locker.setScene(popupJoin);
        popupJoinCtrl.refresh();
        locker.show();
    }


    /**
     * Shows the tag manager scene
     *
     * @param boardID the board id for which to show the tag manager
     */
    public void showTagManager(int boardID) {
        primaryStage.setTitle("Tag Manager :)");
        primaryStage.setScene(tagManager);
        tagManagerCtrl.boardId = boardID;
        //Later combine these methods into one refresh method
        tagManagerCtrl.setLabelBoard();
        tagManagerCtrl.refresh();
        tagManagerCtrl.subscribe();
        primaryStage.show();
    }


    /**
     * @param cardComponent card component instance
     * @return int card id
     */
    public Integer cardComponentToCardId(CardComponent cardComponent) {
        for (int i : this.cardIdComponentMap.keySet()) {
            if (this.cardIdComponentMap.get(i).equals(cardComponent)) {
                return i;
            }
        }
        return null;
    }

//    /**
//     *
//     * @param hbox card component hbox
//     * @return int card id
//     */
//    public Integer cardComponentToCardId(HBox hbox) {
//        for (int i : this.cardIdComponentMap.keySet()) {
//            if (this.cardIdComponentMap.get(i).equals(cardComponent)) {
//                return i;
//            }
//        }
//        return null;
//    }

    /**
     * Method that parses fxColor to int
     *
     * @param fxColor the color to be parsed
     * @return return the color awtColor
     */
    public static int colorParseToInt(Color fxColor) {
        return ((int) (fxColor.getRed() * 255) << 16)
                | ((int) (fxColor.getGreen() * 255) << 8)
                | (int) (fxColor.getBlue() * 255);
    }

    /**
     * Method that parses int to fxColor
     *
     * @param intColor the color to be parsed
     * @return return the color fxColor
     */
    public static Color colorParseToFXColor(int intColor) {
        return Color.rgb(
                (intColor >> 16) & 0xFF, // red component
                (intColor >> 8) & 0xFF, // green component
                intColor & 0xFF // blue component
        );
    }


    /**
     * shows a scene where you can create a new list and add it to the public board
     *
     * @param boardId - board id
     */
    public void showListCreate(int boardId) {
        locker.setTitle("List creation");
        locker.setScene(listCreate);
        listCreationCtrl.boardID = boardId;
        locker.setResizable(false);
        locker.showAndWait();
    }


    /**
     * Shows the task creator scene
     */
    public void showTaskCreator() {
        primaryStage.setTitle("Task creator :)");
        primaryStage.setScene(taskCreator);
        primaryStage.show();
    }

    /**
     * Edit board with given boardID
     *
     * @param boardID the boardID of the board
     */
    public void showEditBoard(int boardID) {
        locker.setTitle("Show edit board :)");
        locker.setScene(editBoard);
        editBoardCtrl.boardId = boardID;
        locker.setResizable(false);
        editBoardCtrl.openScene(boardID);
        locker.showAndWait();
    }

    /**
     * Shows board overview
     *
     * @param boardID the id of the board to join
     */
    public void showBoardOverview(int boardID){
        prepareBoard(boardID);
        boardOverviewCtrl.refresh(null, false);
    }

    /**
     * show board overview
     * @param boardID the id of the board to join
     * @param saveCardPositions whether to update card position attributes in the server
     */
    public void showBoardOverview(int boardID, Boolean saveCardPositions) {
        prepareBoard(boardID);
        boardOverviewCtrl.refresh(null, saveCardPositions);
    }

    /**
     * show board overview util function, prevent duplicate code
     * @param boardID the board's id
     */
    private void prepareBoard(int boardID) {
        primaryStage.setTitle("Board overview :)");
        boardOverviewCtrl.boardID = boardID;
        primaryStage.setScene(boardOverwiew);
        boardOverviewCtrl.refreshName(boardID);
        primaryStage.show();
        //We later have to combine all these methods we call into one refresh method in boardOverviewCtrl
        boardOverviewCtrl.setBoardName();
        boardOverviewCtrl.setColor();
        boardOverviewCtrl.setLock();
        boardOverviewCtrl.refreshListViewTags();
    }

    /**
     * Calls the methods to create a stomp session in boardOverviewCtrl and tagManagerCtrl
     */
    public void setStompSession() {
        boardOverviewCtrl.setStompSession();
        tagManagerCtrl.setStompSession();
        cardCtrl.setStompSession();
        listCreationCtrl.setStompSession();
        customizationCtrl.setStompSession();
    }

    /**
     * Subscribes to endpoint that listens to all updates of cards and lists from a specific board
     *
     * @param boardId the boarId from the board we want updates from
     */
    public void subscribeToBoard(int boardId) {
        boardOverviewCtrl.subscribeToBoard(boardId);
    }

    /**
     * Subscribes to endpoint that listens to all updates of tags from a specific board
     *
     * @param boardId the boarId from the board we want updates from
     */
    public void subscribeToTagsFromBoard(int boardId) {
        boardOverviewCtrl.subscribeToTagsFromBoard(boardId);
    }


    /**
     * calls methood from boardoverview to color board's font
     *
     * @param boardId
     * @param color
     */
    public void colorBF(int boardId, int color) {
        boardOverviewCtrl.boardID = boardId;
        boardOverviewCtrl.colorFont(color);
    }

//    public void colorLF(int boardId, int listId, int color){
//        CardListComponent list = new CardListComponent(this, boardId, listId);
//                list.colorFont(color);
//    }

    /**
     * show card overview
     *
     * @param cardID  card id
     * @param boardID board id
     * @param isLocked whether toe board is locked
     */
    public  void showCard(int cardID, int boardID, boolean isLocked){
        locker.setTitle("Card overview :)");
        locker.setScene(card);
        cardCtrl.cardID = cardID;
        cardCtrl.boardID = boardID;
        cardCtrl.isLocked = isLocked;
        if(isLocked) cardCtrl.disable();
        else cardCtrl.enable();
        cardCtrl.setInfo();
        cardCtrl.registerForDeleted();
        locker.setOnCloseRequest(e -> cardCtrl.stopPollingForDeletedCard());
        cardCtrl.refresh();
        locker.setResizable(false);
        locker.showAndWait();

    }

    /**
     * Shows board creation scene
     */
    public void showBoardCreation() {
        locker.setTitle("Board creation overview :)");
        // styling the page

        // setting the font size for the text
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        boardCreationCtrl.getTitleLabel().setFont(Font.font(screenWidth * 0.03));
        boardCreationCtrl.getBoardKeyLabel().setFont(Font.font(screenWidth * 0.015));
        boardCreationCtrl.getBoardTitleLabel().setFont(Font.font(screenWidth * 0.015));
        boardCreationCtrl.getStopCreatingBoardButton().setFont(Font.font(screenWidth * 0.012));
        boardCreationCtrl.getCreateBoardButton().setFont(Font.font(screenWidth * 0.015));

        // setting the font family for the text
        boardCreationCtrl.getTitleLabel().setStyle("-fx-font-family: Avenir Book;");
        boardCreationCtrl.getBoardKeyLabel().setStyle("-fx-font-family: Avenir Book;");
        boardCreationCtrl.getBoardTitleLabel().setStyle("-fx-font-family: Avenir Book;");
        boardCreationCtrl.getCreateBoardButton().setStyle("-fx-font-family: Avenir Book;");
        boardCreationCtrl.getStopCreatingBoardButton().setStyle("-fx-font-family: Avenir Book;");

        // round the corners for the text areas
        boardCreationCtrl.getBoardKeyTextField().setStyle("-fx-background-radius: 7;");
        boardCreationCtrl.getBoardTitleTextField().setStyle("-fx-background-radius: 7;");

        // round the corners of the buttons
        boardCreationCtrl.getCreateBoardButton().setStyle("-fx-background-radius: 7;");
        boardCreationCtrl.getStopCreatingBoardButton().setStyle("-fx-background-radius: 7;");

        locker.setScene(boardCreation);
        locker.setResizable(false);
        locker.showAndWait();
    }

    /**
     * Shows the help page
     */
    public void showHelpPage() {
        primaryStage.setTitle("Help Page");
        primaryStage.setScene(helpScene);
        primaryStage.show();
    }

    /**
     * call the select ctrl savekey method to update after creating a board
     *
     * @param boardkey the board key to be saved to the client
     */
    public void saveBoardByKey(String boardkey) {
        boardSelectCtrl.saveBoardKey(boardkey);
        boardSelectCtrl.refresh();
    }

    /**
     * shows scene for editing the list
     *
     * @param listId
     * @param boardId
     */
    public void showListEdit(int listId, int boardId) {
        locker.setTitle("List edit");
        locker.setScene(listEdit);
        listEditCtrl.listId = listId;
        listEditCtrl.boardId = boardId;
        locker.setResizable(false);
        locker.showAndWait();
    }

    /**
     * refresh board overview scene with newly polled data from the database
         * @param saveCardPositions whether to save card position attributes
     */
    public void refreshBoardOverview(Boolean saveCardPositions)  {
        boardOverviewCtrl.refresh(null, saveCardPositions);
    }

    /**
     * refreshes
     */
    public void refreshListColours() {
        customizationCtrl.refresh();
        customizationCtrl.colourlist();
    }

    /**
     * add event listener for the enter key, intermediate function
     *
     * @param listID the list mouse is currently in
     */
    public void addEnterKeyListener(int listID) {
        boardOverviewCtrl.addEnterKeyListener(listID);
    }

    /**
     * refresh board after 200 milliseconds
     * waits for database to update before pulling data again and showing in client
     */
    public void timeoutBoardRefresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh(null, false);
        });
        try {
            future.get(200, TimeUnit.MILLISECONDS); // set a timeout of 5 seconds
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true); // cancel the task if it takes too long
            // handle the timeout exception here
            e.printStackTrace();
            System.out.println("time out exception in main controller");
        }
        executor.shutdown();
    }


    /**
     * refresh board overview after custom timeout
     *
     * @param mil the timeout in milliseconds
     */
    public void timeoutBoardRefresh(int mil) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh(null, false);
        });
        try {
            future.get(mil, TimeUnit.MILLISECONDS); // set a timeout of 5 seconds
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true); // cancel the task if it takes too long
            // handle the timeout exception here
            e.printStackTrace();
            System.out.println("time out exception in main controller");
        }
        executor.shutdown();
    }

    /**
     * Creates a card
     *
     * @param listID the id of the list the card is added to
     * @return the card that was created
     */
    public Card createCard(int listID) {
        return boardOverviewCtrl.createCard(listID);
    }

    /**
     * refresh a specific list
     *
     * @param listID    the lists id
     * @param component the cardlist component
     */
    public void refreshListView(int listID, CardListComponent component) {
        boardOverviewCtrl.refreshList(listID, component);
    }

    /**
     * takes you to the customization scene
     *
     * @param boardId
     */
    public void showCustomization(int boardId) {
        primaryStage.setTitle("Customization");
        primaryStage.setScene(customization);
        customizationCtrl.boardId = boardId;
        customizationCtrl.refresh();

        primaryStage.show();
    }

    /**
     * shows board selection scene
     *
     * @param boardID the ID of the board
     */
    public void showTagPopUp(int boardID) {
        locker.setTitle("Tag pop-up");
        locker.setScene(tagPopUpScene);
        locker.setResizable(false);
        tagPopUpCtrl.refresh();
        tagPopUpCtrl.setBoardID(boardID);
        locker.showAndWait();

    }

    /**
     * shows board selection scene
     */
    public void showHelpScene() {
        primaryStage.setTitle("Help Scene");
        primaryStage.setScene(helpScene);
        primaryStage.show();
        helpCtrl.refresh();
    }

        /** gets the boardOverview Controller
         * @return the boardOverview Controller
         */
        public BoardOverviewCtrl getBoardOverviewCtrl() {
            return this.boardOverviewCtrl;
        }

        /**
         * Gets the boardOverview Scene
         * @return the boardOverview Scene
         */
        public Scene getBoardOverviewScene() {
            return this.boardOverwiew;
        }

    }


