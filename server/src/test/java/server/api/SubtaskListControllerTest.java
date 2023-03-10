package server.api;

import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.CardListService;
//import server.database.CardRepository;
//import server.database.QuoteRepositoryTest;
import server.database.CardListRepositoryTest;

import static org.junit.jupiter.api.Assertions.*;

public class SubtaskListControllerTest {
    private CardListRepositoryTest repo;
    private CardListController con;
    private CardListService ser;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        ser = new CardListService(repo);
        con = new CardListController(ser);
    }

    @Test
    public void addtaskTest(){
        CardList task = new CardList("title");
        con.addList(task);
        assertTrue(repo.existsById(task.getId()));

    }

    @Test
    public void deleteTest(){

        CardList task = new CardList("title");
        con.addList(task);
        con.deleteList(task.getId());
        assertFalse(repo.existsById(task.getId()));

    }

    @Test
    public void renameTest(){

        CardList task = new CardList("title");
        con.addList(task);
        con.renameList(task.getId(),"t");
        assertEquals(task.getName(),"t");

    }




}
