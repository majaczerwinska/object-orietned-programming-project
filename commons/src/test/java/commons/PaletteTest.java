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

public class PaletteTest {

    @Test
    public void checkConstructor() {
        var p = new Palette("hi", 1, 2);
        assertEquals(p.getName(), "hi");
        assertEquals(p.getbColor(), 1);
        assertEquals(p.getfColor(),  2);
    }





    @Test
    void setId() {
        Palette c = new Palette("a", 2, 4);
        c.setId(4);
        assertEquals(c.getId(), 4);
    }

    @Test
    void setNameTest() {
        Palette p = new Palette("a", 1, 2);
        p.setName("b");
        assertEquals(p.getName(), "b");
    }

    @Test
    void getNameTest() {
        Palette p = new Palette("a", 1, 2);
        assertEquals(p.getName(), "a");
    }

    @Test
    void setFColorTest() {
        Palette p = new Palette("a", 1, 2);
        p.setfColor(5);
        assertEquals(p.getfColor(), 5);
    }

    @Test
    void getFColorTest() {
        Palette p = new Palette("a", 1, 2);
        assertEquals(p.getfColor(), 2);
    }

    @Test
    void setBColorTest() {
        Palette p = new Palette("a", 1, 2);
        p.setbColor(5);
        assertEquals(p.getbColor(), 5);
    }

    @Test
    void getBColorTest() {
        Palette p = new Palette("a", 1, 2);
        assertEquals(p.getbColor(), 1);
    }

    @Test
    void setIsDefaultTest() {
        Palette p = new Palette("a", 1, 2);
        p.setIsdefault(true);
        assertTrue(p.isIsdefault());
    }

    @Test
    void getIsDefaultTest() {
        Palette p = new Palette("a", 1, 2);
        assertFalse(p.isIsdefault());
    }


    @Test
    void emptyConstructor() {
        Palette p = new Palette();
        assertNull(p.getName());
        assertNotNull(p);
    }



}