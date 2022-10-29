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

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

/**
 * Base type for a dofus map cell
 * Usage :
 * <pre>{@code
 * // Note: the template parameter should be the used domain interface or class
 * interface MyMapCell extends MapCell<MapCell> {
 *     public void myCustomOperation();
 * }
 * }</pre>
 *
 * @param <C> Should be the cell class it-self
 */
public interface MapCell<C extends @NonNull MapCell> {
    /**
     * Get the cell id
     *
     * @return The cell id. Starts at 0
     */
    @Pure
    public @NonNegative int id();

    /**
     * Check if the cell is walkable
     *
     * @return true if walkable
     */
    @Pure
    public boolean walkable();

    /**
     * Get the container map
     *
     * @return The parent DofusMap
     */
    @Pure
    public DofusMap<C> map();

    /**
     * Get the coordinate of the current cell
     * This is equivalent to {@code new CoordinateCell<>(cell)}
     *
     * Note: this method will always recreate a new {@link CoordinateCell} instance
     *
     * <pre>{@code
     * // Compute distance between two cells
     * int distance = current.coordinate().distance(target.coordinate());
     * }</pre>
     *
     * You can optimise {@link CoordinateCell} creation by storing them into the cell instance,
     * optionally wrapped into a {@link java.lang.ref.WeakReference} :
     *
     * <pre>{@code
     * class MyCell extends MapCell<MyCell> {
     *     // ...
     *
     *     private CoordinateCell<MyCell> coordinate;
     *
     *     public CoordinateCell<MyCell> coordinate() {
     *         if (coordinate == null) {
     *             coordinate = new CoordinateCell<>(this);
     *         }
     *
     *         return coordinate;
     *     }
     * }
     * }</pre>
     *
     * @return The cell coordinate
     */
    @SuppressWarnings("unchecked")
    public default CoordinateCell<C> coordinate() {
        return new CoordinateCell<>((C) this);
    }
}
