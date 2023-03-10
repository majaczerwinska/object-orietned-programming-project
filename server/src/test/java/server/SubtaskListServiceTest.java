package server;

import commons.CardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.CardListRepositoryTest;

import static org.junit.jupiter.api.Assertions.*;


public class SubtaskListServiceTest {
    private CardListRepositoryTest repo;
    private CardListService ser;

    @BeforeEach
    public void setup() {
        repo = new CardListRepositoryTest();
        ser = new CardListService(repo);
    }


    @Test
    public void saveTest(){
        CardList card = new CardList("title");
        ser.save(card);
        assertTrue(repo.existsById(card.getId()));
    }

    @Test
    public void getByIdTest(){
        CardList card = new CardList("title");
        ser.save(card);
        assertEquals(ser.getById(card.getId()), card);

    }

    @Test
    public void existsByIdTest(){
        CardList card = new CardList("title");
        ser.save(card);

        assertTrue(ser.existsById(card.getId()));

    }

    @Test
    public void deleteTest(){
        CardList card = new CardList("title");
        ser.save(card);
        ser.delete(card);
        assertFalse(repo.existsById(card.getId()));

    }

    @Test
    public void updateTaskListTest(){
        CardList list = new CardList("title");
        ser.save(list);
        ser.updateTaskListName(list,"t");
        assertEquals(list.getName(), "t");

    }


}
