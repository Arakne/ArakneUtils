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
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;

/**
 * Base implementation of MapCell by adapting a {@link CellData}
 *
 * @param <M> The DofusMap class
 * @param <C> Should be the cell class it-self
 */
public abstract class AbstractCellDataAdapter<M extends @NonNull DofusMap, C extends @NonNull MapCell> implements MapCell<C> {
    protected final CellData data;
    private final M map;
    private final @NonNegative int id;

    /**
     * @param map The container map
     * @param data The cell data
     * @param id The cell id
     */
    public AbstractCellDataAdapter(M map, CellData data, @NonNegative int id) {
        this.map = map;
        this.data = data;
        this.id = id;
    }

    @Pure
    @Override
    public final @NonNegative int id() {
        return id;
    }

    @Pure
    @Override
    public final boolean walkable() {
        return data.active() && data.movement().walkable();
    }

    @Pure
    @Override
    public final M map() {
        return map;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractCellDataAdapter<?, ?> that = (AbstractCellDataAdapter<?, ?>) o;

        return id == that.id && map == that.map;
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, id);
    }
}
