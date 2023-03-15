package server.api;

import commons.Board;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepository;
import server.database.BoardRepositoryTest;
import server.service.TagService;
import server.database.TagRepositoryTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagControllerTest {
    private TagRepositoryTest repo;
    private BoardRepositoryTest br;
    private TagController con;
    private TagService ser;

    @BeforeEach
    public void setup() {
        repo = new TagRepositoryTest();
        br = new BoardRepositoryTest();
        ser = new TagService(repo, br);
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
    public void deleteTest(){

        Tag tag= new Tag("title");
        Board b = new Board("t");
        br.save(b);
        con.addTag(b.getId(),tag);
        con.deleteTag(b.getId(),tag.getId());
        assertFalse(repo.existsById(tag.getId()));

    }
}
