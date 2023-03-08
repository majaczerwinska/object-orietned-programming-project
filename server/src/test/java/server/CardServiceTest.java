package server;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.CardController;
import server.database.CardRepositoryTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class CardServiceTest {
    private CardRepositoryTest repo;
    private CardService ser;

    @BeforeEach
    public void setup() {
        repo = new CardRepositoryTest();
        ser = new CardService(repo);
    }


    @Test
    public void saveTest(){
        Card card = new Card("title");
        ser.save(card);
        assertTrue(repo.existsById(card.getId()));

    }

    @Test
    public void getByIdTest(){
        Card card = new Card("title");
        ser.save(card);

        System.out.println(ser.getById(card.getId()));
        assertEquals(ser.getById(card.getId()), card);

    }

    @Test
    public void deleteTest(){
        Card card = new Card("title");
        ser.save(card);
        ser.delete(card);
        assertFalse(repo.existsById(card.getId()));

    }


}
