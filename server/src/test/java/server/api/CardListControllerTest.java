package server.api;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.CardRepositoryTest;
import server.service.CardListService;
//import server.database.CardRepository;
//import server.database.QuoteRepositoryTest;
import server.database.BoardRepositoryTest;
import server.database.CardListRepositoryTest;
import server.service.CardService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardListControllerTest {
    private CardListRepositoryTest repo;
    private BoardRepositoryTest br;
    private CardListController con;
    private CardListService ser;
    private CardRepositoryTest cr;
    private CardService cardService;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        br = new BoardRepositoryTest();
        ser = new CardListService(repo, br);
        con = new CardListController(ser);
        cr = new CardRepositoryTest();
        cardService = new CardService(cr, repo);
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

    @Test
    public void getCardsFromListTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        Card card = new Card("title");
        int listId = list.getId();
        br.save(board);
        ser.save(list, board.getId());
        cardService.save(card, listId);
        ResponseEntity<List<Card>>  cardList = ResponseEntity.ok(List.of(card));
        assertEquals(con.getCardsFromList(listId), cardList);
    }

    @Test
    public void getCardsFromListFromNonExistingListTest(){
        CardList list = new CardList("c");
        int listId = list.getId();
        assertEquals(con.getCardsFromList(listId), ResponseEntity.badRequest().build());
    }



    @Test
    public void getListSizeTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        Card card = new Card("title");
        Card card2 = new Card("title2");
        Card card3 = new Card("title3");
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.add(card2);
        cards.add(card3);
        list.setCards(cards);
        list.lastPosition = 9;
        int listId = list.getId();
        br.save(board);
        ser.save(list, board.getId());
        cardService.save(card, listId);
        cardService.save(card2, listId);
        cardService.save(card3, listId);
        ResponseEntity<Integer>  size = ResponseEntity.ok(9);
        assertEquals(con.getListSize(listId), size);
    }

    @Test
    public void setListSizeTest(){
        Board board = new Board("board");
        CardList list = new CardList("c");
        Card card = new Card("title");
        Card card2 = new Card("title2");
        Card card3 = new Card("title3");
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.add(card2);
        cards.add(card3);
        list.setCards(cards);
        list.lastPosition = 9;
        int listId = list.getId();
        br.save(board);
        ser.save(list, board.getId());
        cardService.save(card, listId);
        cardService.save(card2, listId);
        cardService.save(card3, listId);
        ResponseEntity<Integer>  size = ResponseEntity.ok(12);
        assertEquals(con.setListSize(listId, 12), size);
    }


}
