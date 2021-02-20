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

package fr.arakne.utils.encoding;

/**
 * Implementation of the Dofus network checksum
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Aks.as#L248
 */
public final class Checksum {
    /**
     * Utility class : disable constructor
     */
    private Checksum() {}

    /**
     * Compute the checksum as integer
     * The returned value is in interval [0-16]
     *
     * @param value Value to compute
     *
     * @return The checksum of value
     */
    public static int integer(String value) {
        int checksum = 0;

        for (int i = 0; i < value.length(); ++i) {
            checksum += value.charAt(i) % 16;
        }

        return checksum % 16;
    }

    /**
     * Compute the checksum as hexadecimal string
     * The return value is a single hexadecimal char string
     *
     * @param value Value to compute
     *
     * @return The checksum of value
     */
    public static String hexadecimal(String value) {
        return Integer.toHexString(integer(value)).toUpperCase();
    }

    /**
     * Verify the checksum of the input value
     *
     * @param input The input value to check
     * @param expectedChecksum The expected checksum as int
     *
     * @return true if checksum match
     */
    public static boolean verify(String input, int expectedChecksum) {
        return integer(input) == expectedChecksum;
    }

    /**
     * Verify the checksum of the input value
     *
     * @param input The input value to check
     * @param expectedChecksum The expected checksum as hexadecimal string
     *
     * @return true if checksum match
     */
    public static boolean verify(String input, String expectedChecksum) {
        return integer(input) == Integer.parseInt(expectedChecksum, 16);
    }
}
