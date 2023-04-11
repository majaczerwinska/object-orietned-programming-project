package server.service;

import commons.Board;
import commons.Card;
import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.database.CardListRepositoryTest;
import server.database.CardRepositoryTest;
import server.service.CardService;


import java.util.ArrayList;
import java.util.List;

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
    public void findByIdTest(){
        CardList list = new CardList("c");
        cl.save(list);
        Card card = new Card("title");
        ser.save(card, list.getId());
        assertEquals(ser.findById(card.getId()).get(), card);

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
        Card c = new Card("t");
        repo.save(c);
        assertNull(ser.delete(c,7));
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

    @Test
    public void getListForCardNull(){
        CardList list = new CardList("l");
        Card card1 = new Card("c1");
        Card card2 = new Card("c2");
        List<Card> cards = new ArrayList<>();
        list.setCards(cards);
        cl.save(list);
        cards.add(card1);
        ser.save(card1,list.getId());
        repo.save(card2);
        assertNull(ser.getListForCard(card2));
    }

    @Test
    public void getListForCardTest(){
        CardList list = new CardList("l");
        Card card1 = new Card("c1");
        Card card2 = new Card("c2");
        List<Card> cards = new ArrayList<>();
        list.setCards(cards);
        cl.save(list);
        cards.add(card1);
        ser.save(card1, list.getId());

        cards.add(card2);
        ser.save(card2, list.getId());

        assertEquals(ser.getListForCard(card2), list);

    }

    @Test
    public void changeListForCardTest(){
        CardList list = new CardList("l");
        Card card1 = new Card("c1");
        Card card2 = new Card("c2");
        List<Card> cards = new ArrayList<>();

        CardList list2 = new CardList("l2");
        list.setCards(cards);
        cl.save(list);
        cl.save(list2);
        cards.add(card1);
        ser.save(card1, list.getId());
        cards.add(card2);
        ser.save(card2, list.getId());
        ser.changeListOfCard(card2, list2.getId());

        assertEquals(list2.getId(), ser.getListForCard(card2).getId());

    }

}
