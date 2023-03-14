package server.service;

import commons.Board;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;
import server.database.TagRepositoryTest;
import server.service.TagService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagServiceTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private TagService ser;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        ser = new TagService(repo, br);
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


}
