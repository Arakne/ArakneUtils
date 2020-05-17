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

package fr.arakne.utils.value;

import java.util.Objects;

/**
 * Dimensions for 2D object
 */
final public class Dimensions {
    final private int width;
    final private int height;

    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get the width
     *
     * @return The width
     */
    public int width() {
        return width;
    }

    /**
     * Get the height
     *
     * @return The height
     */
    public int height() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Dimensions other = (Dimensions) o;

        return other.height == height && other.width == width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Dimensions(" + width + "x" + height + ')';
    }
}
