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
 * Utility class for Dofus Pseudo base 64
 */
final public class Base64 {
    final static private char[] CHARSET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};

    /**
     * Disable constructor
     */
    private Base64() {}

    /**
     * Get int value of base64 char
     *
     * Example:
     * <code>
     *     Base64.ord('d'); // 3
     *     Base64.ord(Base64.chr(3)) == 3;
     * </code>
     *
     * @param c Char to convert
     * @return The int value, between 0 and 63 included
     *
     * @throws IllegalArgumentException When a character outside the charset is given
     * @see Base64#ord(char) For perform the opposite operation
     * @see Base64#decode(String) For decode an int string
     */
    static public int ord(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        }

        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 26;
        }

        if (c >= '0' && c <= '9') {
            return c - '0' + 52;
        }

        switch (c) {
            case '-':
                return 62;
            case '_':
                return 63;
            default:
                throw new IllegalArgumentException("Invalid char value");
        }
    }

    /**
     * Get the base 64 character for the value
     *
     * Example:
     * <code>
     *     Base64.chr(3); // 'd'
     *     Base64.chr(Base64.ord('B')) == 'B';
     * </code>
     *
     * @param value Value to encode. Must be in interval [0, 63]
     * @return Encoded value
     *
     * @see Base64#ord(char) For perform the opposite operation
     * @see Base64#encode(int, int) For encode a 32 bits integer to a string
     */
    static public char chr(int value) {
        return CHARSET[value];
    }

    /**
     * Get the base 64 character for the value,
     * but after applying a modulo on the value to ensure that the call will not fail
     *
     * @param value Value to encode.
     * @return Encoded value
     */
    static public char chrMod(int value) {
        return CHARSET[value % CHARSET.length];
    }

    /**
     * Encode an int value to pseudo base 64
     *
     * Example:
     * <code>
     *     Base64.encode(145, 2); // "cr"
     *     Base64.encode(2, 3); // "aac"
     * </code>
     *
     * @param value Value to encode
     * @param length The expected result length. Must be between 1 and 6 included
     *
     * @return The encoded value. The returned string length will be exactly the same as length parameter
     *
     * @throws IllegalArgumentException When the length parameter is invalid
     */
    static public String encode(int value, int length) {
        if (length < 1 || length > 6) {
            throw new IllegalArgumentException("Invalid length parameter : it must be in range [1-6]");
        }

        char[] encoded = new char[length];

        for (int i = length - 1; i >= 0; --i) {
            encoded[i] = CHARSET[value & 63];
            value >>= 6;
        }

        return new String(encoded);
    }

    /**
     * Encode a byte array to Base64 string
     *
     * Example:
     * <code>
     *     Base64.encode(new byte[] {2, 31}); // "cF"
     * </code>
     *
     * @param data Data to encode
     *
     * @return The encoded value. The result length is the same as the data size
     *
     * @see Base64#toBytes(String) For the opposite operation
     */
    static public String encode(byte[] data) {
        final char[] encoded = new char[data.length];

        for (int i = 0; i < data.length; ++i) {
            encoded[i] = chr(data[i]);
        }

        return new String(encoded);
    }

    /**
     * Decode pseudo base64 value to int
     *
     * Example:
     * <code>
     *     Base64.decode("c"); // 2
     *     Base64.decode("hk"); // 458
     * </code>
     *
     * @param encoded The encoded value
     * @return The decoded value
     *
     * @throws IllegalArgumentException When an invalid string is given
     */
    static public int decode(String encoded) {
        if (encoded.length() > 6) {
            throw new IllegalArgumentException("Invalid encoded string : too long");
        }

        int value = 0;

        for (int i = 0; i < encoded.length(); ++i) {
            value <<= 6;
            value += ord(encoded.charAt(i));
        }

        return value;
    }

    /**
     * Decode a Base 64 string to a byte array
     * Each byte will represents the {@link Base64#ord(char)} value of each characters
     *
     * Example:
     * <code>
     *     Base64.toBytes("cF"); // new byte[] {2, 31}
     * </code>
     *
     * @param encoded The encoded string
     *
     * @return The decode byte array. The array size will be the same as the string length
     */
    static public byte[] toBytes(String encoded) {
        byte[] decoded = new byte[encoded.length()];

        for (int i = 0; i < encoded.length(); ++i) {
            decoded[i] = (byte) Base64.ord(encoded.charAt(i));
        }

        return decoded;
    }
}
