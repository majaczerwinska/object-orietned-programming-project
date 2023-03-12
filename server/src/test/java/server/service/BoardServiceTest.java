package server.service;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;
import server.service.BoardService;


import static org.junit.jupiter.api.Assertions.*;

public class BoardServiceTest {
    private BoardRepositoryTest repo;
    private BoardService ser;

    @BeforeEach
    public void setup() {
        repo = new BoardRepositoryTest();
        ser = new BoardService(repo);
    }


    @Test
    public void saveTest(){
        Board board = new Board("title");
        ser.save(board);
        assertTrue(repo.existsById(board.getId()));

    }

    @Test
    public void getByIdTest(){
        Board board = new Board("title");
        ser.save(board);
        assertEquals(ser.getById(board.getId()), board);

    }

    @Test
    public void existsByIdTest(){
        Board board = new Board("title");
        ser.save(board);
        assertTrue(ser.existsById(board.getId()));

    }

    @Test
    public void deleteTest(){
        Board board = new Board("title");
        ser.save(board);
        ser.delete(board);
        assertFalse(repo.existsById(board.getId()));

    }

}
