package server.api;

import commons.Board;
import commons.Card;
import commons.CardList;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;
import server.database.BoardRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.BoardService;
import server.service.TagService;
import server.database.TagRepositoryTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TagControllerTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardRepositoryTest cr;
    private TagController con;
    private TagService ser;
    private BoardService boardService;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TagService(repo, br, cr);
        boardService = new BoardService(br);
        con = new TagController(ser, boardService);
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

//    @Test
//    public void editTagTest(){
//        Tag tag= new Tag("title");
//        Board b = new Board("t");
//        br.save(b);
//        con.addTag(b.getId(),tag);
//        con.editTag(tag.getId(),"t", "hi", 1);
//        assertEquals(tag.getTitle(), "t");
//        assertEquals(tag.getDescription(), "hi");
//        assertEquals(tag.getColor(), 1);
//
//    }

    @Test
    public void editTagTest(){
        Tag tag= new Tag("title");
        Board b = new Board("t");
        br.save(b);
        con.addTag(b.getId(),tag);
        con.editTag(tag.getId(), new Tag("t", "hi", 1));
        assertEquals(tag.getTitle(), "t");
        assertEquals(tag.getDescription(), "hi");
        assertEquals(tag.getColor(), 1);

    }
}
