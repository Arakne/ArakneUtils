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

package fr.arakne.utils.value.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {
    @Test
    @SuppressWarnings("argument") // allow testing byId(0)
    void byId() {
        assertSame(Race.ECAFLIP, Race.byId(6));
        assertSame(Race.PANDAWA, Race.byId(12));
        assertThrows(IndexOutOfBoundsException.class, () -> Race.byId(42));
        assertThrows(IndexOutOfBoundsException.class, () -> Race.byId(13));
        assertThrows(IndexOutOfBoundsException.class, () -> Race.byId(0));
    }
}
