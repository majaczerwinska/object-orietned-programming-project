package server.api;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import server.service.CardService;
import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;


import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//This annotation loads the WebsocketConfigTest to instantiate websocket server for testing
public class CardControllerTest {
    private CardRepositoryTest repo;
    private CardListRepositoryTest cl;
    private CardController con;
    private CardService ser;
    @Autowired
    private SimpMessagingTemplate msgs;

    @BeforeEach
    public void setup() {
        repo = new CardRepositoryTest();
        cl = new CardListRepositoryTest();
        ser = new CardService(repo, cl);
        con = new CardController(ser, msgs);
    }

    @Test
    public void addCardTest(){
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(0, list.getId(),card);
        assertTrue(repo.existsById(card.getId()));

    }

    @Test
    public void deleteTest(){
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(0, list.getId(), card);
        con.deleteCard(0, list.getId(), card.getId());
        assertFalse(repo.existsById(card.getId()));

    }

    @Test
    public void editCardTest(){
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        Card card2 = new Card("title2");
        con.addCard(0, list.getId(), card);
        con.editCard(0, card.getId(), true, card2);
        assertEquals(card, card2);

    }

    @Test
    public void editCardNullTest(){
        ResponseEntity<Card> responseEntity = ResponseEntity.badRequest().build();
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        Card card2 = new Card();
        con.addCard(0, list.getId(),card);
        con.editCard(0, card.getId(), true, card2);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    public void getCardTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(0, list.getId(), card);
        ResponseEntity<Card> cardResponseEntity = ResponseEntity.ok(card);
        assertEquals(con.getCard(card.getId()), cardResponseEntity);
    }
    @Test
    public void getTasksTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        List<Task> tasks = new ArrayList<>();
        card.setTasks(tasks);
        con.addCard(0, list.getId(), card);
        assertEquals(con.getTasks(card.getId()),ResponseEntity.ok(tasks) );
    }

//    @Test
//    public void changeListForTaskTest(){
//        CardList list = new CardList("c");
//        CardList list2 = new CardList("c");
//
//        Card card = new Card("title");
//        List<Card> cards = new ArrayList<>();
//        cards.add(card);
//        list.setCards(cards);
//
//        cl.save(list);
//        cl.save(list2);
//        con.addCard(list.getId(), card);
//
//        con.changeListforCard(card.getId(), list2.getId(), card);
//
//        assertEquals(cl.getById(list2.getId()).getCards(), cards);
//    }


    @Test
    public void getTagsTest(){
        Board board = new Board("b");
        CardList list = new CardList("c");

        Card card = new Card("title");
        Set<Tag> tags = new HashSet<>();

        tags.add(new Tag("a"));
        card.setTags(tags);

        cl.save(list);
        con.addCard(board.getId(), list.getId(), card);

        ResponseEntity<Set<Tag>> cardResponseEntity = ResponseEntity.ok(tags);
        assertEquals(con.getTags(card.getId()), cardResponseEntity);
    }

//    @Test
//    public void setPositionTest(){
//        Board board = new Board("b");
//        CardList list = new CardList("c");
//        cl.save(list);
//        Card card = new Card("title");
//        ser.save(card, list.getId());
//        con.setPosition(card.getId(), 4, card);
//        assertEquals(Objects.requireNonNull(con.getCard(card.getId()).getBody()).getPosition(), 4);
//    }


//    @Test
//    public void changeListForCardTest(){
//        Board board = new Board("b");
//        CardList list = new CardList("c");
//        CardList list2 = new CardList("b");
//        cl.save(list);
//        cl.save(list2);
//        Card card = new Card("title");
//        ser.save(card, list.getId());
//        con.changeListforCard(card.getId(), list2.getId(), card);
//        assertEquals(ser.getListForCard(card), list2);
//    }
}
