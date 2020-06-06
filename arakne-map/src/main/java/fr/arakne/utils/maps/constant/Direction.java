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

    /**
     * Array of restricted directions (can be used on fight)
     */
    final static private Direction[] restricted = Arrays.stream(values()).filter(Direction::restricted).toArray(Direction[]::new);

    /**
     * Cache values
     */
    final static private Direction[] values = values();

    final private Function<Integer, Integer> computeNextCell;

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
    public char toChar(){
        return (char) (ordinal() + 'a');
    }

    /**
     * Get the opposite direction
     *
     * @return The direction
     */
    public Direction opposite(){
        return values[(ordinal() + 4) % 8];
    }

    /**
     * Get the orthogonal direction
     *
     * @return The direction
     */
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
    public int nextCellIncrement(int mapWidth) {
        return computeNextCell.apply(mapWidth);
    }

    /**
     * Get the direction by its char value
     *
     * @param c The direction character value
     * @return The direction
     */
    static public Direction byChar(char c) {
        return values[c - 'a'];
    }

    /**
     * Get the restricted directions (i.e. can be used on fight)
     * Note: a new array is always returned
     *
     * @return The array of Direction
     */
    static public Direction[] restrictedDirections() {
        return restricted.clone();
    }
}
