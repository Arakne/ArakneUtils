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

import fr.arakne.utils.value.helper.RandomUtil;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.IntRange;
import org.checkerframework.dataflow.qual.Pure;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Colors value type
 * The colors are in 24 bits, so the value is in interval [0, 16777216[
 *
 * Note: This is an immutable value object
 */
public final class Colors {
    public static final Colors DEFAULT = new Colors(-1, -1, -1);
    public static final int MAX_COLOR_VALUE = 16777215;
    private static @MonotonicNonNull RandomUtil random;

    private final @IntRange(from = -1, to = MAX_COLOR_VALUE) int color1;
    private final @IntRange(from = -1, to = MAX_COLOR_VALUE) int color2;
    private final @IntRange(from = -1, to = MAX_COLOR_VALUE) int color3;

    public Colors(@IntRange(from = -1, to = MAX_COLOR_VALUE) int color1, @IntRange(from = -1, to = MAX_COLOR_VALUE) int color2, @IntRange(from = -1, to = MAX_COLOR_VALUE) int color3) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    /**
     * @return The first sprite color
     */
    @Pure
    public @IntRange(from = -1, to = MAX_COLOR_VALUE) int color1() {
        return color1;
    }

    /**
     * @return The second sprite color
     */
    @Pure
    public @IntRange(from = -1, to = MAX_COLOR_VALUE) int color2() {
        return color2;
    }

    /**
     * @return The third sprite color
     */
    @Pure
    public @IntRange(from = -1, to = MAX_COLOR_VALUE) int color3() {
        return color3;
    }

    /**
     * Get colors into an array
     *
     * Example:
     * <code>
     *     new Color(14, 87, 12).toArray(); // int[] {14, 87, 12}
     * </code>
     *
     * @return The array representation of the color
     */
    @Pure
    public @IntRange(from = -1, to = MAX_COLOR_VALUE) int[] toArray() {
        return new int[] {color1, color2, color3};
    }

    /**
     * Get colors as hex array
     * If color is default color (i.e. -1), "-1" will be generated
     *
     * Example:
     * <code>
     *     new Color(14, 87, 12).toArray(); // int[] {"e", "57", "c"}
     * </code>
     *
     * @return The array representation of the color, in hexadecimal string
     */
    @Pure
    public String[] toHexArray() {
        return hexStream().toArray(String[]::new);
    }

    /**
     * Format the colors to an hexadecimal CSV string
     * If color is default color (i.e. -1), "-1" will be generated
     *
     * Example:
     * <code>
     *     new Color(14, 87, 12).toHexString(";"); // "e;57;c"
     * </code>
     *
     * @param separator The separator to use between each colors
     * @return The hexadecimal CSV string
     */
    @Pure
    public String toHexString(CharSequence separator) {
        return hexStream().collect(Collectors.joining(separator));
    }

    /**
     * Create a new colors set with random values
     *
     * @return A new random Colors
     */
    public static Colors randomized() {
        if (random == null) {
            random = RandomUtil.createShared();
        }

        return randomized(random);
    }

    /**
     * Create a new colors set with random values
     *
     * @param random The random generator
     *
     * @return A new random Colors
     */
    @SuppressWarnings("argument") // nextInt bounds are not correctly resolved
    public static Colors randomized(Random random) {
        return new Colors(
            random.nextInt(MAX_COLOR_VALUE + 1),
            random.nextInt(MAX_COLOR_VALUE + 1),
            random.nextInt(MAX_COLOR_VALUE + 1)
        );
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Colors colors = (Colors) o;

        return color1 == colors.color1 && color2 == colors.color2 && color3 == colors.color3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color1, color2, color3);
    }

    @Override
    public String toString() {
        return "Colors(" + color1 + ", " + color2 + ", " + color3 + ')';
    }

    @Pure
    private Stream<String> hexStream() {
        return Arrays.stream(toArray()).mapToObj(value -> value == -1 ? "-1" : Integer.toHexString(value));
    }
}
