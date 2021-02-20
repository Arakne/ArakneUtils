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

/**
 * List of available character races
 */
public enum Race {
    NO_CLASS,
    FECA,
    OSAMODAS,
    ENUTROF,
    SRAM,
    XELOR,
    ECAFLIP,
    ENIRIPSA,
    IOP,
    CRA,
    SADIDA,
    SACRIEUR,
    PANDAWA;

    /**
     * Cache values
     */
    private static final Race[] values = values();

    /**
     * Get a character race by its id
     *
     * @param id The race id
     * @return The race item
     *
     * @throws IndexOutOfBoundsException When an invalid id is given
     */
    public static Race byId(int id) {
        if (id >= values.length || id == 0) {
            throw new IndexOutOfBoundsException("Invalid race " + id);
        }

        return values[id];
    }
}
