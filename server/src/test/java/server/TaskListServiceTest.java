package server;

import commons.Card;
import commons.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.CardController;
import server.database.CardRepositoryTest;
import server.database.TaskListRepositoryTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class TaskListServiceTest {
    private TaskListRepositoryTest repo;
    private TaskListService ser;

    @BeforeEach
    public void setup() {
        repo = new TaskListRepositoryTest();
        ser = new TaskListService(repo);
    }


    @Test
    public void saveTest(){
        TaskList card = new TaskList("title");
        ser.save(card);
        assertTrue(repo.existsById(card.getId()));
    }

    @Test
    public void getByIdTest(){
        TaskList card = new TaskList("title");
        ser.save(card);
        assertEquals(ser.getById(card.getId()), card);

    }

    @Test
    public void existsByIdTest(){
        TaskList card = new TaskList("title");
        ser.save(card);

        assertTrue(ser.existsById(card.getId()));

    }

    @Test
    public void deleteTest(){
        TaskList card = new TaskList("title");
        ser.save(card);
        ser.delete(card);
        assertFalse(repo.existsById(card.getId()));

    }

    @Test
    public void updateTaskListTest(){
        TaskList list = new TaskList("title");
        ser.save(list);
        ser.updateTaskListName(list,"t");
        assertEquals(list.getName(), "t");

    }


}
