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

package fr.arakne.utils.maps._test;

import fr.arakne.utils.maps.AbstractCellDataAdapter;
import fr.arakne.utils.maps.BattlefieldCell;
import fr.arakne.utils.maps.serializer.CellData;
import org.checkerframework.checker.index.qual.NonNegative;

public class MyDofusCell extends AbstractCellDataAdapter<MyDofusMap, MyDofusCell> implements BattlefieldCell<MyDofusCell> {
    public MyDofusCell(MyDofusMap map, CellData data, @NonNegative int id) {
        super(map, data, id);
    }

    @Override
    public boolean sightBlocking() {
        return !data.lineOfSight();
    }
}
