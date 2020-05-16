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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base64Test {
    @Test
    void ordSuccess() {
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";

        for (int i = 0; i < charset.length(); ++i) {
            assertEquals(i, Base64.ord(charset.charAt(i)));
        }
    }

    @Test
    void ordInvalidChar() {
        assertThrows(IllegalArgumentException.class, () -> Base64.ord('#'));
    }

    @Test
    void encodeSingleChar() {
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";

        for (int i = 0; i < charset.length(); ++i) {
            assertEquals(Character.toString(charset.charAt(i)), Base64.encode(i, 1));
        }
    }

    @Test
    void encodeWithTwoChars() {
        assertEquals("cr", Base64.encode(145, 2));
    }

    @Test
    void encodeWithTooSmallNumberWillKeepLength() {
        assertEquals("aac", Base64.encode(2, 3));
    }

    @Test
    void encodeWithInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> Base64.encode(123, 0));
        assertThrows(IllegalArgumentException.class, () -> Base64.encode(123, 15));
    }

    @Test
    void chr() {
        assertEquals('a', Base64.chr(0));
        assertEquals('_', Base64.chr(63));
        assertEquals('c', Base64.chr(2));
    }

    @Test
    void chrMod() {
        assertEquals('a', Base64.chrMod(64));
        assertEquals('r', Base64.chrMod(145));
    }

    @Test
    void decodeWithOneChar() {
        assertEquals(0, Base64.decode("a"));
        assertEquals(2, Base64.decode("c"));
        assertEquals(63, Base64.decode("_"));
    }

    @Test
    void decode() {
        assertEquals(458, Base64.decode("hk"));
    }

    @Test
    void decodeEmptyString() {
        assertEquals(0, Base64.decode(""));
    }

    @Test
    void decodeStringTooLong() {
        assertThrows(IllegalArgumentException.class, () -> Base64.decode("aaaaaaaaaaaaaaaaa"));
    }

    @Test
    void decodeEncodeTwoChars() {
        assertEquals(741, Base64.decode(Base64.encode(741, 2)));
        assertEquals(951, Base64.decode(Base64.encode(951, 2)));
        assertEquals(325, Base64.decode(Base64.encode(325, 2)));
        assertEquals(769, Base64.decode(Base64.encode(769, 2)));
    }

    @Test
    void encodeDecodeBigNumbers() {
        assertEquals("_____w", Base64.encode(-42, 6));
        assertEquals(-42, Base64.decode(Base64.encode(-42, 6)));

        assertEquals("ai4qHh", Base64.encode(148965447, 6));
        assertEquals(148965447, Base64.decode(Base64.encode(148965447, 6)));
    }

    @Test
    void toBytes() {
        assertArrayEquals(new byte[] {}, Base64.toBytes(""));
        assertArrayEquals(new byte[] {2}, Base64.toBytes("c"));
        assertArrayEquals(new byte[] {2, 31}, Base64.toBytes("cF"));
    }

    @Test
    void encodeWithByteArray() {
        assertEquals("", Base64.encode(new byte[0]));
        assertEquals("cF", Base64.encode(new byte[] {2, 31}));
    }
}
