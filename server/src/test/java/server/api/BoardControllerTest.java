package server.api;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.CardListRepositoryTest;
import server.service.BoardService;
import server.database.BoardRepositoryTest;
import server.service.CardListService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardControllerTest {
    private BoardRepositoryTest repo;
    private BoardController con;
    private BoardService ser;
    private CardListRepositoryTest cl;
    private CardListService cardListService;

    @BeforeEach
    public void setup() {
        repo = new BoardRepositoryTest();
        ser = new BoardService(repo);
        con = new BoardController(ser);
        cl = new CardListRepositoryTest();
        cardListService = new CardListService(cl, repo);
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

}
