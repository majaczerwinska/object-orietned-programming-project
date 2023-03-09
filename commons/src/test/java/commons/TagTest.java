package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void checkConstructor() {
        var q = new Tag("hi");
        assertEquals(q.getTitle(), "hi");
    }

    @Test
    public void equalsHashCode() {
        var a = new Tag( "a");
        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Tag("a");
        var b = new Tag("b");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new Tag("c").toString();
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("Tag"));
    }

    @Test
    void setId() {
        Tag c = new Tag("a");
        c.setId(1);
        assertEquals(c.getId(), 1);
    }

    @Test
    void setTitle() {
        Tag c = new Tag("a");
        c.setTitle("b");
        assertEquals(c.getTitle(), "b");
    }

    @Test
    void setDescription() {
        Tag c = new Tag("a");
        c.setDescription("b");
        assertEquals(c.getDescription(), "b");
    }

    @Test
    void setColor() {
        Tag c = new Tag("a");
        c.setColor(1);
        assertEquals(c.getColor(), 1);
    }

    @Test
    void getId() {
        Tag c = new Tag("a");
        assertEquals(c.getId(), 0);
    }

    @Test
    void getTitle() {
        Tag c = new Tag("a");
        assertEquals(c.getTitle(), "a");
    }

    @Test
    void getColor() {
        Tag c = new Tag("a");
        c.setColor(1);
        assertEquals(c.getColor(), 1);
    }

    @Test
    void getDescription() {
        Tag c = new Tag("a");
        c.setDescription("b");
        assertEquals(c.getDescription(), "b");
    }

    @Test
    void emptyConstructor() {
        Tag c = new Tag();
    }
}