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

package fr.arakne.utils.maps.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void toChar() {
        assertEquals('e', Direction.WEST.toChar());
        assertEquals('a', Direction.EAST.toChar());
    }

    @Test
    void byChar() {
        assertEquals(Direction.WEST, Direction.byChar('e'));
        assertEquals(Direction.SOUTH, Direction.byChar('c'));
    }

    @Test
    void opposite() {
        assertEquals(Direction.WEST, Direction.EAST.opposite());
        assertEquals(Direction.NORTH, Direction.SOUTH.opposite());
    }

    @Test
    void orthogonal() {
        assertEquals(Direction.SOUTH, Direction.EAST.orthogonal());
        assertEquals(Direction.WEST, Direction.SOUTH.orthogonal());
        assertEquals(Direction.NORTH, Direction.WEST.orthogonal());
        assertEquals(Direction.EAST, Direction.NORTH.orthogonal());
    }

    @Test
    void restricted() {
        assertFalse(Direction.WEST.restricted());
        assertFalse(Direction.NORTH.restricted());
        assertFalse(Direction.EAST.restricted());
        assertFalse(Direction.SOUTH.restricted());

        assertTrue(Direction.SOUTH_WEST.restricted());
        assertTrue(Direction.NORTH_WEST.restricted());
        assertTrue(Direction.NORTH_EAST.restricted());
        assertTrue(Direction.SOUTH_EAST.restricted());

        assertArrayEquals(
            new Direction[] {Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_WEST, Direction.NORTH_EAST},
            Direction.restrictedDirections()
        );
    }

    @Test
    void nextCellIncrement() {
        assertEquals(1, Direction.EAST.nextCellIncrement(15));
        assertEquals(15, Direction.SOUTH_EAST.nextCellIncrement(15));
        assertEquals(29, Direction.SOUTH.nextCellIncrement(15));
        assertEquals(14, Direction.SOUTH_WEST.nextCellIncrement(15));
        assertEquals(-1, Direction.WEST.nextCellIncrement(15));
        assertEquals(-15, Direction.NORTH_WEST.nextCellIncrement(15));
        assertEquals(-29, Direction.NORTH.nextCellIncrement(15));
        assertEquals(-14, Direction.NORTH_EAST.nextCellIncrement(15));
    }
}
