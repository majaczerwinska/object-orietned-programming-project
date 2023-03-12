package server.service;

import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TagRepositoryTest;
import server.service.TagService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagServiceTest {
    private TagRepositoryTest repo;
    private TagService ser;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        ser = new TagService(repo);
    }


    @Test
    public void saveTest(){
        Tag tag = new Tag("title");
        ser.save(tag);
        assertTrue(repo.existsById(tag.getId()));

    }

    @Test
    public void getByIdTest(){
        Tag tag = new Tag("title");
        ser.save(tag);
        assertEquals(ser.getById(tag.getId()), tag);

    }

    @Test
    public void existsByIdTest(){
        Tag tag = new Tag("title");
        ser.save(tag);
        assertTrue(ser.existsById(tag.getId()));

    }


    @Test
    public void deleteTest(){
        Tag tag = new Tag("title");
        ser.save(tag);
        ser.delete(tag);
        assertFalse(repo.existsById(tag.getId()));

    }


}
