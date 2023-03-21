package commons;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {

    @Test
    public void checkConstructorTitle() {
        var q = new Tag("hi");
        assertEquals(q.getTitle(), "hi");
    }

    @Test
    public void checkConstructorTitleDescription() {
        var q = new Tag("hi", "desc");
        assertEquals(q.getTitle(), "hi");
        assertEquals(q.getDescription(), "desc");
    }

    @Test
    public void checkConstructorTitleDescriptionColor() {
        var q = new Tag("hi", "desc", 1);
        assertEquals(q.getTitle(), "hi");
        assertEquals(q.getDescription(), "desc");
        assertEquals(q.getColor(), 1);
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
    public void notEquals() {
        Tag a = new Tag("a");
        Tag b = new Tag("b");
        assertNotEquals(a,b);

    }
    @Test
    public void notEquals1() {
        Tag a = new Tag("a");

        assertNotEquals(a,"garfield");

    }

    @Test
    public void notEquals2() {
        var a = new Tag("a");
        assertEquals(a,a);

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
    void setCardsAndGetCards() {
        Tag c = new Tag("a");
        List<Card> list = new ArrayList<>();
        Card card = new Card("wdjhbhr");
        list.add(card);
        c.setCards(list);
        assertEquals(c.getCards(), list);
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
        assertNull(c.getTitle());
    }


}