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

package fr.arakne.utils.maps;

import fr.arakne.utils.maps._test.MyDofusCell;
import fr.arakne.utils.maps._test.MyDofusMap;
import fr.arakne.utils.maps.constant.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoordinateCellTest {
    private MyDofusMap map;

    @BeforeEach
    void setUp() {
        map = MyDofusMap.parse("HhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa6GHhaaeaaaaaHhaaeaaaaaHhaae6HaaaHhaae60aaaHhaaeaaaaaHhaae6HaaaHhaaeaaaaaGhaaeaaa7oHhaae6HiaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaa6SHhgSe6HaaaHhaaeaaa6IHhGaeaaaaaHhGaeaaaaaHhqaeaaaqgHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7iHhGaeaaaaaHhGaeaaa6IHhMSeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeJgaaaHhGaeaaaaaGhaaeaaa7hHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaa6THhGaeaaaaaHhGaeaaaaaHhMSe62aaaHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhqaeaaaqgGhaaeaaa7AHhGaeaaaaaHhGaeaaaaaHhaae6Ha7eHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhWaeaaaaaHhGaeaaaaaGhaaeaaa7gHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGae8uaaaGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGae8uaaaHhWae60aaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhqaeaaaqgHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7iHhGaeJgaaaHhaaeaaaaaHhaaeJgaaaHhGae6HaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaa6IGhaaeaaa7hGhaaeaaa7iHhGaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7lGhaae8sa7gHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaaeaaa7gGhaaeaaa7kHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWae62aaaGhaaeaaa7kGhaaeaaa7hHhGaeaaaaaHhGaeaaaaaGhaaeaaa7lHhaaeaaaaaGhaaeaaa7nHhGaeaaaaaGhaaeaaa7lGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7hHhGaeaaaaaGhaaeaaa7mHhGaeaaaaaGhaaeJga7hHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhMTgJgaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGae8saaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhMSeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaae6HaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7jHhGaeaaa6IHhGaeaaaaaHhaaeaaaaaHhaaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7gHhGaeaaaaaHhGaeaaaaaHhaaeaaa6GHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaaeaaa7kHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa6GHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhgTeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaa7dHhaaeaaaaaHhaaeaaa6WHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaGhaaeaaa7yHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaa6XHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhMVgaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaa");
    }

    @Test
    void firstCell() {
        CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(0));

        assertEquals(0, cell.x());
        assertEquals(0, cell.y());
        assertEquals(0, cell.id());
        assertEquals(map.get(0), cell.cell());
    }

    @Test
    void cell() {
        CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(157));

        assertEquals(17, cell.x());
        assertEquals(-7, cell.y());
        assertEquals(157, cell.id());
        assertEquals(map.get(157), cell.cell());
    }

    @Test
    void yAxis() {
        int inc = Direction.SOUTH_EAST.nextCellIncrement(map.dimensions().width());

        for (int i = 0, x = 0; i + inc <= 420; i += inc, ++x) {
            CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(i));

            assertEquals(0, cell.y());
            assertEquals(x, cell.x());
        }
    }

    @Test
    void xAxis() {
        int inc = Direction.SOUTH_WEST.nextCellIncrement(map.dimensions().width());

        for (int i = 123, y = -3; i + inc <= 305; i += inc, ++y) {
            CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(i));

            assertEquals(11, cell.x());
            assertEquals(y, cell.y());
        }
    }

    @Test
    void directionTo() {
        CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(157));

        assertEquals(Direction.SOUTH_WEST, cell.directionTo(new CoordinateCell<>(map.get(227))));
        assertEquals(Direction.NORTH_EAST, cell.directionTo(new CoordinateCell<>(map.get(129))));
        assertEquals(Direction.SOUTH_EAST, cell.directionTo(new CoordinateCell<>(map.get(217))));
        assertEquals(Direction.NORTH_WEST, cell.directionTo(new CoordinateCell<>(map.get(67))));
        assertEquals(Direction.NORTH_EAST, cell.directionTo(new CoordinateCell<>(map.get(157))));
    }

    @Test
    void distance() {
        CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(157));

        assertEquals(0, cell.distance(cell));
        assertEquals(5, cell.distance(new CoordinateCell<>(map.get(227))));
        assertEquals(24, cell.distance(new CoordinateCell<>(map.get(0))));
    }

    @Test
    void is() {
        CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(157));

        assertTrue(cell.is(17, -7));
        assertFalse(cell.is(18, -7));
        assertFalse(cell.is(17, -8));
        assertFalse(cell.is(18, -8));
    }

    @Test
    void equalsAndHashCode() {
        CoordinateCell<MyDofusCell> cell = new CoordinateCell<>(map.get(157));

        assertEquals(cell, cell);
        assertEquals(cell.hashCode(), cell.hashCode());
        assertEquals(cell.hashCode(), new CoordinateCell<>(map.get(157)).hashCode());

        assertNotEquals(cell, null);
        assertNotEquals(cell, new Object());
        assertNotEquals(cell, new CoordinateCell<>(map.get(127)));
        assertNotEquals(cell.hashCode(), new CoordinateCell<>(map.get(127)).hashCode());
    }
}
