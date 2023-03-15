package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardListTest {

    CardList tl = new CardList("test");

    @Test
    void setId() {
        tl.setId(1);
        assertEquals(tl.getId(), 1);
    }

    @Test
    void setCards() {
        List<Card> list = new ArrayList<>();
        tl.setCards(list);
        assertEquals(tl.getCards(), list);
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

    @Test
    public void toStringTest(){
        CardList t = new CardList("t");
        assertEquals(t.toString(), "List #" + t.getId()+ ", " + t.getName()+ ", "+t.getColor());
    }
}