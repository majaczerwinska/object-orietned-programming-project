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

    @Test
    public void setIdTest(){
        Task task = new Task("test");
        task.setId(6);
        assertEquals(task.getId(), 6);
    }

    @Test
    public void getIdTest(){
        Task task = new Task("test");
        assertEquals(task.getId(), 0);
    }
    @Test
    public void toStringTest(){
        Task task = new Task("test");
        assertEquals(task.toString(), "Task #" + task.getId()+ ", name: " + task.getName() + ", checked: "+task.isChecked());
    }


    @Test
    public void setCheckedTest(){
        Task task = new Task("test");
        task.setChecked(true);
        assertTrue(task.isChecked());
    }




}
