package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    public void checkConstructor() {
        var q = new Board("hi");
        assertEquals(q.getName(), "hi");
    }

    @Test
    public void equalsHashCode() {
        var a = new Board( "a");
        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Board("a");
        var b = new Board("b");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new Board("c").toString();
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("Board"));
    }

    @Test
    void setId() {
       Board c = new Board("a");
        c.setId(1);
        assertEquals(c.getId(), 1);
    }

    @Test
    void setLists() {
        Board c = new Board("a");
        List<CardList> list = new ArrayList<>();
        c.setLists(list);
        assertEquals(c.getLists(), list);
    }

    @Test
    void setTitle() {
        Board c = new Board("a");
        c.setName("b");
        assertEquals(c.getName(), "b");
    }

    @Test
    void setKey() {
        Board c = new Board("a");
        c.setBoardkey("b");
        assertEquals(c.getBoardkey(), "b");
    }

    @Test
    void setColor() {
        Board c = new Board("a");
        c.setColor(1);
        assertEquals(c.getColor(), 1);
    }

    @Test
    void getId() {
        Board c = new Board("a");
        assertEquals(c.getId(), 0);
    }

    @Test
    void getTitle() {
        Board c = new Board("a");
        assertEquals(c.getName(), "a");
    }

    @Test
    void getColor() {
        Board c = new Board("a");
        c.setColor(1);
        assertEquals(c.getColor(), 1);
    }

    @Test
    void getDescription() {
        Board c = new Board("a");
        c.setBoardkey("b");
        assertEquals(c.getBoardkey(), "b");
    }

    @Test
    void emptyConstructor() {
        Board c = new Board();
    }

}
