package server.service;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.BoardRepositoryTest;
import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CardListServiceTest {
    private CardListRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardListService ser;
    private CardRepositoryTest cr;
    private CardService cardService;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        br  = new BoardRepositoryTest();
        ser = new CardListService(repo, br);
        cr = new CardRepositoryTest();
        cardService = new CardService(cr, repo);
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

    @Test
    public void getCardsFromListTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        Card card = new Card("title");
        int listId = list.getId();
        br.save(board);
        ser.save(list, board.getId());
        cardService.save(card, listId);
        List<Card> cardList = List.of(card);
        assertEquals(ser.getCardsFromList(listId), cardList);
    }

}
