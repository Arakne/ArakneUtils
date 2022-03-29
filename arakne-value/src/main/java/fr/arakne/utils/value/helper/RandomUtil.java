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

package fr.arakne.utils.value.helper;

import fr.arakne.utils.value.Interval;
import org.checkerframework.checker.index.qual.LessThan;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.common.value.qual.IntRange;
import org.checkerframework.common.value.qual.MinLen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Utility for random numbers
 *
 * This utility class must be used instead of the native {@link Random} class,
 * for create predictable random values on testing environment
 *
 * It's not recommended to share the RandomUtil instance between objects instances (i.e. store into a static field)
 * Each objects instance should have its own RandomUtil instance
 * But in case of short-life objects static instance of RandomUtil can be used when created using {@link RandomUtil#createShared()}
 */
public final class RandomUtil extends Random {
    /**
     * Testing mode ?
     * If set to true, the seed value will be fixed and random will be predictable
     */
    private static boolean testing = false;

    /**
     * List of instance which are shared between instances
     * This list is used for reset random seeds when enable testing mode
     *
     * @see RandomUtil#createShared()
     */
    private static final Collection<RandomUtil> sharedInstances = new ArrayList<>();

    public RandomUtil() {
        if (testing) {
            setSeed(0);
        }
    }

    /**
     * Get random number into [min, max] interval
     * The interval is inclusive
     *
     * If max if lower than min, min is returned
     *
     * @param min The minimal value, included
     * @param max The maximal value, included
     *
     * @return A random int
     */
    @SuppressWarnings("return")
    public @NonNegative @LessThan("#2 + 1") int rand(@NonNegative int min, @NonNegative int max) {
        if (max <= min) {
            return min;
        }

        if (min == 0) {
            return nextInt(max + 1);
        }

        return nextInt((max - min) + 1) + min;
    }

    /**
     * Get random number into given interval
     * The interval is inclusive
     *
     * This method is equivalent to `rand(interval.min(), interval.max())`
     *
     * @param interval The rand interval
     * @return A random int
     */
    public @NonNegative int rand(Interval interval) {
        return rand(interval.min(), interval.max());
    }

    /**
     * Get a random double between interval [0, max[
     *
     * @param max The maximum value (exclusive)
     * @return A random double
     */
    public double decimal(double max) {
        return nextDouble() * max;
    }

    /**
     * Get a random number from an interval
     *
     * @param interval An array of size 1 or 2. If the size is 1, return the array element, else return an array between interval[0] and interval[1] included
     * @return A random int
     */
    public @NonNegative int rand(@NonNegative int @MinLen(1) [] interval) {
        if (interval.length == 1 || interval[0] >= interval[1]) {
            return interval[0];
        }

        return rand(interval[0], interval[1]);
    }

    /**
     * Get a random boolean value
     *
     * @param percent Percent of chance that the returned value is true.
     *                If the value is 100, this method will always returns true
     *                If the value is 0, the method will always returns false
     *
     * @return A random boolean
     */
    public boolean bool(@IntRange(from = 0, to = 100) int percent) {
        return nextInt(100) < percent;
    }

    /**
     * Get a random boolean value
     * The returned value has same chance to be true or false
     *
     * This method is equivalent to `bool(50)`
     *
     * @return A random boolean
     * @see RandomUtil#bool(int)
     */
    public boolean bool() {
        return nextBoolean();
    }

    /**
     * Get a random boolean value following reverse chance
     * The return value if true with the probability of 1 / rate
     * Higher the rate value is, lower the probability is
     *
     * For rate = 2, the probability is 50% and its equivalent to call bool(50) or bool()
     * For rate = 100, the probability is 1% and its equivalent to call bool(1)
     *
     * @param rate The rate
     *
     * @return The random boolean value
     */
    public boolean reverseBool(@Positive int rate) {
        return nextInt(rate) == 0;
    }

    /**
     * Get a random value from an array of values
     * All elements has the same selection probability
     *
     * @param values The provided values
     * @param <T> The array element type
     *
     * @return One of the element of the array
     */
    public <T> T of(T @MinLen(1) [] values) {
        return values[nextInt(values.length)];
    }

    /**
     * Get a random value from an array of characters
     *
     * @param values The provided values
     *
     * @return One of the element of the array
     *
     * @see RandomUtil#of(Object[])
     */
    public char of(char @MinLen(1) [] values) {
        return values[nextInt(values.length)];
    }

    /**
     * Get a random value from a list of values
     * All elements has the same selection probability
     *
     * @param values The provided values
     * @param <T> The list element type
     *
     * @return One of the element of the list
     */
    public <T> T of(List<T> values) {
        return values.get(nextInt(values.size()));
    }

    /**
     * Randomize list element positions
     * This method will create a copy of the list, without modify the input parameter (unlike {@link Collections#shuffle(List)})
     *
     * @param list The list to randomize
     * @param <T> The element type
     *
     * @return The randomized list
     */
    public <T> List<T> shuffle(List<T> list) {
        final List<T> shuffled = new ArrayList<>(list);

        Collections.shuffle(shuffled, this);

        return shuffled;
    }

    /**
     * Create a new RandomUtil instance which is shared between instances (store in a static field)
     * This method allows the system to reset the random seed value when enable testing mode, for predictable random
     *
     * @return A new RandomUtil instance
     */
    public static RandomUtil createShared() {
        final RandomUtil random = new RandomUtil();

        sharedInstances.add(random);

        return random;
    }

    /**
     * Enable the testing mode for get predictable random values
     * This method MUST NOT be used outside tests
     */
    public static void enableTestingMode() {
        testing = true;

        // Reset the random state
        sharedInstances.forEach(randomUtil -> randomUtil.setSeed(0));
    }
}
