package server.api;

import commons.Card;
import commons.TaskList;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.CardService;
import server.TaskListService;
import server.TaskListServiceTest;
//import server.database.CardRepository;
import server.database.CardRepositoryTest;
//import server.database.QuoteRepositoryTest;
import server.database.TaskListRepositoryTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListControllerTest {
    private TaskListRepositoryTest repo;
    private TaskListController con;
    private TaskListService ser;

    @BeforeEach
    public void setup() {
        repo = new TaskListRepositoryTest();
        ser = new TaskListService(repo);
        con = new TaskListController(ser);
    }

    @Test
    public void addtaskTest(){
        TaskList task = new TaskList("title");
        con.addList(task);
        assertTrue(repo.existsById(task.getId()));

    }

    @Test
    public void deleteTest(){

        TaskList task = new TaskList("title");
        con.addList(task);
        con.deleteList(task.getId());
        assertFalse(repo.existsById(task.getId()));

    }

    @Test
    public void renameTest(){

        TaskList task = new TaskList("title");
        con.addList(task);
        con.renameList(task.getId(),"t");
        assertEquals(task.getName(),"t");

    }




}
