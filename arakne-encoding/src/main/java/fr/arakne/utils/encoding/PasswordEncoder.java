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
 * The password encoding algorithm for Dofus
 * The algo is a pseudo base 64 vigenere cypher implementation
 *
 * Note: This is not a safe method for store or communicate password.
 *       You must use this algo only for compatibility with Dofus 1.
 *
 * Usage:
 * <code>
 *     PasswordEncoder encoder = new PasswordEncoder("my connection key");
 *
 *     encoder.encode("my password"); // Encode password for send to the server
 *     encoder.decode(encodedPassword); // Decode the password
 * </code>
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/ank/utils/Crypt.as#L20
 */
final public class PasswordEncoder {
    final private String key;

    /**
     * @param key The key to use. It must be long enough to encode the password.
     */
    public PasswordEncoder(String key) {
        this.key = key;
    }

    /**
     * Get the encoding key
     *
     * @return The key
     */
    public String key() {
        return key;
    }

    /**
     * Decode given string using key
     *
     * @param encoded Encoded string
     *
     * @return Decoded string, in Dofus base 64 format
     *
     * @throws IllegalArgumentException When the encoded string is invalid, or the key is too small
     */
    public String decode(String encoded) {
        if (encoded.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid encoded string");
        }

        if (key.length() * 2 < encoded.length()) {
            throw new IllegalArgumentException("Encoded string is too long for the key");
        }

        char[] decoded = new char[encoded.length() / 2];

        // Iterate over pair chars
        for (int i = 0; i < encoded.length(); i += 2) {
            int p = i / 2;
            int k = key.charAt(p) % 64; // Get key char

            // Get two chars int value (divider and modulo)
            int d = Base64.ord(encoded.charAt(i));
            int r = Base64.ord(encoded.charAt(i + 1));

            // Remove key value
            d -= k;
            r -= k;

            // if values are negative (due to modulo), reverse the module
            while (d < 0) { d += 64; }
            while (r < 0) { r += 64; }

            // retrieve the original value
            int v = d * 16 + r;

            decoded[p] = (char) v;
        }

        return new String(decoded);
    }

    /**
     * Encode the password using the key
     *
     * @param password The raw password
     * @return The encoded password
     *
     * @throws IllegalArgumentException When the password is too long
     */
    public String encode(String password) {
        if (key.length() < password.length()) {
            throw new IllegalArgumentException("The password is too long for the key");
        }

        char[] encoded = new char[password.length() * 2];

        for (int i = 0; i < password.length(); ++i) {
            // Password char and key
            char c = password.charAt(i);
            char k = key.charAt(i);

            // Get the divider and modulo values
            int d = c / 16;
            int r = c % 16;

            // Encode into base64
            encoded[i * 2] = Base64.chrMod(d + k);
            encoded[i * 2 + 1] = Base64.chrMod(r + k);
        }

        return new String(encoded);
    }
}
