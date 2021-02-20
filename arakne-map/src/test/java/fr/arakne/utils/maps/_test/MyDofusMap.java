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

import fr.arakne.utils.maps.DofusMap;
import fr.arakne.utils.maps.serializer.CellData;
import fr.arakne.utils.maps.serializer.DefaultMapDataSerializer;
import fr.arakne.utils.value.Dimensions;

public class MyDofusMap implements DofusMap<MyDofusCell> {
    private final CellData[] cells;

    public MyDofusMap(CellData[] cells) {
        this.cells = cells;
    }

    @Override
    public int size() {
        return cells.length;
    }

    @Override
    public MyDofusCell get(int id) {
        return new MyDofusCell(this, cells[id], id);
    }

    @Override
    public Dimensions dimensions() {
        return new Dimensions(15, 17);
    }

    public static MyDofusMap parse(String mapData) {
        return new MyDofusMap(new DefaultMapDataSerializer().deserialize(mapData));
    }
}
