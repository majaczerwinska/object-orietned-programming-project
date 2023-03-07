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

public class CardTest {

    @Test
    public void checkConstructor() {
        var q = new Card(0, "hi");
        assertEquals(q.getId(), 0);
        assertEquals(q.getTitle(), "hi");
    }

    @Test
    public void equalsHashCode() {
        var a = new Card(0, "a");
        var b = new Card(0, "a");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Card(0, "a");
        var b = new Card(0, "b");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new Card(0, "c").toString();
        assertTrue(actual.contains(Quote.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("Card"));
    }
}