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
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Iterator;

/**
 * Iterate between cells of the line of sight of two not aligned cells
 *
 * @param <C> The cell type
 */
final class NotAlignedLineOfSightIterator<C extends @NonNull BattlefieldCell> implements Iterator<C> {
    private final BattlefieldSight<C> battlefield;
    private final CoordinateCell<C> source;
    private final CoordinateCell<C> target;

    private final int xDirection;
    private final int yDirection;
    private final double ySlope;
    private final double yAtZero;

    private int currentX;
    private int currentY;

    private int lastY;
    private int nextY;

    public NotAlignedLineOfSightIterator(BattlefieldSight<C> battlefield, CoordinateCell<C> source, CoordinateCell<C> target) {
        this.battlefield = battlefield;
        this.source = source;
        this.target = target;

        this.xDirection = source.x() > target.x() ? -1 : 1;
        this.yDirection = source.y() > target.y() ? -1 : 1;
        this.ySlope = (double) (target.y() - source.y()) / (double) (target.x() - source.x());
        this.yAtZero = source.y() - ySlope * source.x();

        init();
    }

    @Override
    public boolean hasNext() {
        return !target.is(currentX, currentY);
    }

    @Override
    public C next() {
        currentY += yDirection;

        // lastY is exceeded : increment the X value
        if (currentY * yDirection > lastY * yDirection) {
            currentX += xDirection;
            currentY = nextY;

            computeNextY();
        }

        return getCurrentCell();
    }

    /**
     * Initialise iterator
     */
    private void init() {
        currentX = source.x();
        currentY = source.y();

        computeNextY();
    }

    /**
     * Compute the target Y coordinate for the current X value
     * The value lastY is used as ending boundary for the Y coordinate iteration
     * The value nextY is used as start boundary for the Y coordinate iteration for the next X value
     */
    private void computeNextY() {
        // yMax is the value of Y at the current X
        // + 0.5 permit to ignore cells around the line of sight on diagonal (i.e. ySlope = 1)
        final double yAtCurrentX = (currentX + xDirection * 0.5) * ySlope + yAtZero;

        if (yDirection > 0) {
            nextY = (int) Math.round(yAtCurrentX);
            lastY = (int) Math.ceil(yAtCurrentX - 0.5);
        } else {
            nextY = (int) Math.ceil(yAtCurrentX - 0.5);
            lastY = (int) Math.round(yAtCurrentX);
        }
    }

    /**
     * @return The cell on the current coordinates
     */
    private C getCurrentCell() {
        return battlefield.getCellByCoordinates(currentX, currentY);
    }
}
