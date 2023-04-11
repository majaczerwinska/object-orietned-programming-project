package server.api;

import commons.Board;
import commons.Card;
//import commons.CardList;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.BoardRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.BoardService;
import server.service.TagService;
import server.database.TagRepositoryTest;

//import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//This annotation loads the WebsocketConfigTest to instantiate websocket server for testing
public class TagControllerTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardRepositoryTest cr;
    private TagController con;
    private TagService ser;
    private BoardService boardService;
    @Autowired
    private SimpMessagingTemplate msgs;


    @BeforeEach
    public void setup() {

        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TagService(repo, br, cr);
        boardService = new BoardService(br);
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

    @Test
    public void removeTagFromCardTest(){
        Tag tag= new Tag("title");
        Board b = new Board("t");
        Card c = new Card("t");
        cr.save(c);
        br.save(b);
        con.addTag(b.getId(),tag);
        con.addTagToCard(b.getId(), c.getId(), tag.getId());
        assertEquals(con.removeTagFromCard(c.getId(), tag.getId()),ResponseEntity.ok().build());

    }
}
