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
public final class LineOfSight<C extends BattlefieldCell> {
    private final DofusMap<C> battlefield;
    private final int width; // store map width for optimisation

    public LineOfSight(DofusMap<C> battlefield) {
        this.battlefield = battlefield;
        this.width = battlefield.dimensions().width();
    }

    /**
     * Check the line of sight between those two cells
     *
     * @param source The source cell
     * @param target The target cell
     *
     * @return true if target is not blocked
     */
    public boolean between(C source, C target) {
        return between(new CoordinateCell<>(source), new CoordinateCell<>(target));
    }

    /**
     * Check the line of sight between those two cells
     *
     * @param source The source cell
     * @param target The target cell
     *
     * @return true if target is not blocked
     */
    public boolean between(CoordinateCell<C> source, CoordinateCell<C> target) {
        // Swap source and target to ensure that source.x < target.x
        if (source.x() > target.x()) {
            return between(target, source);
        }

        if (source.equals(target)) {
            return true;
        }

        if (source.x() == target.x()) {
            return checkWithSameX(source, target);
        }

        return checkNotAlignedCells(source, target);
    }

    /**
     * Check the line of sight value for a single cell by its coordinates
     *
     * @return true if the cell block the line of sight
     */
    private boolean cellSightBlocking(int x, int y) {
        // https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Pathfinding.as#L550
        final int cellId = x * width + y * (width - 1);

        // Cell outside the battlefield
        if (cellId >= battlefield.size() || cellId < 0) {
            return false;
        }

        return battlefield.get(cellId).sightBlocking();
    }

    /**
     * Check line of sight with aligned cells
     */
    private boolean checkWithSameX(CoordinateCell<C> source, CoordinateCell<C> target) {
        return checkWithSameX(target.x(), source.y(), target.y());
    }

    /**
     * Check line of sight with aligned cells coordinates
     */
    private boolean checkWithSameX(int x, int y1, int y2) {
        final int startY = Math.min(y1, y2);
        final int endY = Math.max(y1, y2);

        // Ignore the first and last cells
        for (int currentY = startY + 1; currentY < endY; ++currentY) {
            if (cellSightBlocking(x, currentY)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check line of between two not aligned cells
     *
     * @param source The source cell. source.x() must be lower that target.x()
     * @param target The target cell
     *
     * @return true if the line of sight is free
     */
    private boolean checkNotAlignedCells(CoordinateCell<C> source, CoordinateCell<C> target) {
        final int yDirection = source.y() > target.y() ? -1 : 1;
        final double ySlope = (double) (target.y() - source.y()) / (double) (target.x() - source.x());
        final double yAtZero = source.y() - ySlope * source.x();

        int currentY = source.y();

        // For every X between source and target
        for (int currentX = source.x(); currentX <= target.x(); ++currentX) {
            // yMax is the value of Y at the current X
            final int yMax = (int) Math.round((currentX + 0.5) * ySlope + yAtZero);

            for (;;) {
                // target is reached : do not check it's LoS
                if (target.is(currentX, currentY)) {
                    return true;
                }

                // Ignore the source LoS
                if (!source.is(currentX, currentY) && cellSightBlocking(currentX, currentY)) {
                    return false;
                }

                // Increment Y until yMax is reached
                if (currentY == yMax) {
                    break;
                }

                currentY += yDirection;
            }
        }

        // target X == current X : increments Y until target is reached
        return checkWithSameX(target.x(), currentY, target.y());
    }
}
