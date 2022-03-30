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

class KeyTest {
    @Test
    void generate() {
        assertEquals(128, Key.generate().toString().length());
        assertEquals(32, Key.generate(32).toString().length());
        assertNotEquals(Key.generate().toString(), Key.generate().toString());

        String generated = Key.generate(2048).toString();

        assertTrue(generated.matches("^[a-zA-Z0-9!\"#\\$%&'(=)*+,\\/:;<>?@\\\\\\[\\]\\^_`{}|~.-]+$"), "Invalid charset for key " + generated);
    }

    @Test
    void string() {
        assertEquals("my key", new Key("my key").toString());
    }

    @Test
    void encode() {
        assertEquals("6d792b6b6579", new Key("my key").encode());
        assertEquals("253235254333254239253430254333254130", new Key("%ù@à").encode());
    }

    @Test
    void parse() {
        assertEquals("my key", Key.parse("6d792b6b6579").toString());
        assertEquals("%ù@à", Key.parse("253235254333254239253430254333254130").toString());

        assertThrows(IllegalArgumentException.class, () -> Key.parse("invalid"));
        assertThrows(NumberFormatException.class, () -> Key.parse("not hexa"));
    }

    @Test
    void cipher() {
        Key key = new Key("my key");

        assertEquals("my key", key.cipher().key());
        assertSame(key.cipher(), key.cipher());
    }
}
