package server;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;


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

        System.out.println(ser.getById(board.getId()));
        assertEquals(ser.getById(board.getId()), board);

    }

    @Test
    public void deleteTest(){
        Board board = new Board("title");
        ser.save(board);
        ser.delete(board);
        assertFalse(repo.existsById(board.getId()));

    }

}
