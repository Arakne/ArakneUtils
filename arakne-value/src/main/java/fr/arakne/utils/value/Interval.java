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

package fr.arakne.utils.value;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Objects;
import java.util.function.IntUnaryOperator;

/**
 * Integer interval, min and max included
 * min and max can be equals
 *
 * Note: This is an immutable value object
 */
public final class Interval {
    private final @NonNegative int min;
    private final @NonNegative int max;

    /**
     * @param min Minimal value of the interval
     * @param max Maximal value of the interval
     *
     * @throws IllegalArgumentException When max is lower than min
     */
    public Interval(@NonNegative int min, @NonNegative int max) {
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
    @Pure
    public @NonNegative int min() {
        return min;
    }

    /**
     * The maximal value of the interval
     *
     * @return The max value
     */
    @Pure
    public @NonNegative int max() {
        return max;
    }

    /**
     * Compute the average value of the interface
     * The average is `min + max / 2`
     *
     * @return The average, in double
     */
    @Pure
    public double average() {
        return (min + max) / 2d;
    }

    /**
     * Compute the interval amplitude
     * The amplitude is the size of the interval (i.e. `max - min`)
     * If the interval is a singleton, the amplitude is 0
     *
     * @return The amplitude. Must be a positive integer
     */
    @Pure
    @SuppressWarnings("return") // max is always >= to min
    public @NonNegative int amplitude() {
        return max - min;
    }

    /**
     * Check if the value is contained into the interval
     * The interval is inclusive
     *
     * @param value The value to check
     *
     * @return true if value is in the interface
     */
    @Pure
    public boolean contains(int value) {
        return value >= min && value <= max;
    }

    /**
     * Check if the current interval is a singleton (i.e. `min == max`)
     *
     * @return true if it's a singleton
     */
    @Pure
    public boolean isSingleton() {
        return min == max;
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
    public Interval modify(int modifier) {
        if (modifier == 0 || (min == max && modifier < 0)) {
            return this;
        }

        return new Interval(min, Math.max(max + modifier, min));
    }

    /**
     * Apply value transformer to the interval boundaries
     * If the Interval is a singleton, the transformer will be applied only once
     * The transformer may change the boundary order (ex: if the transformer reverse values), the result Interval will be reordered
     *
     * @param transformer The boundary transformation process
     *
     * @return The new transformed interval
     */
    public Interval map(NonNegativeIntUnaryOperator transformer) {
        if (isSingleton()) {
            return Interval.of(transformer.applyAsInt(min));
        }

        return Interval.of(
            transformer.applyAsInt(min),
            transformer.applyAsInt(max)
        );
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Interval)) {
            return false;
        }

        final Interval other = (Interval) obj;

        return other.min == min && other.max == max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    /**
     * Create a singleton interval
     *
     * @param value The interval value
     *
     * @return The new Interval instance
     */
    public static Interval of(@NonNegative int value) {
        return new Interval(value, value);
    }

    /**
     * Create an interval with unordered boundaries
     * The two boundaries will be ordered to create a valid interval
     *
     * @param a First boundary of the interval
     * @param b Second boundary of the interval
     *
     * @return The new Interval instance
     */
    public static Interval of(@NonNegative int a, @NonNegative int b) {
        if (a > b) {
            return new Interval(b, a);
        }

        return new Interval(a, b);
    }

    /**
     * Duplicate of {@link IntUnaryOperator} but for NonNegative integer
     */
    @FunctionalInterface
    public interface NonNegativeIntUnaryOperator {
        /**
         * Applies this operator to the given operand.
         *
         * @param operand the operand
         * @return the operator result
         */
        public @NonNegative int applyAsInt(@NonNegative int operand);
    }
}
