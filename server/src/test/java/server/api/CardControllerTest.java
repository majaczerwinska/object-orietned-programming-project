package server.api;

import commons.Card;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.CardService;
import server.database.CardRepository;
import server.database.CardRepositoryTest;
import server.database.QuoteRepositoryTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CardControllerTest {
    private Random r;
    private CardRepositoryTest repo;
    private CardController con;
    private CardService ser;

    @BeforeEach
    public void setup() {
        r = new Random();
        repo = new CardRepositoryTest();
        ser = new CardService(repo);
        con = new CardController(ser);
    }

    @Test
    public void addCardTest(){
        int id = r.nextInt();
        Card card = new Card(id, "title");
        con.addCard(card);
        assertTrue(repo.existsById(id));

    }

    @Test
    public void deleteTest(){
        int id = r.nextInt();
        Card card = new Card(id, "title");
        con.addCard(card);
        con.deleteCard(id);
        assertFalse(repo.existsById(id));

    }




}
