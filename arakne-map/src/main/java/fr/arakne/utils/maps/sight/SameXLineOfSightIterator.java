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

import java.util.Iterator;

/**
 * Line of sight iterator for cells with same X coordinate
 */
final class SameXLineOfSightIterator<C extends BattlefieldCell> implements Iterator<C> {
    private final BattlefieldSight<C> battlefield;
    private final CoordinateCell<C> source;
    private final CoordinateCell<C> target;
    private final int yDirection;

    private int currentY;

    public SameXLineOfSightIterator(BattlefieldSight<C> battlefield, CoordinateCell<C> source, CoordinateCell<C> target) {
        this.battlefield = battlefield;
        this.source = source;
        this.target = target;

        yDirection = source.y() > target.y() ? -1 : 1;
        currentY = source.y();
    }

    @Override
    public boolean hasNext() {
        return target.y() != currentY;
    }

    @Override
    public C next() {
        currentY += yDirection;

        return getCurrentCell();
    }

    /**
     * @return The cell on the current coordinates
     */
    private C getCurrentCell() {
        return battlefield.getCellByCoordinates(source.x(), currentY);
    }
}
