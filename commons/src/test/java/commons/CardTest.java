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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}