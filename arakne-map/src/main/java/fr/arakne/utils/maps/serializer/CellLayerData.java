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

/**
 * Data for a single cell layer
 */
public interface CellLayerData {
    /**
     * The object number on the cell
     *
     * @return int
     */
    public int number();

    /**
     * The rotation value of the sprite
     *
     * @return int
     */
    public int rotation();

    /**
     * Does the sprite has been flipped ?
     *
     * @return bool
     */
    public boolean flip();

    /**
     * Check if the layer is active (i.e. has an object)
     *
     * @return bool
     */
    default public boolean active() {
        return number() != 0;
    }
}
