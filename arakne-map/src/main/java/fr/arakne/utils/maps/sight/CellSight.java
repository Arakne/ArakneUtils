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

package fr.arakne.utils.maps.sight;

import fr.arakne.utils.maps.BattlefieldCell;
import fr.arakne.utils.maps.CoordinateCell;
import fr.arakne.utils.maps.DofusMap;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * Handle the line of sight from a given cell
 *
 * @param <C> The cell type
 */
public final class CellSight<C extends @NonNull BattlefieldCell> {
    private final BattlefieldSight<C> battlefield;
    private final CoordinateCell<C> source;

    public CellSight(BattlefieldSight<C> battlefield, CoordinateCell<C> source) {
        this.battlefield = battlefield;
        this.source = source;
    }

    public CellSight(CoordinateCell<C> source) {
        this(new BattlefieldSight<>(source.cell().map()), source);
    }

    /**
     * Check if the line of sight is free towards the target
     *
     * Usage:
     * <pre>{@code
     * final CellSight<FightCell> sight = fighter.cell().sight();
     *
     * if (!sight.isFree(target.cell().coordinate())) {
     *     throw new Exception("Target is no reachable");
     * }
     * }</pre>
     *
     * @param target The target cell
     *
     * @return true if line sight of sight is free
     */
    public boolean isFree(CoordinateCell<C> target) {
        if (source.equals(target)) {
            return true;
        }

        final Iterator<C> sight = to(target);

        while (sight.hasNext()) {
            final C cell = sight.next();

            if (cell.sightBlocking() && !cell.equals(target.cell())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the line of sight is free towards the target
     *
     * Usage:
     * <pre>{@code
     * final CellSight<FightCell> sight = fighter.cell().sight();
     *
     * if (!sight.isFree(target.cell())) {
     *     throw new Exception("Target is no reachable");
     * }
     * }</pre>
     *
     * @param target The target cell
     * @return true if line sight of sight is free
     */
    public boolean isFree(C target) {
        return isFree(target.coordinate());
    }

    /**
     * Iterator of cells between current one and the target
     * Note: the current cell is excluded from the iterator, but not the target. If the source and the target are same cells, an empty iterator is returned
     *
     * Usage:
     * <pre>{@code
     * // Iterate over all cells between fighter.cell() and target.cell()
     * fighter.cell().sight().to(target.cell()).forEachRemaining(cell -> {
     *     // cell is on the line of sight
     * });
     * }</pre>
     *
     * @param target The target cell
     *
     * @return The cells iterator
     */
    public Iterator<C> to(C target) {
        return to(target.coordinate());
    }

    /**
     * Iterator of cells between current one and the target
     * Note: the current cell is excluded from the iterator, but not the target. If the source and the target are same cells, an empty iterator is returned
     *
     * Usage:
     * <pre>{@code
     * // Iterate over all cells between fighter.cell() and target.cell()
     * fighter.cell().sight().to(target.cell()).forEachRemaining(cell -> {
     *     // cell is on the line of sight
     * });
     * }</pre>
     *
     * @param target The target cell
     *
     * @return The cells iterator
     */
    public Iterator<C> to(CoordinateCell<C> target) {
        if (source.equals(target)) {
            return Collections.emptyIterator();
        }

        return source.x() == target.x()
            ? new SameXLineOfSightIterator<>(battlefield, source, target)
            : new NotAlignedLineOfSightIterator<>(battlefield, source, target)
        ;
    }

    /**
     * Iterate over all map cells, and check for each cells is the line of sight is free
     *
     * <pre>{@code
     * final CellSight<FightCell> sight = fighter.cell().sight();
     * final CoordinateCell<FightCell> to = new CoordinateCell<>(target.cell());
     *
     * sight.forEach((cell, free) -> {
     *     if (free) {
     *         performActionOnAccessibleCell(cell);
     *     }
     * });
     * }</pre>
     *
     * @param cellViewConsumer The cell consumer. Takes as first parameter the cell and at second the sight status (true if free)
     *
     * @see CellSight#accessible() For dump accessible cells
     * @see CellSight#blocked() For dump blocked cells
     */
    public void forEach(BiConsumer<C, Boolean> cellViewConsumer) {
        final DofusMap<C> map = source.cell().map();
        final int size = map.size();

        for (int id = 0; id < size; ++id) {
            final C target = map.get(id);

            cellViewConsumer.accept(target, isFree(target));
        }
    }

    /**
     * @return Get all accessible cells from the current cell
     */
    public Collection<C> accessible() {
        final Collection<C> cells = new ArrayList<>();

        forEach((cell, free) -> {
            if (free) {
                cells.add(cell);
            }
        });

        return cells;
    }

    /**
     * @return Get all blocked cells from the current cell
     */
    public Collection<C> blocked() {
        final Collection<C> cells = new ArrayList<>();

        forEach((cell, free) -> {
            if (!free) {
                cells.add(cell);
            }
        });

        return cells;
    }
}
