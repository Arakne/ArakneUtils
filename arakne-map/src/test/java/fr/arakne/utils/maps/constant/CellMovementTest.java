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

class CellMovementTest {
    @Test
    void byValue() {
        assertSame(CellMovement.NOT_WALKABLE, CellMovement.byValue(0));
        assertSame(CellMovement.NOT_WALKABLE_INTERACTIVE, CellMovement.byValue(1));
        assertSame(CellMovement.TRIGGER, CellMovement.byValue(2));
        assertSame(CellMovement.LESS_WALKABLE, CellMovement.byValue(3));
        assertSame(CellMovement.DEFAULT, CellMovement.byValue(4));
        assertSame(CellMovement.PADDOCK, CellMovement.byValue(5));
        assertSame(CellMovement.ROAD, CellMovement.byValue(6));
        assertSame(CellMovement.MOST_WALKABLE, CellMovement.byValue(7));
    }

    @Test
    void walkable() {
        assertFalse(CellMovement.NOT_WALKABLE.walkable());
        assertFalse(CellMovement.NOT_WALKABLE_INTERACTIVE.walkable());
        assertTrue(CellMovement.TRIGGER.walkable());
        assertTrue(CellMovement.LESS_WALKABLE.walkable());
        assertTrue(CellMovement.DEFAULT.walkable());
        assertTrue(CellMovement.PADDOCK.walkable());
        assertTrue(CellMovement.ROAD.walkable());
        assertTrue(CellMovement.MOST_WALKABLE.walkable());
    }
}
