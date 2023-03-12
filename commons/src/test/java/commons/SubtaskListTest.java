package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskListTest {

    CardList tl = new CardList("test");

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
        CardList t = new CardList();
        assertNull(t.getName());
    }
}