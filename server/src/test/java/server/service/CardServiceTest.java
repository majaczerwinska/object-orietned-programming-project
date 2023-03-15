package server.service;

import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.CardService;


import static org.junit.jupiter.api.Assertions.*;


public class CardServiceTest {
    private CardRepositoryTest repo;
    private CardListRepositoryTest cl;
    private CardService ser;

    @BeforeEach
    public void setup() {
        repo = new CardRepositoryTest();
        cl = new CardListRepositoryTest();
        ser = new CardService(repo, cl);
    }


    @Test
    public void saveTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        assertTrue(repo.existsById(card.getId()));
        assertTrue(cl.getById(list.getId()).getCards().contains(card));

    }

    @Test
    public void getByIdTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        assertEquals(ser.getById(card.getId()), card);

    }

    @Test
    public void existsByIdTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        assertTrue(ser.existsById(card.getId()));

    }


    @Test
    public void deleteTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        ser.delete(card, list.getId());
        assertFalse(repo.existsById(card.getId()));
        assertFalse(cl.getById(list.getId()).getCards().contains(card));

    }

    @Test
    public void setCardInfoTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        Card card2 = new Card("title2");
        card2.setId(card.getId());
        ser.setCardInfo(card2);
        assertEquals(card.getTitle(), card2.getTitle());

    }



}
