package server.service;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;
import server.service.BoardService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        System.out.println(repo.findById(board.getId()));
        System.out.println(board.getId());
        assertTrue(repo.existsById(board.getId()));
    }

    @Test
    public void getByIdTest(){
        Board board = new Board("title--");
        System.out.println("saving board: \n"+ser.save(board));
        System.out.println("board id : " + board.id);
        System.out.println("getter output: "+ser.getById(board.id));
        assertEquals(board, ser.getById(board.id));
    }

    @Test
    public void existsByIdTest(){
        Board board = new Board("title");
        ser.save(board);
        assertTrue(ser.existsById(board.getId()));

    }

    @Test
    public void existsByIdTestFalse(){
        Board board = new Board("title");
        assertFalse(ser.existsById(board.getId()));
        assertFalse(ser.existsById(0));

    }

    @Test
    public void deleteTest(){
        Board board = new Board("title");
        ser.save(board);
        ser.delete(board);
        assertFalse(repo.existsById(board.getId()));

    }

    @Test
    void findById() {
        Board b = new Board("a");
        ser.save(b);

        assertEquals(ser.findById(0), Optional.of(b));
    }

    @Test
    void findAll() {
        Board b = new Board("a");
        ser.save(b);
        List<Board> bl = new ArrayList<>();
        bl.add(b);
        assertEquals(ser.findAll(), bl);

    }
}
