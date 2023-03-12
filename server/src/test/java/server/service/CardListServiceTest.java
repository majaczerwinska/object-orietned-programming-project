package server.service;

import commons.Board;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;
import server.database.CardListRepositoryTest;
import server.service.CardListService;

import static org.junit.jupiter.api.Assertions.*;


public class CardListServiceTest {
    private CardListRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardListService ser;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        br  = new BoardRepositoryTest();
        ser = new CardListService(repo, br);
    }


    @Test
    public void saveTest(){
        Board board = new Board("board");
        br.save(board);
        CardList list = new CardList("title");
        ser.save(list, board.getId());
        assertTrue(repo.existsById(list.getId()));
        assertTrue(br.getById(board.getId()).getLists().contains(list));
    }

    @Test
    public void getByIdTest(){
        Board board = new Board("board");
        br.save(board);
        CardList list = new CardList("title");
        ser.save(list, board.getId());
        assertEquals(ser.getById(list.getId()), list);

    }

    @Test
    public void existsByIdTest(){
        Board board = new Board("board");
        br.save(board);
        CardList list = new CardList("title");
        ser.save(list, board.getId());

        assertTrue(ser.existsById(list.getId()));

    }

    @Test
    public void deleteTest(){
        Board board = new Board("board");
        br.save(board);
        CardList list = new CardList("title");
        ser.save(list, board.getId());
        ser.delete(list, board.getId());
        assertFalse(repo.existsById(list.getId()));
        assertFalse(br.getById(list.getId()).getLists().contains(list));

    }

    @Test
    public void updateTaskListTest(){
        Board board = new Board("board");
        br.save(board);
        CardList list = new CardList("title");
        ser.save(list, board.getId());
        ser.updateTaskListName(list,"t");
        assertEquals(list.getName(), "t");

    }


}
