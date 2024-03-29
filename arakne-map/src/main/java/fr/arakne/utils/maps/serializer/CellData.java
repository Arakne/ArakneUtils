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

package fr.arakne.utils.maps.serializer;

import fr.arakne.utils.maps.constant.CellMovement;
import org.checkerframework.dataflow.qual.Pure;

/**
 * Data for a single map cell
 */
public interface CellData {
    /**
     * Check if the cell do not block the line of sight
     *
     * @return false if block line of sight
     */
    @Pure
    public boolean lineOfSight();

    /**
     * Get the permitted movement type
     * The value is an int in range [0 - 7]
     *
     * @return The movement value
     */
    @Pure
    public CellMovement movement();

    /**
     * Check if the cell is active or not
     *
     * @return true if the cell if active
     */
    @Pure
    public boolean active();

    /**
     * Get the ground layer
     *
     * @return The ground layer
     */
    public GroundCellData ground();

    /**
     * Get the first layer object
     *
     * @return The object1 layer
     */
    public CellLayerData layer1();

    /**
     * Get the second layer object
     * Can contains an interactive object
     *
     * @return The object2 layer
     */
    public InteractiveObjectData layer2();
}
