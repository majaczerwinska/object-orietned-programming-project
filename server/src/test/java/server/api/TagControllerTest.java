package server.api;

import commons.Card;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.CardService;
import server.TagService;
import server.database.CardRepositoryTest;
import server.database.TagRepositoryTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagControllerTest {
    private TagRepositoryTest repo;
    private TagController con;
    private TagService ser;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        ser = new TagService(repo);
        con = new TagController(ser);
    }

    @Test
    public void addTagTest(){
        Tag tag= new Tag("title");
        con.addTag(tag);
        assertTrue(repo.existsById(tag.getId()));

    }

    @Test
    public void deleteTest(){

        Tag tag= new Tag("title");
        con.addTag(tag);
        con.deleteTag(tag.getId());
        assertFalse(repo.existsById(tag.getId()));

    }
}
