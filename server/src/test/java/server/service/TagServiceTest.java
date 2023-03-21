package server.service;

import commons.Board;
import commons.Card;
import commons.CardList;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;
import server.database.CardRepositoryTest;
import server.database.TagRepositoryTest;
import server.service.TagService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagServiceTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardRepositoryTest cr;
    private TagService ser;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TagService(repo, br, cr);
    }


    @Test
    public void saveTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        br.save(b);
        ser.save(tag, b.getId());
        assertTrue(repo.existsById(tag.getId()));

    }

    @Test
    public void getTagsFromBoardTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        int boardId = b.getId();
        List<Tag> tagList = List.of(tag);
        br.save(b);
        ser.save(tag, boardId);
        assertEquals(ser.getTagsFromBoard(boardId), tagList);
    }

    @Test
    public void addTagToCardTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        CardList list=new CardList("rfg");
        Card card = new Card(("c"));
        br.save(b);
        cr.save(card);
        ser.save(tag, b.getId());
        ser.addTagToCard(b.getId(), card.getId(), tag.getId());
        assertTrue(repo.getById(tag.getId()).getCards().contains(card) );
        assertTrue(cr.getById(card.getId()).getTags().contains(tag));

    }
    @Test
    public void addTagToCard2Test(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        CardList list=new CardList("rfg");
        Card card = new Card(("c"));
        br.save(b);
        cr.save(card);
        ser.save(tag, b.getId());

        assertEquals(ser.addTagToCard(b.getId(), card.getId(), tag.getId()),tag );

    }
    @Test
    public void getByIdTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        br.save(b);
        ser.save(tag, b.getId());
        assertEquals(ser.getById(tag.getId()), tag);

    }

    @Test
    public void existsByIdTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        br.save(b);
        ser.save(tag, b.getId());
        assertTrue(ser.existsById(tag.getId()));

    }


    @Test
    public void deleteTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        br.save(b);
        ser.save(tag, b.getId());
        ser.delete(tag, b.getId());
        assertFalse(repo.existsById(tag.getId()));

    }

//    @Test
//    public void editTagTest(){
//        Tag tag = new Tag("title");
//        Board b = new Board("b");
//        br.save(b);
//        ser.save(tag, b.getId());
//        ser.editTag(tag,"t", "hi", 1);
//        assertEquals(tag.getTitle(), "t");
//        assertEquals(tag.getDescription(), "hi");
//        assertEquals(tag.getColor(), 1);
//    }

    @Test
    public void editTagTest(){
        Tag tag = new Tag("title");
        Board b = new Board("b");
        br.save(b);
        ser.save(tag, b.getId());
        ser.editTag(tag, new Tag("t", "hi", 1));
        assertEquals(tag.getTitle(), "t");
        assertEquals(tag.getDescription(), "hi");
        assertEquals(tag.getColor(), 1);
    }


}
