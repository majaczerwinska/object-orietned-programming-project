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
import java.util.Map;
import java.util.Set;

import commons.*;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
//import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import org.springframework.web.client.HttpClientErrorException;
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


    public String checkPassword(String pwd) {
        try {
            var response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("admin")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .post(Entity.entity(pwd, APPLICATION_JSON), Response.class);
            if (response.getStatus() == 200) {
                System.out.println("Successful authentication");
                return response.readEntity(String.class);
            }
        } catch (WebApplicationException u) {
            if (u.getResponse().getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                System.out.println("User unauthorised: "+u.getMessage());
            } else if (u.getResponse().getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
                System.out.println("Bad Request at server password endpoint");
            } else {
                System.out.println("Unknown error: "+u.getMessage());
                u.printStackTrace();
            }
            return "error";
        }
        return "error";
    }

    /**
     * @param card card o be added
     * @param listID the list id
     * @param boardId the board the list is in
     * @return -
     */
    public Card addCard(Card card, int boardId, int listID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + boardId + "/" + listID) //
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
     * @param boardId the board the card is in
     * @param id the id of the card to edit
     * @param card the new card the old card is replaced by
     * @param ignore indicates whether websockets should ignore this update
     * @return return the card edited
     */
    public Card editCard(int boardId, int id, Card card, boolean ignore) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/cards/edit/" + boardId + "/" + id + "/" + ignore)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(card, APPLICATION_JSON), Card.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Sets the position
     * @param card the card
     * @param position the position
     * @return the card
     */
    public Card setPosition(Card card, int position) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/cards/position/" +card.getId()+ "/" + position)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .post(Entity.entity(card, APPLICATION_JSON), Card.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Edit list colour
     * @param id the id of the list to edit
     * @param list with the new color
     * @return return the list edited
     */
    public CardList editCardListColour(int id, CardList list) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/lists/color/"+id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(list, APPLICATION_JSON), CardList.class);
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
                    .target(SERVER).path("api/cards/move/" + card.getId()+"/"+listid)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .post(Entity.entity(card, APPLICATION_JSON), Card.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Bad request in changeListOfCard server utils");
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
    public List<Tag> getTagsFromBoard(int boardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tags/" + boardId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Tag>>() {});
    }

    /**
     * Gets a list of all tags for a card
     * @param cardId the card id
     * @return the list with tags
     */
    public Set<Tag> getTagsForCard(int cardId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + cardId + "/tags") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Set<Tag>>() {});
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
     * remove tag from card
     * @param tagid tag's id
     * @param cardid card's id
     * @return the tag element
     */
    public Tag removeTagFromCard( int tagid, int cardid) {
        return ClientBuilder.newClient(new ClientConfig()) // /{boardId}/{cardId}/{tagId}
                .target(SERVER).path("api/tags/" + cardid + "/"+tagid) //
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
     * @param boardId the board the card is in
     * @param id the id of the tag to be edited
     * @param newTag the tag with de new attributes
     * @return the edited tag
     */
    public Tag editTag(int boardId, int id, Tag newTag){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/tags/edit/" + boardId + "/" + id) //
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
        System.out.println("Sending PUT request to " + "api/lists/" + listId + "/size/"+ newSize);
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
     * @param boardId the board the list is in
     * @param listId - id of the list to be renamed
     * @param newName - new name
     * @return - edited list
     */
    public CardList editList(int boardId, int listId, String newName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/lists/" + boardId + "/" + listId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(newName, APPLICATION_JSON), CardList.class);
    }
    /**
     * send a delete request for a card
     * @param boardId the board the card is in
     * @param c the card instance
     * @param listID the list the card is in
     * @return the deleted card response
     */
    public Card deleteCard(Card c, int boardId, int listID) {
        System.out.println("Sending DELETE request to api/cards/" +boardId+ "/" +listID+"/"+c.getId()+"" +
                "\nCard for card element "+c);
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + boardId + "/" + listID + "/" + c.getId()) //
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

    /**
     *
     * @param boardId
     * @param palette
     * @return - added palette ?
     */
    public Palette addPaletteToBoard(int boardId, Palette palette){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/palettes/" + boardId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(palette, APPLICATION_JSON), Palette.class);

    }

    /**
     *
     * @param boardId
     * @return - added palette?
     */
    public List<Palette> getPalettesFromBoard(int boardId){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards/palettes/" + boardId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Palette>>() {});
    }


    public Board deleteBoard(int id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(Board.class);
    }


    public List<Map<String, Object>> executeSQLQuery(String query, String token) {
        System.out.println("Sending query="+query);
        System.out.println("With token="+token);
        try {
            var response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("query")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .header("Authorization", "Bearer "+token)
                    .post(Entity.entity(query, APPLICATION_JSON), Response.class);
            if (response.getStatus() == 200) {
                var res = response.readEntity(List.class);
                System.out.println(res);
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}