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
     * @return -
     */
    public Card addCard(Card card) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards") //
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
     * returns the board from the database with the given id
     * @param id - id of the board
     * @return - the board
     */
    public Board getBoard(int id){
        try{
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/boards/"+id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<>() {
                    });
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Get card by id
     * @param id the id to get cards by
     * @return the card found by id
     */
    public Card getCard(int id) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/cards" + id)
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
                    .target(SERVER).path("api/cards" + id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .put(Entity.entity(card, APPLICATION_JSON), Card.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}