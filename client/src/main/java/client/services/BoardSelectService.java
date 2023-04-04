package client.services;

import client.utils.ServerUtils;
import commons.Board;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

@Service
public class BoardSelectService {
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    private final String saveID = "boardSave";

    /**
     * adds a board to the list of known boards
     * @param key the board's key
     */
    public void saveBoardKey(String key) {
        if (key == null || key.length() < 1) {
            System.out.println("Invalid key saved");
            return;
        }
        String keys = prefs.get(saveID, "public");
        if (keys.contains(key)) {
            System.out.println(key + "this key is already saved");
            return;
        }
        keys += "," + key;
        prefs.put(saveID, keys);
    }

    /**
     * Get the list of saved board keys from preferences
     * @return a list of strings
     */
    public List<String> getBoardKeys() {
        String keys = prefs.get(saveID, "public");
        List<String> keyList = new ArrayList<>(List.of(keys.split(",")));
        System.out.println(keyList);
        return keyList;
    }

    /**
     * Get board instances from database using saved board keys
     * @param server the serverUtils instance
     * @return the list of board elements that exist in the database
     */
    public List<Board> getBoards(ServerUtils server) {
        List<Board> b = new ArrayList<>();
        List<String> keys = getBoardKeys();
        System.out.println("Getting board keys from save, results = " + keys);
        for (String key : keys) {
            System.out.println("Getting board instance from server for key=" + key);
            Board res = server.getBoardByKey(key);
            if (res != null) {
                b.add(res);
            } else {
                removeBoardKey(key);
            }
        }
        return b;
    }

    /**
     * Remove a board key from the save
     * @param key key to remove
     */
    public void removeBoardKey(String key) {
        if (key == null || key.length() < 1) {
            System.out.println("Invalid key removal");
            return;
        }
        String keys = prefs.get(saveID, "public");
        if (!keys.contains(",")) {
            prefs.put(saveID, "public");
            return;
        }
        System.out.println("removing key. before: " + keys);
        keys = keys.replace(key + ",", "");
        keys = keys.replace("," + key, "");
        System.out.println("after: " + keys);
        prefs.put(saveID, keys);
    }
}
