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

import org.checkerframework.dataflow.qual.Pure;

/**
 * Character gender
 */
public enum Gender {
    MALE,
    FEMALE;

    /**
     * Get Gender from string value
     *
     * @param value String to parse
     *
     * @return The Gender
     */
    @Pure
    public static Gender parse(String value) {
        if ("0".equals(value)) {
            return MALE;
        }

        return FEMALE;
    }
}
