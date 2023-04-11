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
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import server.service.CardService;
import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;


import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

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
        ResponseEntity r = ResponseEntity.badRequest().build();
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(0, list.getId(), card);

        assertEquals(con.deleteCard(0, list.getId(), 50),r);

    }

    @Test
    public void delete2Test(){
        ResponseEntity r = ResponseEntity.badRequest().build();
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(0, list.getId(), card);

        assertEquals(con.deleteCard(0, list.getId(), card.getId()),ResponseEntity.ok().build());

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

    @Test
    public void changeListForTaskTest(){
        CardList list = new CardList("c");
        CardList list2 = new CardList("c");

        Card card = new Card("title");
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        list.setCards(cards);

        cl.save(list);
        cl.save(list2);
        con.addCard(1,list.getId(), card);

        con.changeListforCard(card.getId(), list2.getId(), card);

        assertEquals(cl.getById(list2.getId()).getCards(), cards);
    }


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



    @Test
    public void setPositionTest(){
        Board board = new Board("b");
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        Card card1 = new Card("title");
        ser.save(card, list.getId());
        ser.save(card1, list.getId());

        assertEquals(con.setPosition(card.getId(), 1, card), ResponseEntity.ok().build());

    }

    @Test
    public void setPositionBadTest(){
        Board board = new Board("b");
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        Card card1 = new Card("title");
        ser.save(card, list.getId());
        ser.save(card1, list.getId());

        assertEquals(con.setPosition(80, 1, card), ResponseEntity.badRequest().build());

    }


    @Test
    public void changeListForCardTest(){
        Board board = new Board("b");
        CardList list = new CardList("c");
        CardList list2 = new CardList("b");
        cl.save(list);
        cl.save(list2);
        Card card = new Card("title");
        ser.save(card, list.getId());
        con.changeListforCard(card.getId(), list2.getId(), card);
        assertEquals(con.changeListforCard(card.getId(), list2.getId(), card),ResponseEntity.ok(card));
    }

    @Test
    public void changeListForCard2Test(){
        Board board = new Board("b");
        CardList list = new CardList("c");
        CardList list2 = new CardList("b");
        cl.save(list);
        cl.save(list2);
        Card card = new Card("title");
        Card c = card;
        ser.save(card, list.getId());
        ser.save(c, list2.getId());
        con.changeListforCard(card.getId(), list2.getId(), card);
        assertEquals(con.changeListforCard(card.getId(), list2.getId(), card),ResponseEntity.ok(c));
    }

    @Test
    public void changeListForCardBadTest(){
        Board board = new Board("b");
        CardList list = new CardList("c");
        CardList list2 = new CardList("b");
        cl.save(list);
        cl.save(list2);
        Card card = new Card("title");
        ser.save(card, list.getId());
        con.changeListforCard(card.getId(), list2.getId(), card);
        assertEquals(con.changeListforCard(70, list2.getId(), card),ResponseEntity.badRequest().build());
    }

    @Test
    public void registerCardDeletedTest(){
        Board board = new Board("b");
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        ResponseEntity<Object> noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<Card>> res =
                new DeferredResult<>(5000L, noContent);

        assertEquals(con.registerCardDeleted(card.getId()).getResult(), res.getResult());
    }

    @Test
    public void registerCardDeleted2Test(){
         Map<Object, Consumer<Card>> listeners = new HashMap<>();
        Board board = new Board("b");
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        ResponseEntity<Object> noContent = ResponseEntity.ok().build();
        DeferredResult<ResponseEntity<Card>> res =
                new DeferredResult<>(5000L, noContent);

        assertEquals(con.registerCardDeleted(card.getId()).getResult(), res.getResult());
    }

    @Test
    public void registerCardDeleted3Test(){
        Map<Object, Consumer<Card>> listeners = new HashMap<>();
        Board board = new Board("b");
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        ResponseEntity<Object> noContent = ResponseEntity.badRequest().build();
        DeferredResult<ResponseEntity<Card>> res =
                new DeferredResult<>(5000L, noContent);

        assertEquals(con.registerCardDeleted(70).getResult(), res.getResult());
    }
}
