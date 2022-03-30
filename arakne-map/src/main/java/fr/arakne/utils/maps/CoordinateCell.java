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

import fr.arakne.utils.maps.constant.Direction;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;

/**
 * Map cell with coordinates
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Pathfinding.as#L191
 */
public final class CoordinateCell<C extends @NonNull MapCell<C>> {
    private final C cell;

    private final int x;
    private final int y;

    /**
     * CoordinateCell constructor.
     * Prefer use {@link MapCell#coordinate()} method instead of directly call the constructor
     *
     * @param cell The cell to wrap
     *
     * @see MapCell#coordinate() For get the {@link CoordinateCell} instance from a cell
     */
    public CoordinateCell(C cell) {
        this.cell = cell;

        final int width = cell.map().dimensions().width();
        final int line = cell.id() / (width * 2 - 1);
        final int column = cell.id() - line * (width * 2 - 1);
        final int offset = column % width;

        this.y = line - offset;
        this.x = (cell.id() - (width - 1) * this.y) / width;
    }

    /**
     * Get the related map cell
     *
     * @return The base cell instance
     */
    @Pure
    public C cell() {
        return cell;
    }

    /**
     * Get the cell id
     *
     * @return The cell id
     */
    @Pure
    public @NonNegative int id() {
        return cell.id();
    }

    /**
     * @return The X coordinate of the cell
     */
    @Pure
    public int x() {
        return x;
    }

    /**
     * @return The Y coordinate of the cell
     */
    @Pure
    public int y() {
        return y;
    }

    /**
     * Check if the cell is at the given coordinates
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     *
     * @return true if coordinates match, or false otherwise
     */
    @Pure
    public boolean is(int x, int y) {
        return this.x == x && this.y == y;
    }

    /**
     * Compute the direction to the target cell
     *
     * https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Pathfinding.as#L204
     *
     * @param target The target cell
     * @return The direction
     */
    @Pure
    public Direction directionTo(CoordinateCell<C> target) {
        if (x == target.x) {
            if (target.y > y) {
                return Direction.SOUTH_WEST;
            } else {
                return Direction.NORTH_EAST;
            }
        } else if (target.x > x) {
            return Direction.SOUTH_EAST;
        } else {
            return Direction.NORTH_WEST;
        }
    }

    /**
     * Compute the direction to the target cell
     *
     * https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Pathfinding.as#L204
     *
     * @param target The target cell
     * @return The direction
     */
    public Direction directionTo(C target) {
        return directionTo(target.coordinate());
    }

    /**
     * Get the cell distance
     * Note: Do not compute a pythagorean distance, but "square" distance.
     *       So, this distance represents the minimal number of cells for a path between current and target.
     *
     * @param target The target cell
     * @return The distance, in cells number
     */
    @Pure
    public @NonNegative int distance(CoordinateCell<C> target) {
        return Math.abs(x - target.x) + Math.abs(y - target.y);
    }

    /**
     * Get the cell distance
     * Note: Do not compute a pythagorean distance, but "square" distance.
     *       So, this distance represents the minimal number of cells for a path between current and target.
     *
     * @param target The target cell
     * @return The distance, in cells number
     */
    public @NonNegative int distance(C target) {
        return distance(target.coordinate());
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CoordinateCell<?> that = (CoordinateCell<?>) o;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
