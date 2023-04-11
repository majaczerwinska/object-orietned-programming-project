/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void checkConstructor() {
        var q = new Card("hi");
        assertEquals(q.getTitle(), "hi");
    }

    @Test
    public void equalsHashCode() {
        var a = new Card( "a");
        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Card("a");
        var b = new Card("b");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new Card("c").toString();
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("Card"));
    }

    @Test
    void setId() {
        Card c = new Card("a");
        c.setId(1);
        assertEquals(c.getId(), 1);
    }

    @Test
    void setTitle() {
        Card c = new Card("a");
        c.setTitle("b");
        assertEquals(c.getTitle(), "b");
    }

    @Test
    void setDescription() {
        Card c = new Card("a");
        c.setDescription("b");
        assertEquals(c.getDescription(), "b");
    }

    @Test
    void setTasks() {
        Card c = new Card("a");
        List<Task> list = new ArrayList<>();
        c.setTasks(list);
        assertEquals(c.getTasks(), list);
    }

    @Test
    void setColor() {
        Card c = new Card("a");
        c.setColor(1);
        assertEquals(c.getColor(), 1);
    }

    @Test
    void getId() {
        Card c = new Card("a");
        assertEquals(c.getId(), 0);
    }
    @Test
    void getPosition() {
        Card c = new Card("a");
        assertEquals(c.getPosition(), 0.0);
    }
    @Test
    void setPosition() {
        Card c = new Card("a");
        c.setPosition(1);
        assertEquals(c.getPosition(), 1);
    }

    @Test
    void getPalette() {
        Card c = new Card("a");
        assertEquals(c.getPalette(), "");
    }

    @Test
    void setPalette() {
        Card c = new Card("a");
        Palette p = new Palette("name", 0, 0);
        c.setPalette(p.getName());
        assertEquals(c.getPalette(), p.getName());
    }


    @Test
    void getTitle() {
        Card c = new Card("a");
        assertEquals(c.getTitle(), "a");
    }

    @Test
    void getColor() {
        Card c = new Card("a");
        c.setColor(1);
        assertEquals(c.getColor(), 1);
    }

    @Test
    void setFColor() {
        Card c = new Card("a");
        c.setFcolor(1);
        assertEquals(c.getFcolor(), 1);
    }
    @Test
    void getFColor() {
        Card c = new Card("a");
        assertEquals(c.getFcolor(), 0);
    }

    @Test
    void getDescription() {
        Card c = new Card("a");
        c.setDescription("b");
        assertEquals(c.getDescription(), "b");
    }

    @Test
    void emptyConstructor() {
        Card c = new Card();
        assertTrue(c.getTitle()==null);
    }

    @Test
    void getListTest(){
        Card c = new Card("test");
        List<Task> task = new ArrayList<>();
        assertEquals(task, c.getTasks());
    }

    @Test
    void hasDescriptionTest() {
        Card c = new Card("a");
        assertFalse(c.hasDescription());
    }

    @Test
    void softEqualsTest() {
        Card c = new Card("a");
        c.setDescription("ab");
        Card b = new Card("a");
        b.setDescription("ab");
        assertTrue(c.softEquals(b));
    }

    @Test
    void setTags() {
        Card c = new Card("a");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("a"));
        c.setTags(tags);
        assertEquals(c.getTags(), tags);
    }
}