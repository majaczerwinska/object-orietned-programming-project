package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    TaskList tl = new TaskList("test");

    @Test
    void setId() {
        tl.setId(1);
        assertEquals(tl.getId(), 1);
    }

    @Test
    void setName() {
        tl.setName("hi");
        assertEquals(tl.getName(), "hi");
    }

    @Test
    void setColor() {
        tl.setColor(1);
        assertEquals(tl.getColor(),1);
    }

    @Test
    void getId() {
        assertEquals(tl.getId(), 0);
    }

    @Test
    void getName() {
        assertEquals(tl.getName(), "test");
    }

    @Test
    void getColor() {
        tl.setColor(0);
        assertEquals(tl.getColor(),0);
    }

    @Test
    void testEmptyConstructor() {
        TaskList t = new TaskList();
        assertNull(t.getName());
    }
}