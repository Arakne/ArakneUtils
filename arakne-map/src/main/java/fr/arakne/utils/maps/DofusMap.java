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

import fr.arakne.utils.value.Dimensions;
import org.checkerframework.checker.index.qual.IndexFor;
import org.checkerframework.checker.index.qual.LengthOf;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Pure;

/**
 * Base dofus map type
 *
 * @param <C> The cell type
 */
public interface DofusMap<C extends @NonNull MapCell> {
    /**
     * Get the map size (the number of cells)
     *
     * @return The map size
     */
    @Pure
    public @LengthOf("this") int size();

    /**
     * Get a cell by its id
     *
     * @param id The cell id
     *
     * @return The cell
     */
    public C get(@IndexFor("this") int id);

    /**
     * Get the map dimensions
     *
     * @return The dimensions
     */
    @Pure
    public Dimensions dimensions();
}
