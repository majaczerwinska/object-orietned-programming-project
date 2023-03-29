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
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
import java.util.List;
import java.util.Set;

import commons.Board;
import commons.Card;
import commons.CardList;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import commons.Task;
import jakarta.ws.rs.core.GenericType;
import commons.Tag;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

//import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.List;
//import jakarta.ws.rs.core.GenericType;
//import jakarta.ws.rs.core.GenericType;

public class ServerUtils {

    public static String SERVER = "http://localhost:8080/";

//    /**
//     *
//     * @throws IOException -
//     */
//    public void getQuotesTheHardWay() throws IOException {
//        var url = new URL("http://localhost:8080/api/quotes");
//        var is = url.openConnection().getInputStream();
//        var br = new BufferedReader(new InputStreamReader(is));
//        String line;
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }
//    }

//    /**
//     *
//     * @return -
//     */
//    public List<Quote> getQuotes() {
//        return ClientBuilder.newClient(new ClientConfig()) //
//                .target(SERVER).path("api/quotes") //
//                .request(APPLICATION_JSON) //
//                .accept(APPLICATION_JSON) //
//                .get(new GenericType<List<Quote>>() {});
//    }

//    /**
//     *
//     * @param quote -
//     * @return -
//     */
//    public Quote addQuote(Quote quote) {
//        return ClientBuilder.newClient(new ClientConfig()) //
//                .target(SERVER).path("api/quotes") //
//                .request(APPLICATION_JSON) //j
//                .accept(APPLICATION_JSON) //
//                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
//    }

    /**
     * @param card -
     * @param listID the list id
     * @return -
     */
    public Card addCard(Card card, int listID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + listID) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(card, APPLICATION_JSON), Card.class);
    }

//
//    public Board addBoard(Board board) {
//        return ClientBuilder.newClient(new ClientConfig())
//                .target(SERVER).path("api/boards/")
//                .request(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .post(Entity.entity(board, APPLICATION_JSON), Board.class);
//    }

    /**
     * returns all boards from the database
     * @return - list of boards
     */
    public List<Board> getBoards() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/boards/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }


    /**
     * Returns a board from the database, searched by key
     * @param key boardkey
     * @return the board element, if not found returns null
     */
    public Board getBoardByKey(String key) {
        try{
            System.out.println("sending a request to api/boards/key/"+key);
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/boards/key/"+key)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<Board>() {});
        }catch(Exception e){
            System.out.println("Exception raised in getBoardByKey() in server utils for key "+key);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * returns the board from the database with the given id
     * @param id - id of the board
     * @return - the board
     */
    public Board getBoard(int id){
        System.out.println(id);
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/boards/"+id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<Board>() {
                    });

    }

    /**
     * Get card by id
     * @param id the id to get cards by
     * @return the card found by id
     */
    public Card getCard(int id) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/cards/" + id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<Card>() {
                    });
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Edit card with id to the new card
     * @param id the id of the card to edit
     * @param card the new card the old card is replaced by
     * @return return the card edited
     */
    public Card editCard(int id, Card card) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/cards/" + id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(card, APPLICATION_JSON), Card.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Changes to which list the card belongs
     * @param listid the new list
     * @param card the card
     * @return the card
     */
    public Card changeListOfCard(int listid, Card card) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/cards/" + card.getId()+"/"+listid)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(card, APPLICATION_JSON), Card.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Edit task with id
     * @param id the id of the task to edit
     * @param task the new task the old task is replaced by
     * @return return the task edited
     */
    public Task editTask(int id, Task task) {
        try{
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/tasks/" + id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(task, APPLICATION_JSON), Task.class);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Gets a list of all tags from a board
     * @param boardId the id from the board we need the tags from
     * @return the list with tags from the board
     */
    public Set<Tag> getTagsFromBoard(int boardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tags/" + boardId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Set<Tag>>() {});
    }

    /**
     * Gets a list of all tags for a card
     * @param cardId the card id
     * @return the list with tags
     */
    public List<Tag> getTagsForCard(int cardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + cardId + "/tags") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Tag>>() {});
    }

    /**
     * add tag to card
     * @param boardid board's id
     * @param tagid tag's id
     * @param cardid card's id
     * @return the tag element
     */
    public Tag addTagToCard(int boardid, int tagid, int cardid) {
        return ClientBuilder.newClient(new ClientConfig()) // /{boardId}/{cardId}/{tagId}
                .target(SERVER).path("api/tags/" + boardid + "/" + cardid + "/"+tagid) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity("", APPLICATION_JSON), Tag.class);
    }

    /**
     * Gets a list of all tasks from a card
     * @param cardId the id from the card we need the tasks from
     * @return the list with tasks from the card
     */
    public List<Task> getTasksFromCard(int cardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + cardId + "/tasks") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Task>>() {});
    }

    /**
     * Adds a tag to the database
     * @param tag the tag to be added
     * @param boardId the id from the board where the tag needs to be added
     * @return the added tag
     */
    public Tag addTag(Tag tag, int boardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tags/" + boardId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(tag, APPLICATION_JSON), Tag.class);
    }

    /**
     * Adds a task to the database
     * @param task the task to be added
     * @param cardId the id from the card where the task needs to be added
     * @return the added task
     */
    public Task addTask(Task task, int cardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tasks/" + cardId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(task, APPLICATION_JSON), Task.class);
    }

    /**
     * Deletes a tag (calls the delete method in the controller)
     * @param tag the tag to be deleted
     * @param boardId the boardId from where the tag is coming from
     * @return the deleted tag
     */
    public Tag deleteTag(Tag tag, int boardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tags/" + boardId + "/" + tag.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Tag.class);
    }

    /**
     * Deletes a task (calls the delete method in the controller)
     * @param taskId the id of the task to be deleted
     * @param cardId the cardId from where the tag is coming from
     * @return the deleted task
     */
    public Task deleteTask(int taskId, int cardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tasks/" + cardId + "/" + taskId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Task.class);
    }


    /**
     * Edits a tag in the database (calls the edit method from the controller)
     * @param oldTag the old tag
     * @param newTag the tag with de new attributes
     * @return the edited tag
     */
    public Tag editTag(Tag oldTag, Tag newTag){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tags/" + oldTag.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(newTag, APPLICATION_JSON), Tag.class);
    }

    /**
     * set a lists size
     * @param listId the list id
     * @param newSize the new size
     * @return int size
     */
    public Integer setListSize(int listId, int newSize) {
        // /{listId}/size/{size}
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists/" + listId + "/size/"+ newSize) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(newSize, APPLICATION_JSON), Integer.class);
    }
    /**
     * creates new list and assigns it to a board
     * @param boardId - board to add the list to
     * @param list - a new list
     * @return - list
     */
    public CardList createList(int boardId, CardList list) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/lists/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(list, APPLICATION_JSON), CardList.class);
    }

    /**
     * Gets a list of all cards from a list
     * @param listId the id from the list we need the cards from
     * @return the list with cards from the list
     */
    public List<Card> getCardsFromList(int listId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists/" + listId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Card>>() {});
    }

    /**
     * get the size of a list
     * @param listID the lists id
     * @return int size
     */
    public Integer getListSize(int listID) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/lists/"+listID+"/size")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Integer>() {});
    }

    /**
     * Gets a list of all cardlists from a board
     * @param boardId the id from the board we need the lists from
     * @return the list with cardlists from the board
     */
    public List<CardList> getCardListsFromBoard(int boardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards/lists/" + boardId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<CardList>>() {});
    }

    /**
     * send a request to an ip and await a response, making sure that
     * a connection can be established with a talio server
     * @param ip address to test connection to
     * @return status code (-1: timeout, -2: response received, but address is not a talio server)
     */
    public int testConnection(String ip) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(ip)
                    .request()
                    .get();
            String body = response.readEntity(String.class);
            System.out.println("Response: " + body);
            response.close();
            if (response.getStatus() == 200) {
                if (body.contains("talio")) {
                    return response.getStatus();
                } else {
                    return -2;
                }
            } else {
                return response.getStatus();
            }
        } catch (ProcessingException e) {
            System.out.println("Connection timed out: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * adds a board to the database
     * @param board board that will be added to the database
     * @return board to be added
     */
    public Board addBoard(Board board) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(board, APPLICATION_JSON), Board.class);
    }

    /**
     * renames your list
     * @param listId - id of the list to be renamed
     * @param newName - new name
     * @return - edited list
     */
    public CardList editList(int listId, String newName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/lists/" + listId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(newName, APPLICATION_JSON), CardList.class);
    }
    /**
     * send a delete request for a card
     * @param c the card instance
     * @param listID the list the card is in
     * @return the deleted card response
     */
    public Card deleteCard(Card c, int listID) {
        System.out.println("Sending DELETE request to api/cards/"+listID+"/"+c.getId()+"\nCard for card element "+c);
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + listID + "/" + c.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Card.class);
    }
    /**
     * send a delete request for a cardlist
     * @param listID the cardlist instance
     * @param boardID the board the cardlist is in
     * @return the deleted cardlist response
     */
    public CardList deleteCardList(int listID, int boardID) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists/" + boardID + "/" + listID) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(CardList.class);
    }

    /**
     * Edit board with id to the new board
     * @param id the id of the board to edit
     * @param board the new board the old card is replaced by
     * @return return the board edited
     */
    public Board editBoard(int id, Board board) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/boards/" + id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(board, APPLICATION_JSON), Board.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
    * get a tag by its id
     * @param tagID its id
     * @return the tag element
     */
    public Tag getTag(int tagID){
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/tags/" + tagID)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<Tag>() {
                    });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get a cardlist by its id
     * @param listID its id
     * @return the cardlist element
     */
    public CardList getCardList(int listID){

            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/lists/" + listID)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<CardList>() {
                    });

    }
}