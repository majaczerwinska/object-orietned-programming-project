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
import javafx.scene.Node;
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

    private ColorPopUpCtrl colorPopUpCtrl;
    private Scene colorPopUpScene;

    private PaletteCreationCtrl paletteCreationCtrl;
    private Scene paletteCreation;


    private EnterAdminPassword adminPasswordCtrl;
    private Scene enterPasswordScene;


    private Admin adminCtrl;
    private Scene adminScene;
    public Map<Integer, CardComponent> cardIdComponentMap;

    /**
     *
     * @param primaryStage
     * @param locker
     * @param landing
     * @param card
     * @param publicBoard
     * @param boardSelect
     * @param popup
     * @param tagManager
     * @param listCreate
     * @param select
     * @param boardOverview
     * @param boardCreation
     * @param listEdit
     * @param editBoard
     * @param help
     * @param tagPopUp
     * @param colorPopUp
     * @param unlocked
     * @param providePassword
     * @param customization
     * @param editPassword
     * @param paletteCreate
     * @param warning
     * @param adminPwd
     * @param adminPage
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
                           Pair<ListEditCtrl, Parent> listEdit,
                           Pair<EditBoardCtrl, Parent> editBoard,
                           Pair<HelpCtrl, Parent> help,
                           Pair<TagPopUpCtrl, Parent> tagPopUp,
                           Pair<ColorPopUpCtrl, Parent> colorPopUp,
                           Pair<LockInUnlockedBoardCtrl, Parent> unlocked,
                           Pair<ProvidePasswordCtrl, Parent> providePassword,
                           Pair<CustomizationCtrl, Parent> customization,
                           Pair<EditPasswordCtrl, Parent> editPassword,
                           Pair<WarningCtrl, Parent> warning,
                           Pair<EnterAdminPassword, Parent> adminPwd,
                           Pair<Admin, Parent> adminPage,
                           Pair<PaletteCreationCtrl, Parent> paletteCreate
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

        this.colorPopUpCtrl = colorPopUp.getKey();
        this.colorPopUpScene = new Scene(colorPopUp.getValue());


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


        this.adminPasswordCtrl = adminPwd.getKey();
        this.enterPasswordScene = new Scene(adminPwd.getValue());
        this.adminCtrl = adminPage.getKey();
        this.adminScene = new Scene(adminPage.getValue());

        this.paletteCreationCtrl = paletteCreate.getKey();
        this.paletteCreation = new Scene(paletteCreate.getValue());
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
        this.appendStyle(serverSelectCtrl.getSelectedServerLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getConnectionLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getSelectTalioServer(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getIpFieldHeader(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getConnectionStatus(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getSelectedServer(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getTestConnection(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getAddServer(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getEnterServer(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getGoBackButton(),"-fx-font-family: Avenir Book;");
        this.appendStyle(serverSelectCtrl.getRemoveServer(),"-fx-font-family: Avenir Book;");

        // round the corners for the buttons and text fields
        String radiusStyle = "-fx-background-radius: 7;";
        appendStyle(serverSelectCtrl.getIpField(), radiusStyle);
        appendStyle(serverSelectCtrl.getTestConnection(), radiusStyle);
        appendStyle(serverSelectCtrl.getAddServer(), radiusStyle);
        appendStyle(serverSelectCtrl.getEnterServer(), radiusStyle);
        appendStyle(serverSelectCtrl.getGoBackButton(), radiusStyle);
        appendStyle(serverSelectCtrl.getRemoveServer(), radiusStyle);

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
        locker.setOnCloseRequest(e -> lockInUnlockedBoardCtrl.cancel());
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
        locker.setOnCloseRequest(e -> providePasswordCtrl.cancel());
        locker.showAndWait();
    }

    /**
     * Shows the popup for providing a password
     *
     * @param boardID the id of the board
     */
    public void showEditPassword(int boardID) {
        locker.setTitle("Edit password!!");
        // styling the edit password pop up

        // changing the font for the text
        this.appendStyle(editPasswordCtrl.getCancel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getEdit(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getEditPassLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getPassLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getRemove(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getVerifyLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getWarningPassLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editPasswordCtrl.getWarningVerifyLabel(),"-fx-font-family: Avenir Book;");

        // rounding the buttons and text-fields
        this.appendStyle(editPasswordCtrl.getCancel(),"-fx-background-radius: 7;");
        this.appendStyle(editPasswordCtrl.getEdit(),"-fx-background-radius: 7;");
        this.appendStyle(editPasswordCtrl.getPasswordTextField(),"-fx-background-radius: 7;");
        this.appendStyle(editPasswordCtrl.getPasswordVerifyTextField(),"-fx-background-radius: 7;");
        this.appendStyle(editPasswordCtrl.getRemove(),"-fx-background-radius: 7;");

        locker.setScene(editPassword);
        editPasswordCtrl.boardID = boardID;
        editPasswordCtrl.refresh();
        locker.setResizable(false);
        locker.setOnCloseRequest(e -> editPasswordCtrl.cancel());
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
        locker.setOnCloseRequest(e -> locker.close());
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
        String fontStyle = "-fx-font-family: Avenir Book;";
        appendStyle(boardSelectCtrl.getCreateButton(), fontStyle);
        appendStyle(boardSelectCtrl.getRemoveButton(), fontStyle);
        appendStyle(boardSelectCtrl.getJoinButton(), fontStyle);
        appendStyle(boardSelectCtrl.getBackButton(), fontStyle);
//        boardSelectCtrl.getDoubleClickText().setStyle("-fx-font-family: Avenir Book;");
        appendStyle(boardSelectCtrl.getJoinExistingLabel(), fontStyle);
        appendStyle(boardSelectCtrl.getEnterKeyLabel(), fontStyle);
        appendStyle(boardSelectCtrl.getCreateButton(), fontStyle);
        appendStyle(boardSelectCtrl.getOrYouLabel(), fontStyle);

        // rounding the corners of the buttons and text fields
        String radiusStyle = "-fx-background-radius: 7;";
        appendStyle(boardSelectCtrl.getCreateButton(), radiusStyle);
        appendStyle(boardSelectCtrl.getRemoveButton(), radiusStyle);
        appendStyle(boardSelectCtrl.getJoinButton(), radiusStyle);
        appendStyle(boardSelectCtrl.getBackButton(), radiusStyle);
        appendStyle(boardSelectCtrl.getBoardKeyTextField(), radiusStyle);

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
        locker.setOnCloseRequest(e -> locker.close());
        locker.showAndWait();
    }


    /**
     * Shows the tag manager scene
     *
     * @param boardID the board id for which to show the tag manager
     */
    public void showTagManager(int boardID) {
        primaryStage.setTitle("Tag Manager :)");
        // styling the tag manager page 

        // applying fonts
        String fontStyle = "-fx-font-family: Avenir Book;";
        appendStyle(tagManagerCtrl.getAddTagButton(), fontStyle);
        appendStyle(tagManagerCtrl.getDeleteTagButton(), fontStyle);
        appendStyle(tagManagerCtrl.getEditTagButton(), fontStyle);
        appendStyle(tagManagerCtrl.getGoBackButton(), fontStyle);
        appendStyle(tagManagerCtrl.getLabelBoard(), fontStyle);
        appendStyle(tagManagerCtrl.getTagColorLabel(), fontStyle);
        appendStyle(tagManagerCtrl.getTagTitleLabel(), fontStyle);
        appendStyle(tagManagerCtrl.getWarningLabel(), fontStyle);

        // rounding the buttons and text fields
        String radiusStyle = "-fx-background-radius: 7;";
        appendStyle(tagManagerCtrl.getAddTagButton(), radiusStyle);
        appendStyle(tagManagerCtrl.getDeleteTagButton(), radiusStyle);
        appendStyle(tagManagerCtrl.getEditTagButton(), radiusStyle);
        appendStyle(tagManagerCtrl.getGoBackButton(), radiusStyle);
        appendStyle(tagManagerCtrl.getTfTitle(), radiusStyle);

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
        locker.setOnCloseRequest(e -> listCreationCtrl.cancel());
        locker.showAndWait();
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
        // styling the edit board page

        // changing the font family for the texts
       // editBoardCtrl.getColorLabel().setStyle("-fx-font-family: Avenir Book;");
        this.appendStyle(editBoardCtrl.getExit(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editBoardCtrl.getNameLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editBoardCtrl.getSave(),"-fx-font-family: Avenir Book;");
        this.appendStyle(editBoardCtrl.getTitleLabel(),"-fx-font-family: Avenir Book;");

        // rounding the buttons and text fields
        appendStyle(editBoardCtrl.getExit(),"-fx-background-radius: 15;");
        appendStyle(editBoardCtrl.getName(),"-fx-background-radius: 7;");
        appendStyle(editBoardCtrl.getSave(),"-fx-background-radius: 7;");

        locker.setOnCloseRequest(e -> editBoardCtrl.exitButton());
        locker.showAndWait();
    }

    /**
     * Shows board overview
     *
     * @param boardID the id of the board to join
     */
    public void showBoardOverview(int boardID){
        prepareBoard(boardID);
        boardOverviewCtrl.refresh(null);
    }

    /**
     * show board overview
     * @param boardID the id of the board to join
     * @param isAdmin whether the user is in the board as an admin
     */
    public void showBoardOverview(int boardID, Boolean isAdmin) {
        boardOverviewCtrl.setAdmin(isAdmin);
        prepareBoard(boardID);
        boardOverviewCtrl.refresh(null);
    }

    /**
     * Appends style to a node, use this method everywhere instead of setStyle
     * @param node the node which you want to append style to
     * @param style the style you want to append
     */
    public void appendStyle(Node node, String style) {
        String existingStyle = node.getStyle();
        node.setStyle(existingStyle.replaceAll(style, "") + style);
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
        // We later have to combine all these methods we call into one refresh method in boardOverviewCtrl
        boardOverviewCtrl.setBoardName();
        boardOverviewCtrl.setLock();
        boardOverviewCtrl.refreshListViewTags();

        // adding some styling to the board overview
        String fontStyle = "-fx-font-family: Avenir Book;";
        String roundingStyle = "-fx-background-radius: 7;";
        String existingStyle;
        // changing the fonts to the labels and buttons
        appendStyle(boardOverviewCtrl.getBoardTitleLabel(), fontStyle);
        appendStyle(boardOverviewCtrl.getAddListButton(), fontStyle);
        appendStyle(boardOverviewCtrl.getBackButton(), fontStyle);
        appendStyle(boardOverviewCtrl.getBoardLabel(), fontStyle);
        appendStyle(boardOverviewCtrl.getBoardKeyLabel(), fontStyle);
        appendStyle(boardOverviewCtrl.getCustomizationButton(), fontStyle);
        appendStyle(boardOverviewCtrl.getEditBoardButton(), fontStyle);
        appendStyle(boardOverviewCtrl.getTagLabel(), fontStyle);
        appendStyle(boardOverviewCtrl.getTagManagerButton(), fontStyle);

        // rounding the buttons
        appendStyle(boardOverviewCtrl.getAddListButton(), roundingStyle);
        appendStyle(boardOverviewCtrl.getBackButton(), roundingStyle);
        appendStyle(boardOverviewCtrl.getCustomizationButton(), roundingStyle);
        appendStyle(boardOverviewCtrl.getEditBoardButton(), roundingStyle);
        appendStyle(boardOverviewCtrl.getLockButton(), roundingStyle);
        appendStyle(boardOverviewCtrl.getTagManagerButton(), roundingStyle);
        boardOverviewCtrl.setColor();
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


    /**
     * show card overview
     *
     * @param cardID  card id
     * @param boardID board id
     * @param isLocked whether toe board is locked
     */
    public  void showCard(int cardID, int boardID, boolean isLocked){
        cardCtrl.cardID = cardID;
        cardCtrl.boardID = boardID;
        cardCtrl.isLocked = isLocked;
        if(isLocked) cardCtrl.disable();
        else cardCtrl.enable();

        cardCtrl.setInfo();
        cardCtrl.registerForDeleted();
        cardCtrl.isViewed = true;
        locker.setOnCloseRequest(e -> {
            cardCtrl.stopPollingForDeletedCard();
            cardCtrl.exit();
        });
        cardCtrl.refresh();
        locker.setTitle("Card overview :)");
        locker.setScene(card);
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
        this.appendStyle(boardCreationCtrl.getTitleLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(boardCreationCtrl.getBoardKeyLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(boardCreationCtrl.getBoardTitleLabel(),"-fx-font-family: Avenir Book;");
        this.appendStyle(boardCreationCtrl.getCreateBoardButton(),"-fx-font-family: Avenir Book;");
        this.appendStyle(boardCreationCtrl.getStopCreatingBoardButton(),"-fx-font-family: Avenir Book;");

        // round the corners for the text areas
        this.appendStyle(boardCreationCtrl.getBoardKeyTextField(),"-fx-background-radius: 7;");
        this.appendStyle(boardCreationCtrl.getBoardTitleTextField(),"-fx-background-radius: 7;");

        // round the corners of the buttons
        this.appendStyle(boardCreationCtrl.getCreateBoardButton(),"-fx-background-radius: 7;");
        this.appendStyle(boardCreationCtrl.getStopCreatingBoardButton(),"-fx-background-radius: 7;");

        locker.setScene(boardCreation);
        locker.setResizable(false);
        locker.setOnCloseRequest(e -> boardCreationCtrl.stopCreatingBoardButtonHandler());
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
        listEditCtrl.setName();
        locker.setResizable(false);
        locker.setOnCloseRequest(e -> listEditCtrl.cancel());
        locker.showAndWait();
    }

    /**
     * refresh board overview scene with newly polled data from the database
     */
    public void refreshBoardOverview()  {
        boardOverviewCtrl.refresh(null);
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
     * refresh board overview after custom timeout
     *
     * @param mil the timeout in milliseconds
     */
    public void timeoutBoardRefresh(int mil) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            boardOverviewCtrl.refresh(null);
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
     * goes to scene for creating a new palette for that board's cards
     * @param boardId
     */
    public void showPaletteCreation(int boardId){
        primaryStage.setTitle("create");
        primaryStage.setScene(paletteCreation);
        paletteCreationCtrl.boardId = boardId;
        paletteCreationCtrl.refresh();
        primaryStage.show();
    }

    /**
     * shows board selection scene
     *
     * @param boardID the ID of the board
     * @param cardID the ID of the card
     */
    public void showTagPopUp(int boardID, int cardID) {
        locker.setTitle("Tag pop-up");
        // styling the pop up

        // adding fonts for the text
        this.appendStyle(tagPopUpCtrl.getGoBackButton(),"-fx-font-family: Avenir Book;");
//        tagPopUpCtrl.getName().setStyle("-fx-font-family: Avenir Book;");
//        tagPopUpCtrl.getTitle().setStyle("-fx-font-family: Avenir Book;");
//        tagPopUpCtrl.getPlusButton().setStyle("-fx-font-family: Avenir Book;");

        // rounding the buttons and text fields
        this.appendStyle(tagPopUpCtrl.getGoBackButton(),"-fx-background-radius: 7;");
//        tagPopUpCtrl.getPlusButton().setStyle("-fx-background-radius: 20;");
//        tagPopUpCtrl.getName().setStyle("-fx-background-radius: 7;");

        locker.setScene(tagPopUpScene);
        locker.setResizable(false);
        tagPopUpCtrl.setBoardAndCardID(boardID, cardID);
        tagPopUpCtrl.refresh();
        locker.setOnCloseRequest(e -> tagPopUpCtrl.back());
        locker.showAndWait();

    }


    /**
     * shows board selection scene
     *
     * @param boardID the ID of the board
     * @param cardID the ID of the card
     */
    public void showColorPopUp(int boardID, int cardID) {
        locker.setTitle("Color pop-up");
        // styling the pop up



        locker.setScene(colorPopUpScene);
        locker.setResizable(false);
        colorPopUpCtrl.setBoardAndCardID(boardID, cardID);
        colorPopUpCtrl.refresh();
        locker.setOnCloseRequest(e -> colorPopUpCtrl.back());
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


    /**
     * open the admin panel page.
     * called from the password check pop up, after having
     * received a valid authentication token
     * @param ip the server ip
     * @param token the authentication token received during the password check proccess
     */
    public void openAdminPanel(String ip, String token) {
            primaryStage.setTitle("Admin panel for server "+ip);
            primaryStage.setScene(adminScene);
            if (adminCtrl==null){
                throw new RuntimeException("admin panel is null for some reason");
            } /*else {
                System.out.println("admin ctrl is not null");
            }*/
            adminCtrl.setToken(token);
            adminCtrl.refresh(ip);
        }

    /**
     * show pop up for entering admin password
     * //todo currently obviously not a pop up. make it a pop up
     * @param ip the ip of the server joining
     */
    public void showAdminPasswordEnter(String ip) {
        primaryStage.setTitle("Admin pwd for "+ip);
        primaryStage.setScene(enterPasswordScene);
        adminPasswordCtrl.refresh(ip);
    }
}