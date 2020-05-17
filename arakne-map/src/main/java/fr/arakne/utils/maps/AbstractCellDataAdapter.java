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

import fr.arakne.utils.maps.serializer.CellData;

import java.util.Objects;

/**
 * Base implementation of MapCell by adapting a {@link CellData}
 *
 * @param <M> The DofusMap class
 */
abstract public class AbstractCellDataAdapter<M extends DofusMap<?>> implements MapCell {
    final protected CellData data;
    final private M map;
    final private int id;

    /**
     * @param map The container map
     * @param data The cell data
     * @param id The cell id
     */
    public AbstractCellDataAdapter(M map, CellData data, int id) {
        this.map = map;
        this.data = data;
        this.id = id;
    }

    @Override
    final public int id() {
        return id;
    }

    @Override
    final public boolean walkable() {
        return data.active() && data.movement().walkable();
    }

    @Override
    final public M map() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractCellDataAdapter<?> that = (AbstractCellDataAdapter<?>) o;

        return id == that.id && map == that.map;
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, id);
    }
}
