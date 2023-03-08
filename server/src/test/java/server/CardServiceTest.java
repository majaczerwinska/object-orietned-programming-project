package server;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.CardController;
import server.database.CardRepositoryTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class CardServiceTest {
    private Random r;
    private CardRepositoryTest repo;
    private CardService ser;

    @BeforeEach
    public void setup() {
        r = new Random();
        repo = new CardRepositoryTest();
        ser = new CardService(repo);
    }


    @Test
    public void saveTest(){
        int id = r.nextInt();
        Card card = new Card(id, "title");
        ser.save(card);
        assertTrue(repo.existsById(id));

    }

    @Test
    public void getByIdTest(){
        int id = r.nextInt();
        Card card = new Card(id, "title");
        ser.save(card);

        System.out.println(ser.getById(id));
        assertEquals(ser.getById(id), card);

    }

    @Test
    public void deleteTest(){
        int id = r.nextInt();
        Card card = new Card(id, "title");
        ser.save(card);
        ser.delete(card);


        assertFalse(repo.existsById(id));

    }


}
