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

class XorCipherTest {
    @Test
    void key() {
        assertEquals("my key", new XorCipher("my key").key());
    }

    @Test
    void encrypt() {
        XorCipher cipher = new XorCipher("my key");

        assertEquals("251C4C070A593A16520701594C", cipher.encrypt("Hello World !", 0));
        assertEquals("251C4C070A59271648054558", cipher.encrypt("Hello John !", 0));
        assertEquals("271104091D003C0A0B03104844", new XorCipher("other key").encrypt("Hello World !", 0));
    }

    @Test
    void decrypt() {
        XorCipher cipher = new XorCipher("my key");

        assertThrows(IllegalArgumentException.class, () -> cipher.decrypt("invalid", 0));
        assertThrows(NumberFormatException.class, () -> cipher.decrypt("####", 0));

        assertEquals("Hello World !", cipher.decrypt("251C4C070A593A16520701594C", 0));
        assertEquals("Hello John !", cipher.decrypt("251C4C070A59271648054558", 0));

        assertNotEquals("Hello World !", new XorCipher("other key").decrypt("251C4C070A593A16520701594C", 0));
    }

    @Test
    void keyOffset() {
        XorCipher c1 = new XorCipher("abcd");
        XorCipher c2 = new XorCipher("cdab");

        assertEquals(c1.encrypt("Hello World !", 2), c2.encrypt("Hello World !", 0));
        assertEquals("Hello World !", c2.decrypt(c1.encrypt("Hello World !", 2), 0));
    }

    @Test
    void encryptDecrypt() {
        XorCipher cipher = new XorCipher("my key");

        String[] strings = new String[] {
            "Hello World !",
            "%%%%",
            "",
            "éà@_9èè£$ø*µ+",
        };

        for (String s : strings) {
            assertEquals(s, cipher.decrypt(cipher.encrypt(s, 0), 0));
        }
    }
}
