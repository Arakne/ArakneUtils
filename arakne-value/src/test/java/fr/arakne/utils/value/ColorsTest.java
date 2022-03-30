/*
 * This file is part of ArakneUtils.
 *
 * ArakneUtils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ArakneUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ArakneUtils.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2017-2020 Vincent Quatrevieux
 */

package fr.arakne.utils.value;

import fr.arakne.utils.value.helper.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ColorsTest {
    @Test
    void getters() {
        Colors colors = new Colors(123, 456, 789);

        assertEquals(123, colors.color1());
        assertEquals(456, colors.color2());
        assertEquals(789, colors.color3());
    }

    @Test
    void defaultConstant() {
        assertEquals(-1, Colors.DEFAULT.color1());
        assertEquals(-1, Colors.DEFAULT.color2());
        assertEquals(-1, Colors.DEFAULT.color3());
    }

    @Test
    void toArray() {
        Colors colors = new Colors(123, 456, 789);

        assertArrayEquals(new int[] {123, 456, 789}, colors.toArray());
    }

    @Test
    void toHexArray() {
        Colors colors = new Colors(123, 456, 789);

        assertArrayEquals(new String[] {"7b", "1c8", "315"}, colors.toHexArray());
    }

    @Test
    void toHexArrayDefaultColors() {
        Colors colors = new Colors(-1, -1, 789);

        assertArrayEquals(new String[] {"-1", "-1", "315"}, colors.toHexArray());
    }

    @Test
    void toHexString() {
        Colors colors = new Colors(123, 456, 789);

        assertEquals("7b;1c8;315", colors.toHexString(";"));
    }

    @Test
    void equals() {
        Colors colors = new Colors(123, 456, 789);

        assertEquals(colors, colors);
        assertEquals(colors, new Colors(123, 456, 789));
        assertFalse(colors.equals(null));
        assertNotEquals(colors, new Colors(124, 456, 789));
        assertNotEquals(colors, new Colors(123, 455, 789));
        assertNotEquals(colors, new Colors(123, 456, 788));
        assertNotEquals(colors, new Object());
    }

    @Test
    void hashCodeTest() {
        Colors colors = new Colors(123, 456, 789);

        assertEquals(colors.hashCode(), new Colors(123, 456, 789).hashCode());
        assertNotEquals(colors.hashCode(), new Colors(123, 456, 788).hashCode());
    }

    @Test
    void randomized() {
        Random random = new Random(42);

        assertEquals(new Colors(12206493, 917130, 11462587), Colors.randomized(random));
        assertEquals(new Colors(804288, 5179452, 15805371), Colors.randomized(random));

        RandomUtil.createShared();
        RandomUtil.enableTestingMode();

        assertEquals(new Colors(12263604, 13949265, 4035531), Colors.randomized());
    }

    @Test
    void string() {
        assertEquals("Colors(12, 78, 11)", new Colors(12, 78, 11).toString());
    }
}
