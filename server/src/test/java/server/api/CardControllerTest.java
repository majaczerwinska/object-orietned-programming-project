package server.api;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.service.CardService;
import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardControllerTest {
    private CardRepositoryTest repo;
    private CardListRepositoryTest cl;
    private CardController con;
    private CardService ser;

    @BeforeEach
    public void setup() {
        repo = new CardRepositoryTest();
        cl = new CardListRepositoryTest();
        ser = new CardService(repo, cl);
        con = new CardController(ser);
    }

    @Test
    public void addCardTest(){
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(list.getId(),card);
        assertTrue(repo.existsById(card.getId()));

    }

    @Test
    public void deleteTest(){
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(list.getId(), card);
        con.deleteCard(list.getId(), card.getId());
        assertFalse(repo.existsById(card.getId()));

    }

    @Test
    public void editCardTest(){
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        Card card2 = new Card("title2");
        con.addCard(list.getId(), card);
        con.editCard(card.getId(), card2);
        assertEquals(card, card2);

    }

    @Test
    public void editCardNullTest(){
        ResponseEntity<Card> responseEntity = ResponseEntity.badRequest().build();
        CardList list = new CardList("l");
        cl.save(list);
        Card card = new Card("title");
        Card card2 = new Card();
        con.addCard(list.getId(),card);
        con.editCard(card.getId(), card2);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    public void getCardTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        con.addCard(list.getId(), card);
        ResponseEntity<Card> cardResponseEntity = ResponseEntity.ok(card);
        assertEquals(con.getCard(card.getId()), cardResponseEntity);
    }
    @Test
    public void getTasksTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        List<Task> tasks = new ArrayList<>();
        card.setTasks(tasks);
        con.addCard(list.getId(), card);
        assertEquals(con.getTasks(card.getId()),ResponseEntity.ok(tasks) );
    }

//    @Test
//    public void changeListForTaskTest(){
//        CardList list = new CardList("c");
//        CardList list2 = new CardList("c");
//
//        Card card = new Card("title");
//        List<Card> cards = new ArrayList<>();
//        cards.add(card);
//        list.setCards(cards);
//
//        cl.save(list);
//        cl.save(list2);
//        con.addCard(list.getId(), card);
//
//        con.changeListforCard(card.getId(), list2.getId(), card);
//
//        assertEquals(cl.getById(list2.getId()).getCards(), cards);
//    }




}
