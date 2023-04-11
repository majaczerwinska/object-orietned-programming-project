package server.api;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import server.database.CardRepositoryTest;
import server.service.CardListService;
//import server.database.CardRepository;
//import server.database.QuoteRepositoryTest;
import server.database.BoardRepositoryTest;
import server.database.CardListRepositoryTest;
import server.service.CardService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//This annotation loads the WebsocketConfigTest to instantiate websocket server for testing
public class CardListControllerTest {
    @LocalServerPort
    private int port;
    private CardListRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardListController con;
    private CardListService ser;
    private CardRepositoryTest cr;
    private CardService cardService;
    @Autowired
    private SimpMessagingTemplate msgs;
    @Autowired
    private TestRestTemplate restTemplate;
    private StompSession session;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        br = new BoardRepositoryTest();
        ser = new CardListService(repo, br);
        con = new CardListController(ser, msgs);
        cr = new CardRepositoryTest();
        cardService = new CardService(cr, repo);
    }

    @Test
    public void websocketTest() throws ExecutionException, InterruptedException {
        int boardID = 0;
        String[] messages = new String[1];
        CountDownLatch latch = new CountDownLatch(1);
        Consumer<String> consumer = message -> {
            System.out.println("Message received in test");
            messages[0] = message;
            latch.countDown();
        };
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        System.out.println("Connecting to WebSocket server...");
        session = stompClient.connect("ws://localhost:" + port + "/websocketTest", new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/boards/" + boardID, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        consumer.accept((String)payload);
                    }
                });
                latch.countDown();
            }
        }).get();

        latch.await();

        con.messageClient(boardID);
        assertEquals(messages[0], "CardList added on board#" + boardID);
    }

    @Test
    public void testMessageClient() throws Exception {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        String wsUrl = "ws://localhost:" + port + "/websocket";
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        System.out.println(stompClient);
        this.session = stompClient
                .connect(wsUrl, headers, new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);
        int boardId = 123;
        String expectedMessage = "CardList added on board#" + boardId;

        con.messageClient(boardId);

        final BlockingQueue<String> messages = new LinkedBlockingQueue<>();

        session.subscribe("/topic/boards/" + boardId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messages.add((String) payload);
            }
        });

        String messageBody = messages.poll(5, TimeUnit.SECONDS);

        assertEquals(expectedMessage, messageBody);
        session.disconnect();
    }

    @Test
    public void addListTest(){
        Board board = new Board("t");
        br.save(board);
        CardList list = new CardList("title");
        con.addList(board.getId(), list);
        assertTrue(repo.existsById(list.getId()));

    }

    @Test
    public void deleteTest(){
        Board board = new Board("t");
        br.save(board);
        CardList list = new CardList("title");
        con.addList(board.getId(), list);
        con.deleteList(board.getId(), list.getId());
        assertFalse(repo.existsById(list.getId()));

    }

    @Test
    public void renameTest(){
        Board board = new Board("t");
        br.save(board);
        CardList list = new CardList("title");
        con.addList(board.getId(), list);
        con.renameList(board.getId(), list.getId(),"t");
        assertEquals(list.getName(),"t");

    }

    @Test
    public void getCardsFromListTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        Card card = new Card("title");
        int listId = list.getId();
        br.save(board);
        ser.save(list, board.getId());
        cardService.save(card, listId);
        ResponseEntity<List<Card>>  cardList = ResponseEntity.ok(List.of(card));
        assertEquals(con.getCardsFromList(listId), cardList);
    }

    @Test
    public void getCardsFromListFromNonExistingListTest(){
        CardList list = new CardList("c");
        int listId = list.getId();
        assertEquals(con.getCardsFromList(listId), ResponseEntity.badRequest().build());
    }

    @Test
    public void getCardTest(){
        CardList list = new CardList("c");
        repo.save(list);
        ResponseEntity<CardList> cardResponseEntity = ResponseEntity.ok(list);
        assertEquals(con.getCard(list.getId()), cardResponseEntity);
    }


    @Test
    public void getListSizeTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        br.save(board);
        ser.save(list, board.getId());
        ResponseEntity<Integer>  size = ResponseEntity.ok(0);
        assertEquals(con.getListSize(list.getId()), size);
    }

    @Test
    public void setListSizeTest(){
        Board board = new Board("board");
        List<CardList> list = new ArrayList<>();
        CardList cardList = new CardList("c");
        list.add(cardList);
        board.setLists(list);

        int listId = cardList.getId();
        br.save(board);
        ser.save(cardList, board.getId());
        con.setListSize(listId, 12);
        ResponseEntity<Integer>  size = ResponseEntity.ok(12);
        assertEquals(br.getById(board.getId()).getLists().get(0).lastPosition, 12);
    }

    @Test
    public void recolorListTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        List<Card> cards = new ArrayList<>();

        br.save(board);
        ser.save(list, board.getId());

        list.setbColor(121);
        list.setfColor(123);
        con.recolorList(list.getId(), list);
        assertEquals(repo.getById(list.getId()), list);
    }

//    @Test
//    public void messageClientTest() {
//        // Arrange
//        Board board = new Board("b");
//
//        con.messageClient(board.getId());
//
//    }





}
