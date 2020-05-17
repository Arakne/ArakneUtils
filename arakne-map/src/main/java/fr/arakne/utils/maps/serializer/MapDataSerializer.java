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
 * Handle serialization for map data
 */
public interface MapDataSerializer {
    /**
     * Parse serialized map data to cells
     *
     * @param mapData The raw map data
     * @return The deserialized cells
     *
     * @throws IllegalArgumentException When invalid mapData is given
     */
    public CellData[] deserialize(String mapData);

    /**
     * Serialize the cells to a string
     * The result value must be compatible with deserialize()
     * So, the code `serializer.serialize(serializer.deserialize(mapData)).equals(mapData)` must always return true
     *
     * @param cells Cells to serialize
     * @return The serialized cells
     */
    public String serialize(CellData[] cells);
}
