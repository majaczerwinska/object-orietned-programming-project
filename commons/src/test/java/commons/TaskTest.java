package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void defaultConstructorTest(){
        Task task = new Task();
        assertNotNull(task);
    }

    @Test
    public void constructorTest(){
        Task task = new Task("test");
        assertNotNull(task);
    }

    @Test
    public void getNameTest(){
        Task task = new Task("test");
        assertEquals("test", task.getName());
    }

    @Test
    public void setNameTest(){
        Task task = new Task("test");
        task.setName("test1");
        assertEquals("test1", task.getName());
    }
}
