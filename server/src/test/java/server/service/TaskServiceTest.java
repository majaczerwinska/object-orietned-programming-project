package server.service;

import commons.Card;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.CardRepositoryTest;
import server.database.TaskRepositoryTest;
import server.service.TaskService;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskServiceTest {
    private TaskRepositoryTest repo;
    private CardRepositoryTest cr;
    private TaskService ser;

    @BeforeEach
    public void setup() {
        repo = new TaskRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TaskService(repo, cr);
    }


    @Test
    public void saveTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        ser.save(task, card.getId());
        assertTrue(cr.getById(card.getId()).getTasks().contains(task));
        assertTrue(repo.existsById(task.getId()));

    }


    @Test
    public void getByIdTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        ser.save(task, card.getId());
        assertEquals(ser.getById(task.getId()), task);

    }

    @Test
    public void existsByIdTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        ser.save(task, card.getId());
        assertTrue(ser.existsById(task.getId()));

    }


    @Test
    public void deleteTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        ser.save(task, card.getId());
        ser.delete(task, card.getId());
        assertFalse(repo.existsById(task.getId()));
        assertFalse(cr.getById(card.getId()).getTasks().contains(task));

    }

    @Test
    public void updateTaskTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        ser.save(task, card.getId());
        Task task2 = new Task("title2");

        ser.updateTask(task, task2.getName());
        assertEquals(task.getName(), task2.getName());

    }
}
