package server.api;

import commons.Board;
import commons.Card;
import commons.CardList;
import commons.Palette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import server.database.CardListRepositoryTest;
import server.service.BoardService;
import server.database.BoardRepositoryTest;
import server.service.CardListService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardControllerTest {
    private BoardRepositoryTest repo;
    private BoardController con;
    private BoardService ser;
    private CardListRepositoryTest cl;
    private CardListService cardListService;
    @Autowired
    private SimpMessagingTemplate msgs;

    @BeforeEach
    public void setup() {
        repo = new BoardRepositoryTest();
        ser = new BoardService(repo);
        con = new BoardController(ser, msgs);
        cl = new CardListRepositoryTest();
        cardListService = new CardListService(cl, repo);
    }

    @Test
    public void addBoardTest(){
        Board board = new Board("title");
        con.addBoard(board);
        assertTrue(repo.existsById(board.getId()));

    }

    @Test
    public void deleteBoardTest(){
        Board board = new Board("title");
        con.addBoard(board);
        con.deleteBoard(board.getId());
        assertFalse(repo.existsById(board.getId()));
    }

    @Test
    public void deleteEmptyBoardTest(){
        Board board = new Board("title");
        assertEquals(con.deleteBoard(board.getId()), ResponseEntity.badRequest().build());
    }

    @Test
    public void getCardsFromListTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        int listId = list.getId();
        repo.save(board);
        cardListService.save(list, board.getId());
        ResponseEntity<List<CardList>> cardLists = ResponseEntity.ok(List.of(list));
        assertEquals(con.getCardListsFromBoard(listId), cardLists);
    }

    @Test
    public void getCardsFromListFromNonExistingListTest(){
        Board board = new Board("board");
        assertEquals(con.getCardListsFromBoard(board.getId()), ResponseEntity.badRequest().build());
    }

    @Test
    public void editBoardTest(){
        Board b = new Board("title");
        Board b2 = new Board("title2");
        con.addBoard(b);
        con.editBoard(b.getId(), b2);
        ResponseEntity<Board> board = ResponseEntity.ok(b2);
        assertEquals(con.getBoard(b.getId()), board);
    }

    @Test
    public void editEmptyBoardTest(){
        Board b = new Board("title");
        Board b2 = new Board("title2");

        assertEquals(con.editBoard(b2.getId(), b), ResponseEntity.badRequest().build());
    }

    @Test
    public void getBoardTest(){
        Board board = new Board("title");
        con.addBoard(board);
        ResponseEntity<Board> boardResponseEntity = ResponseEntity.ok(board);
        assertEquals(con.getBoard(board.getId()), boardResponseEntity);
    }

    @Test
    public void getBoardByKeyTest(){
        Board board = new Board("title");
        board.setBoardkey("abc");
        con.addBoard(board);
        ResponseEntity<Board> boardResponseEntity = ResponseEntity.ok(board);
        assertEquals(con.getBoardByKey(board.getBoardkey()), board);
    }

    @Test
    public void getBoardsTest(){
        Board b1 = new Board("title");
        Board b2 = new Board("title2");
        List<Board> boardList = new ArrayList<>();

        boardList.add(b2);
        boardList.add(b1);
        repo.save(b1);
        repo.save(b2);
        //ResponseEntity<List<Board>> boardResponseEntity = ResponseEntity.ok(boardList);
        assertEquals(con.getBoards(), boardList);
    }

    @Test
    public void getPalettesFromBoardTest(){
        Board board = new Board("board");
        List<Palette> list = new ArrayList<>();
        list.add(new Palette("p", 1, 2));
        board.setPalettes(list);
        repo.save(board);
        ResponseEntity<List<Palette>> cardLists = ResponseEntity.ok(list);
        assertEquals(con.getPalettesFromBoard(board.getId()), cardLists);
    }

    @Test
    public void getPalettesFromEmptyBoardTest(){
        Board board = new Board("board");
        List<Palette> list = new ArrayList<>();
        list.add(new Palette("p", 1, 2));
        board.setPalettes(list);
        assertEquals(con.getPalettesFromBoard(board.getId()), ResponseEntity.badRequest().build());
    }

}
