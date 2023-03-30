package server.api;

import commons.Board;
import commons.Card;
//import commons.CardList;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
import server.WebsocketConfig;
import server.database.BoardRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.BoardService;
import server.service.TagService;
import server.database.TagRepositoryTest;

//import java.lang.reflect.Type;
//import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class TagControllerTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardRepositoryTest cr;
    private TagController con;
    private TagService ser;
    private BoardService boardService;
    private SimpMessagingTemplate msgs;
    private WebsocketConfig websocketConfig;

    @BeforeEach
    public void setup() throws ExecutionException, InterruptedException {
//        StompSession session;
//            StandardWebSocketClient client = new StandardWebSocketClient();
//            WebSocketStompClient stompClient = new WebSocketStompClient(client);
//            stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//            System.out.println("Connecting to WebSocket server...");
//            session = stompClient.connect("ws://localhost:8080/websocket", new StompSessionHandlerAdapter() {
//                @Override
//                public Type getPayloadType(StompHeaders headers) {
//                    return null;
//                }
//
//                @Override
//                public void handleFrame(StompHeaders headers, Object payload) {
//                }
//            }).get();
//            session.subscribe("/topic/tags/0",null);

        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TagService(repo, br, cr);
        boardService = new BoardService(br);
        msgs = new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return false;
            }
        });
        con = new TagController(ser, boardService, msgs);
    }

    @Test
    public void getTagsFromBoardTest(){
        Tag tag = new Tag("title");
        Board b = new Board("t");
        int boardId = b.getId();
        ResponseEntity<List<Tag>> tagList = ResponseEntity.ok(List.of(tag));
        br.save(b);
        con.addTag(boardId, tag);
        assertEquals(con.getTagsFromBoard(boardId), tagList);
    }

    @Test
    public void getTagsFromBoardTestFromNonExistingBoard(){
        Board b = new Board("t");
        int boardId = b.getId();
        assertEquals(con.getTagsFromBoard(boardId), ResponseEntity.badRequest().build());
    }

    @Test
    public void addTagTest(){
        Tag tag= new Tag("title");
        Board b = new Board("t");
        br.save(b);
        con.addTag(b.getId(),tag);
        assertTrue(repo.existsById(tag.getId()));

    }
    @Test
    public void addTagToCardTest(){
        Tag tag= new Tag("title");
        Board b = new Board("t");
        Card c = new Card("ddjhevfb");
        br.save(b);
        cr.save(c);
        con.addTag(b.getId(),tag);
        con.addTagToCard(b.getId(),c.getId(), tag.getId());
        assertTrue(ser.getById(tag.getId()).getCards().contains(c));

    }

    @Test
    public void deleteTest(){

        Tag tag= new Tag("title");
        Board b = new Board("t");
        br.save(b);
        con.addTag(b.getId(),tag);
        con.deleteTag(b.getId(),tag.getId());
        assertFalse(repo.existsById(tag.getId()));

    }


    @Test
    public void editTagTest(){
        Tag tag= new Tag("title");
        Board b = new Board("t");
        br.save(b);
        con.addTag(b.getId(),tag);
        con.editTag(b.getId(), tag.getId(), new Tag("t", 1));
        assertEquals(tag.getTitle(), "t");
        assertEquals(tag.getColor(), 1);

    }

    @Test
    public void removeTagFromCard2Test(){
        Tag tag= new Tag("title");
        Board b = new Board("t");
        Card c = new Card("t");
        cr.save(c);
        br.save(b);
        con.addTag(b.getId(),tag);
        con.addTagToCard(b.getId(), c.getId(), tag.getId());
        assertEquals(con.removeTagFromCard(c.getId(), 500),ResponseEntity.badRequest().build());

    }
}
