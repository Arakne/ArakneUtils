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
 * Copyright (c) 2017-2021 Vincent Quatrevieux
 */

package fr.arakne.utils.maps;

import fr.arakne.utils.maps.sight.CellSight;

/**
 * Base type for a battlefield cell
 */
public interface BattlefieldCell extends MapCell {
    /**
     * Check if the cell block line of sight
     *
     * @see CellSight
     * @see BattlefieldCell#sight()
     */
    public boolean sightBlocking();

    /**
     * Get the current cell sight
     * This method is equivalent to {@code new CellSight<>(cell)}
     *
     * Note: each call of this method will recreate a new {@link CellSight} instance
     *
     * <pre>{@code
     * // Check if the "target" cell is accessible from the current cell
     * if (!fighter.cell().sight().isFree(target)) {
     *     return false;
     * }
     * }</pre>
     *
     * @return The current cell sight
     */
    public default CellSight<BattlefieldCell> sight() {
        return new CellSight<>(new CoordinateCell<>(this));
    }
}
