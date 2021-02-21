/*
 *
 *  * This file is part of ArakneUtils.
 *  *
 *  * ArakneUtils is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * ArakneUtils is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with ArakneUtils.  If not, see <https://www.gnu.org/licenses/>.
 *  *
 *  * Copyright (c) 2017-2020 Vincent Quatrevieux
 *
 *
 */

package fr.arakne.utils.encoding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {
    @Test
    void encodeSimple() {
        PasswordEncoder encoder = new PasswordEncoder("my super secure key");

        assertEquals("0W_-MJ648321Q05YMH62SOQQ7e50RP", encoder.encode("secure_password"));
        assertEquals("Z8a9MO5483", encoder.encode("other"));
    }

    @Test
    void encodeTooLongPassword() {
        assertThrows(IllegalArgumentException.class, () -> new PasswordEncoder("key").encode("too long"));
    }

    @Test
    void decodeSimple() {
        PasswordEncoder encoder = new PasswordEncoder("my super secure key");

        assertEquals("secure_password", encoder.decode("0W_-MJ648321Q05YMH62SOQQ7e50RP"));
        assertEquals("other", encoder.decode("Z8a9MO5483"));
    }

    @Test
    void decodeWithSpecialChars() {
        String data = "é#ç@à²";

        PasswordEncoder encoder = new PasswordEncoder("azertyuiop");
        String encoded = encoder.encode(data);

        assertEquals(data, encoder.decode(encoded));
    }

    @Test
    void decodeWithComplexKey() {
        String data = "my_secret_data";

        PasswordEncoder encoder = new PasswordEncoder("é#ç@à²{ùø*µ°~§a/_.");
        String encoded = encoder.encode(data);

        assertEquals(data, encoder.decode(encoded));
    }

    @Test
    void decodeInvalidData() {
        PasswordEncoder encoder = new PasswordEncoder("my super secure key");

        assertThrows(IllegalArgumentException.class, () -> encoder.decode("aaa"));
        assertThrows(IllegalArgumentException.class, () -> encoder.decode("not base64"));
        assertThrows(IllegalArgumentException.class, () -> encoder.decode("a_string_too_long_for_the_given_key_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    void encodeDecode() {
        PasswordEncoder encoder = new PasswordEncoder("my super secure key");

        assertEquals("", encoder.decode(encoder.encode("")));
        assertEquals("aaa", encoder.decode(encoder.encode("aaa")));
        assertEquals("Duis blandit id", encoder.decode(encoder.encode("Duis blandit id")));

        encoder = new PasswordEncoder("a");
        assertEquals("a", encoder.decode(encoder.encode("a")));
    }

    @Test
    void key() {
        assertEquals("my super secure key", new PasswordEncoder("my super secure key").key());
    }
}
