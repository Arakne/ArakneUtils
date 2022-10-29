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

package fr.arakne.utils.maps.path;

import fr.arakne.utils.maps.MapCell;
import fr.arakne.utils.maps.constant.Direction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

/**
 * Step for a path
 */
public final class PathStep<C extends @NonNull MapCell> {
    private final C cell;
    private final Direction direction;

    /**
     * @param cell The cell
     * @param direction The direction
     */
    public PathStep(C cell, Direction direction) {
        this.cell = cell;
        this.direction = direction;
    }

    /**
     * @return The cell
     */
    @Pure
    public C cell() {
        return cell;
    }

    /**
     * @return The direction
     */
    @Pure
    public Direction direction() {
        return direction;
    }

    @Override
    public String toString() {
        return "{" + cell + ", " + direction + "}";
    }
}
