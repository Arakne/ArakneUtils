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
 * Integer interval, min and max included
 * min and max can be equals
 *
 * Note: This is an immutable value object
 */
final public class Interval {
    final private int min;
    final private int max;

    /**
     * @param min Minimal value of the interval
     * @param max Maximal value of the interval
     *
     * @throws IllegalArgumentException When max is lower than min
     */
    public Interval(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be higher than min");
        }

        this.min = min;
        this.max = max;
    }

    /**
     * The minimal value of the interval
     *
     * @return The min value
     */
    public int min() {
        return min;
    }

    /**
     * The maximal value of the interval
     *
     * @return The max value
     */
    public int max() {
        return max;
    }

    /**
     * Check if the value is contained into the interval
     * The interval is inclusive
     *
     * @param value The value to check
     *
     * @return true if value is in the interface
     */
    public boolean contains(int value) {
        return value >= min && value <= max;
    }

    /**
     * Modify the end of the interval
     * The returned interval will be [min, max + modifier]
     * If the new end is lower than the min (i.e. -modifier higher than max - min), the interval [min, min] will be returned
     *
     * @param modifier The modifier value. If positive will increase max, if negative will decrease
     *
     * @return The new interval
     */
    public Interval modify(int modifier)
    {
        if (modifier == 0 || (min == max && modifier < 0)) {
            return this;
        }

        return new Interval(min, Math.max(max + modifier, min));
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Interval)) {
            return false;
        }

        Interval other = (Interval) obj;

        return other.min == min && other.max == max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
