package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.service.BoardService;
import server.database.BoardRepositoryTest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardControllerTest {
    private BoardRepositoryTest repo;
    private BoardController con;
    private BoardService ser;

    @BeforeEach
    public void setup() {
        repo = new BoardRepositoryTest();
        ser = new BoardService(repo);
        con = new BoardController(ser);
    }

    @Test
    public void addCardTest(){
        Board board = new Board("title");
        con.addBoard(board);
        assertTrue(repo.existsById(board.getId()));

    }

    @Test
    public void deleteTest(){

        Board board = new Board("title");
        con.addBoard(board);
        con.deleteBoard(board.getId());
        assertFalse(repo.existsById(board.getId()));

    }


}
