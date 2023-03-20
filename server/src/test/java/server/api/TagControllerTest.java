package server.api;

import commons.Board;
import commons.Card;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.database.BoardRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.TagService;
import server.database.TagRepositoryTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagControllerTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardRepositoryTest cr;
    private TagController con;
    private TagService ser;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TagService(repo, br, cr);
        con = new TagController(ser);
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
}
