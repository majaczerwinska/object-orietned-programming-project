package server.api;

import commons.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.CardService;
import server.database.CardRepositoryTest;


import static org.junit.jupiter.api.Assertions.*;

public class CardControllerTest {
    private CardRepositoryTest repo;
    private CardController con;
    private CardService ser;

    @BeforeEach
    public void setup() {
        repo = new CardRepositoryTest();
        ser = new CardService(repo);
        con = new CardController(ser);
    }

    @Test
    public void addCardTest(){
        Card card = new Card("title");
        con.addCard(card);
        assertTrue(repo.existsById(card.getId()));

    }

    @Test
    public void deleteTest(){

        Card card = new Card("title");
        con.addCard(card);
        con.deleteCard(card.getId());
        assertFalse(repo.existsById(card.getId()));

    }

    @Test
    public void editCardTest(){
        Card card = new Card("title");
        Card card2 = new Card("title2");
        con.addCard(card);
        con.editCard(card.getId(), card2);
        assertEquals(card, card2);

    }

    @Test
    public void editCardNullTest(){
        ResponseEntity<Card> responseEntity = ResponseEntity.badRequest().build();
        Card card = new Card("title");
        Card card2 = new Card();
        con.addCard(card);
        con.editCard(card.getId(), card2);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }




}
