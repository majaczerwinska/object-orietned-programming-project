package client.services;

import client.utils.ServerUtils;
import commons.Board;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

@Service
public class BoardService {

    /**
     * check if a board should be locked
     * @param server server utils instance
     * @param lock lock button
     * @param isAdmin is admin bool
     * @param boardID board's id
     * @param pref preferences instance
     * @return t/f should the board be unlocked
     */
    public boolean checkLock(ServerUtils server, Button lock, boolean isAdmin, int boardID, Preferences pref) {
        Board board = server.getBoard(boardID);
        if(board.getPassword().equals("") || board.getPassword()==null ){
            lock.setText("\uD83D\uDD13");
            lock.setStyle("-fx-background-color: white;");
            return true;
        }else{
            lock.setText("\uD83D\uDD12");
            checkForPref(server, pref);
            if (isAdmin) {
                lock.setStyle("-fx-background-color: green;");
                return true;
            } else if (pref.get(String.valueOf(boardID),"").equals("")){
                lock.setStyle("-fx-background-color: red;");
                return false;
            }
            else{
                lock.setStyle("-fx-background-color: green;");
                return true;
            }
        }
    }


    /**
     * Checks if the board is the correct board
     * @param pref preferences instance
     * @param server server utils instance
     */
    public void checkForPref(ServerUtils server, Preferences pref){
        for(Board b :server.getBoards()){
            if(!pref.get(String.valueOf(b.getId()),"").equals("") &&
                    !pref.get(String.valueOf(b.getId()),"notfound").equals(b.getPassword())) {
                pref.remove(String.valueOf(b.getId()));
            }
        }
    }


    /**
     * Creates an observable list with all tags
     * @param server server utils instance
     * @param boardID the board id we are in
     * @return an observable list with all tags
     */
    public ObservableList<Tag> getTagList(ServerUtils server, int boardID){
        List<Tag> t = server.getTagsFromBoard(boardID);
        List<Tag> tags = new ArrayList<>(t);
        return FXCollections.observableList(tags);
    }
}
