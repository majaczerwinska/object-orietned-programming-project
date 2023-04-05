package client.services;

import client.utils.ServerUtils;
import commons.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CardService {

    /**
     * Gets the list of tags and puts it in an observable list
     * @param server the server utils instance
     * @param boardID the id of the board
     * @param cardID the id of the card you are viewing
     * @return an observable list of tags
     */
    public ObservableList<Tag> getTagListOfBoard(ServerUtils server, int boardID, int cardID){
        List<Tag> tags = server.getTagsFromBoard(boardID);
        List<Tag> t = new ArrayList<>();
        Set<Tag> tagsofcard = server.getTagsForCard(cardID);
        for(Tag tag : tags){
            if(tagsofcard!=null && !tagsofcard.contains(tag)){
                t.add(tag);
            }
        }
        ObservableList<Tag> tagList = FXCollections.observableArrayList(t);
        return tagList;
    }

    /**
     * Gets the list of tags and puts it in an observable list
     * @param server the server utils instance
     * @param cardID the id of the board
     * @return an observable list of tags
     */
    public ObservableList<Tag> getTagListOfCard(ServerUtils server, int cardID){
        Set<Tag> tags = server.getTagsForCard(cardID);
        List<Tag> t = new ArrayList<>();
        for(Tag tag : tags){
            t.add(tag);
        }
        ObservableList<Tag> tagList = FXCollections.observableArrayList(t);
        return tagList;
    }
}
