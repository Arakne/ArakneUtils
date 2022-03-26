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

import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.common.value.qual.IntRange;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.util.Arrays;
import java.util.function.Function;

/**
 * List of available directions
 */
public enum Direction {
    EAST(width -> 1),
    SOUTH_EAST(width -> width),
    SOUTH(width -> 2 * width - 1),
    SOUTH_WEST(width -> width - 1),
    WEST(width -> -1),
    NORTH_WEST(width -> -width),
    NORTH(width -> -(2 * width - 1)),
    NORTH_EAST(width -> -(width - 1));

    public static final char FIRST_CHAR = 'a';
    public static final char LAST_CHAR = 'h';

    /**
     * Array of restricted directions (can be used on fight)
     */
    private static final Direction[] restricted = Arrays.stream(values()).filter(Direction::restricted).toArray(Direction[]::new);

    /**
     * Cache values
     */
    private static final Direction[] values = values();

    private final Function<Integer, Integer> computeNextCell;

    /**
     * @param computeNextCell The function for compute the next cell id following the direction
     */
    Direction(Function<Integer, Integer> computeNextCell) {
        this.computeNextCell = computeNextCell;
    }

    /**
     * Get the char value of the direction
     *
     * @return The direction char value
     */
    @Pure
    public char toChar() {
        return (char) (ordinal() + 'a');
    }

    /**
     * Get the opposite direction
     *
     * @return The direction
     */
    @Pure
    @SuppressWarnings("array.access.unsafe.low")
    public Direction opposite() {
        return values[(ordinal() + 4) % 8];
    }

    /**
     * Get the orthogonal direction
     *
     * @return The direction
     */
    @Pure
    @SuppressWarnings("array.access.unsafe.low")
    public Direction orthogonal() {
        return values[(ordinal() + 2) % 8];
    }

    /**
     * Does the direction is restricted
     *
     * A restricted direction can be used in fight, or by monsters's sprites
     * Restricted direction do not allow diagonal
     *
     * @return true if the direction can be used when restrictions are enabled
     */
    @Pure
    public boolean restricted() {
        return ordinal() % 2 == 1;
    }

    /**
     * Get the increment to apply to cell id for get the next cell on the direction
     *
     * Ex:
     * MapCell current = map.get(123);
     * MapCell east = map.get(Direction.EAST.nextCellIncrement(map.dimensions().width()) + current.id());
     *
     * @param mapWidth The map width
     *
     * @return The next cell id increment
     */
    @Pure
    public int nextCellIncrement(@Positive int mapWidth) {
        return computeNextCell.apply(mapWidth);
    }

    /**
     * Get the direction by its char value
     *
     * @param c The direction character value
     * @return The direction
     */
    @Pure
    public static Direction byChar(@IntRange(from = FIRST_CHAR, to = LAST_CHAR) char c) {
        return values[c - FIRST_CHAR];
    }

    /**
     * Get the restricted directions (i.e. can be used on fight)
     * Note: a new array is always returned
     *
     * @return The array of Direction
     */
    @SideEffectFree
    public static Direction[] restrictedDirections() {
        return restricted.clone();
    }
}
