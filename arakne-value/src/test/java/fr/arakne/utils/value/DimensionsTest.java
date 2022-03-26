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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DimensionsTest {
    @Test
    void data() {
        Dimensions dimensions = new Dimensions(15, 17);

        assertEquals(17, dimensions.height());
        assertEquals(15, dimensions.width());
    }

    @Test
    void equals() {
        Dimensions dimensions = new Dimensions(12, 15);

        assertFalse(dimensions.equals(null));
        assertNotEquals(dimensions, new Object());
        assertNotEquals(dimensions, new Dimensions(15, 12));

        assertEquals(dimensions, dimensions);
        assertEquals(dimensions, new Dimensions(12, 15));
    }

    @Test
    void hash() {
        assertNotEquals(new Dimensions(15, 12).hashCode(), new Dimensions(12, 15).hashCode());
        assertEquals(new Dimensions(12, 15).hashCode(), new Dimensions(12, 15).hashCode());
    }

    @Test
    void string() {
        assertEquals("Dimensions(12x15)", new Dimensions(12, 15).toString());
    }
}
