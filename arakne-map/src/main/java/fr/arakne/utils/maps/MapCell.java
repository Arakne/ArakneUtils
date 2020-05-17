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

/**
 * Base type for a dofus map cell
 */
public interface MapCell {
    /**
     * Get the cell id
     *
     * @return The cell id. Starts at 0
     */
    public int id();

    /**
     * Check if the cell is walkable
     *
     * @return true if walkable
     */
    public boolean walkable();

    /**
     * Get the container map
     *
     * @return The parent DofusMap
     */
    public DofusMap<?> map();
}
