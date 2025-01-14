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
        var actual = new Board("b1").toString();
//        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("b1"));
    }

    @Test
    void setId() {
       Board c = new Board("a");
        c.setId(1);
        assertEquals(c.getId(), 1);
    }

    @Test
    void setListt() {
        Board c = new Board("a");
        c.setListt(1);
        assertEquals(c.getListt(), 1);
    }
    @Test
    void setListb() {
        Board c = new Board("a");
        c.setListb(1);
        assertEquals(c.getListb(), 1);
    }

    @Test
    void setLists() {
        Board c = new Board("a");
        List<CardList> list = new ArrayList<>();
        c.setLists(list);
        assertEquals(c.getLists(), list);
    }
    @Test
    void setTags() {
        Board c = new Board("a");
        List<Tag> list = new ArrayList<>();
        list.add(new Tag("a"));
        c.setTags(list);
        assertEquals(c.getTags(), list);
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

//    @Test
//    void setColor() {
//        Board c = new Board("a");
//        c.setColor(1);
//        assertEquals(c.getColor(), 1);
//    }

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

//    @Test
//    void getColor() {
//        Board c = new Board("a");
//        c.setColor(1);
//        assertEquals(c.getColor(), 1);
//    }

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

    @Test
    void getPassword() {
        Board b = new Board();
        assertNull(b.getPassword());
    }

    @Test
    void setPassword() {
        Board b = new Board();
        b.setPassword("a");
        assertEquals("a", b.getPassword());
    }

    @Test
    public void checkEmptyConstructor() {
        var q = new Board();
        assertNotNull(q);
    }

    @Test
    void getListTest() {
        Board c = new Board("a");
        List<CardList> list = new ArrayList<>();
        list.add(new CardList("e"));
        c.setLists(list);
        assertEquals(c.getLists(), list);
    }

    @Test
    void getBoardKeyTest() {
        Board c = new Board("a");
        c.setBoardkey("boardKey");

        assertEquals(c.getBoardkey(), "boardKey");
    }

    @Test
    void setBoardKeyTest() {
        Board c = new Board("a");
        c.setBoardkey("boardKey");

        assertEquals(c.getBoardkey(), "boardKey");
    }

    @Test
    void getBColorTest() {
        Board c = new Board("a");
        c.setbColor(23467);

        assertEquals(c.getbColor(), 23467);
    }

    @Test
    void setBColorTest() {
        Board c = new Board("a");
        c.setbColor(23467);

        assertEquals(c.getbColor(), 23467);
    }

    @Test
    void getFColorTest() {
        Board c = new Board("a");
        c.setfColor(23467);

        assertEquals(c.getfColor(), 23467);
    }

    @Test
    void setFColorTest() {
        Board c = new Board("a");
        c.setfColor(23467);

        assertEquals(c.getfColor(), 23467);
    }

    @Test
    void getPalettesTest() {
        Board c = new Board("a");
        List<Palette> paletteList = new ArrayList<>();
        paletteList.add(new Palette("123", 125, 123));
        c.setPalettes(paletteList);

        assertEquals(c.getPalettes(), paletteList);
    }

    @Test
    void setPalettesTest() {
        Board c = new Board("a");
        List<Palette> paletteList = new ArrayList<>();
        paletteList.add(new Palette("123", 125, 123));
        c.setPalettes(paletteList);

        assertEquals(c.getPalettes(), paletteList);
    }



}
