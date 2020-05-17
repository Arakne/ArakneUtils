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
 * Cell layer data for the interactive object, or "object2"
 */
public interface InteractiveObjectData extends CellLayerData {
    /**
     * Rotation is not supported by the layerObject2
     */
    @Override
    default public int rotation() {
        return 0;
    }

    /**
     * Does the object is interactive ?
     *
     * @return true if the layer contains an interactive object
     */
    public boolean interactive();
}
