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

/**
 * Available movement values for a cell.
 * Contains information about the walkable state and path priority.
 */
public enum CellMovement {
    /**
     * Simple not walkable cell
     */
    NOT_WALKABLE,

    /**
     * Not walkable cell containing an interactive object
     */
    NOT_WALKABLE_INTERACTIVE,

    /**
     * Movement for triggers.
     * Note: some triggers has the "default" movement. This value is not reliable for detect triggers.
     */
    TRIGGER,

    /**
     * Walkable cells, but not prioritized.
     * Also contains cellar access cells.
     */
    LESS_WALKABLE,

    /**
     * Default movement value for walkable cells
     */
    DEFAULT,

    /**
     * Cells for paddocks
     */
    PADDOCK,

    /**
     * Cells for a road. Those cells are prioritized.
     */
    ROAD,

    /**
     * The higher movement value, the most prioritized walkable cells.
     */
    MOST_WALKABLE;

    /**
     * Cache the movement values
     */
    private static final CellMovement[] values = values();

    /**
     * Check if the current movement is for a walkable cell
     *
     * @return true is the cel is walkable
     */
    public boolean walkable() {
        return ordinal() > 1;
    }

    /**
     * Get a cell movement by its integer value
     *
     * @param value The movement id
     * @return The movement item
     */
    public static CellMovement byValue(int value) {
        return values[value];
    }
}
