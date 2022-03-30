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

package fr.arakne.utils.maps.sight;

import fr.arakne.utils.maps.BattlefieldCell;
import fr.arakne.utils.maps.CoordinateCell;
import fr.arakne.utils.maps.DofusMap;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Utility class for compute line of sights
 * Note: The used algorithm is not exactly same as the client's one, some differences can occurs
 *
 * Algorithm :
 * - Get coordinates of the two cells
 * - "draw" a line between the cells :
 * - Compute the vector between those cells
 * - Creates the function : Y = ySlope * X + yAtZero with
 * - For each X between the cells compute the related Y using the function
 * - Increments Y cell by cell to reach the computed Y
 * - Check the cell line of sight value
 * - If the sight is blocked, return false
 *
 * @param <C> The battlefield cell type
 */
public final class BattlefieldSight<C extends @NonNull BattlefieldCell<C>> {
    private final DofusMap<C> battlefield;
    private final @Positive int width; // store map width for optimisation

    public BattlefieldSight(DofusMap<C> battlefield) {
        this.battlefield = battlefield;
        this.width = battlefield.dimensions().width();
    }

    /**
     * Check the line of sight between those two cells
     *
     * This is equivalent to {@code sight.from(source).isFree(target)}
     *
     * Usage:
     * <pre>{@code
     * final BattlefieldSight<FightCell> sight = new BattlefieldSight<>(map);
     *
     * if (!sight.between(fighter.cell(), target.cell())) {
     *     throw new Exception("Target is no reachable");
     * }
     * }</pre>
     *
     * @param source The source cell
     * @param target The target cell
     *
     * @return true if target is not blocked
     *
     * @see CellSight#isFree(BattlefieldCell)
     */
    public boolean between(C source, C target) {
        return between(source.coordinate(), target.coordinate());
    }

    /**
     * Check the line of sight between those two cells
     *
     * This is equivalent to {@code sight.from(source).isFree(target)}
     *
     * Usage:
     * <pre>{@code
     * final BattlefieldSight<FightCell> sight = new BattlefieldSight<>(map);
     * final CoordinateCell<FightCell> from = new CoordinateCell<>(fighter.cell());
     * final CoordinateCell<FightCell> to = new CoordinateCell<>(target.cell());
     *
     * if (!sight.between(from, to)) {
     *     throw new Exception("Target is no reachable");
     * }
     * }</pre>
     *
     * @param source The source cell
     * @param target The target cell
     *
     * @return true if target is not blocked
     *
     * @see CellSight#isFree(CoordinateCell)
     */
    public boolean between(CoordinateCell<C> source, CoordinateCell<C> target) {
        return from(source).isFree(target);
    }

    /**
     * Get the line of sight of a cell
     *
     * Usage:
     * <pre>{@code
     * final BattlefieldSight<FightCell> sight = new BattlefieldSight<>(map);
     * final CoordinateCell<FightCell> current = new CoordinateCell<>(fighter.cell());
     *
     * sight.from(current).to(target.cell()).forEachRemaining(cell -> {
     *     // Iterator on line of sight
     * });
     * }</pre>
     *
     * @param cell The cell to check
     *
     * @return The line of sight instance
     */
    public CellSight<C> from(CoordinateCell<C> cell) {
        return new CellSight<>(this, cell);
    }

    /**
     * Get the line of sight of a cell
     *
     * Usage:
     * <pre>{@code
     * final BattlefieldSight<FightCell> sight = new BattlefieldSight<>(map);
     *
     * sight.from(fighter.cell()).to(target.cell()).forEachRemaining(cell -> {
     *     // Iterator on line of sight
     * });
     * }</pre>
     *
     * @param cell The cell to check
     *
     * @return The line of sight instance
     */
    public CellSight<C> from(C cell) {
        return new CellSight<>(this, cell.coordinate());
    }

    /**
     * Get a cell by its coordinates
     */
    @SuppressWarnings("argument") // Consider x and y as safe
    C getCellByCoordinates(int x, int y) {
        // https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Pathfinding.as#L550
        return battlefield.get(x * width + y * (width - 1));
    }
}
