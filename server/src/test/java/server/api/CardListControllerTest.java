package server.api;

import commons.Board;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.service.CardListService;
//import server.database.CardRepository;
//import server.database.QuoteRepositoryTest;
import server.database.BoardRepositoryTest;
import server.database.CardListRepositoryTest;

import static org.junit.jupiter.api.Assertions.*;

public class CardListControllerTest {
    private CardListRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardListController con;
    private CardListService ser;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        br = new BoardRepositoryTest();
        ser = new CardListService(repo, br);
        con = new CardListController(ser);
    }

    @Test
    public void addListTest(){
        Board board = new Board("t");
        br.save(board);
        CardList list = new CardList("title");
        con.addList(board.getId(), list);
        assertTrue(repo.existsById(list.getId()));

    }

    @Test
    public void deleteTest(){
        Board board = new Board("t");
        br.save(board);
        CardList list = new CardList("title");
        con.addList(board.getId(), list);
        con.deleteList(board.getId(), list.getId());
        assertFalse(repo.existsById(list.getId()));

    }

    @Test
    public void renameTest(){
        Board board = new Board("t");
        br.save(board);
        CardList list = new CardList("title");
        con.addList(board.getId(), list);
        con.renameList(list.getId(),"t");
        assertEquals(list.getName(),"t");

    }




}
