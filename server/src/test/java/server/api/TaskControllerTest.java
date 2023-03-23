package server.api;

import commons.Card;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.service.TaskService;
import server.database.CardRepositoryTest;
import server.database.TaskRepositoryTest;


import static org.junit.jupiter.api.Assertions.*;

public class TaskControllerTest {
    private TaskRepositoryTest repo;
    private CardRepositoryTest cr;
    private TaskController con;
    private TaskService ser;

    @BeforeEach
    public void setup() {
        repo = new TaskRepositoryTest();
        cr = new CardRepositoryTest();
        ser = new TaskService(repo, cr);
        con = new TaskController(ser);
    }

    @Test
    public void addTaskToCardTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        con.addTaskToCard(card.getId(), task);
        assertTrue(repo.existsById(task.getId()));

    }

    @Test
    public void deleteTest(){
        Task task = new Task("title");
        Card card = new Card("title");
        cr.save(card);
        con.addTaskToCard(card.getId(), task);
        con.deleteTask(card.getId(), task.getId());
        assertFalse(repo.existsById(task.getId()));

    }

    @Test
    public void editTaskTest(){
        Task task1 = new Task("title1");
        Card card = new Card("title");
        cr.save(card);
        con.addTaskToCard(card.getId(), task1);
        con.editTask(task1.getId(), "t");
        assertEquals(task1.getName(), "t");

    }

    @Test
    public void getTaskTest(){
        Task task1 = new Task("title1");
        Card card = new Card("title");
        cr.save(card);
        con.addTaskToCard(card.getId(), task1);

        assertEquals(con.getTask(task1.getId()), ResponseEntity.ok(task1));

    }





}
