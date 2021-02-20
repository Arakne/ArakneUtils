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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Handle key for Dofus packets
 */
public final class Key {
    private static SecureRandom random;

    private final String key;
    private XorCipher cipher;

    public Key(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    /**
     * Get the cipher related to this key
     * The cipher instance is saved into the key
     *
     * @return The cipher instance
     */
    public XorCipher cipher() {
        if (cipher == null) {
            cipher = new XorCipher(key);
        }

        return cipher;
    }

    /**
     * Encode the key to hexadecimal string
     *
     * @return The encoded key
     * @see Key#parse(String) For parse the encoded key
     */
    public String encode() {
        final String raw;

        try {
            raw = URLEncoder.encode(key, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Invalid UTF-8 key", e);
        }

        final StringBuilder encrypted = new StringBuilder(raw.length() * 2);

        for (int i = 0; i < raw.length(); ++i) {
            final char c = raw.charAt(i);

            if (c < 16) {
                encrypted.append('0');
            }

            encrypted.append(Integer.toHexString(c));
        }

        return encrypted.toString();
    }

    /**
     * Parse an hexadecimal key string
     *
     * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Aks.as#L232
     *
     * @param input Key to parse
     *
     * @return The key instance
     *
     * @throws IllegalArgumentException When invalid key is given
     * @throws NumberFormatException When invalid hexadecimal string is given
     *
     * @see Key#encode() For generate the hexadecimal string
     */
    public static Key parse(String input) {
        if (input.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid key");
        }

        final char[] keyArr = new char[input.length() / 2];

        for (int i = 0; i < input.length(); i += 2) {
            keyArr[i / 2] = (char) Integer.parseInt(input.substring(i, i + 2), 16);
        }

        try {
            return new Key(URLDecoder.decode(new String(keyArr), StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid UTF-8 character");
        }
    }

    /**
     * Generate a new random key, with length of 128 characters
     * The generated key contains only displayable characters
     *
     * @return The generated key
     */
    public static Key generate() {
        return generate(128);
    }

    /**
     * Generate a new random key
     * The generated key contains only displayable characters
     *
     * @param size The key size
     *
     * @return The generated key
     */
    public static Key generate(int size) {
        if (random == null) {
            random = new SecureRandom();
        }

        return generate(size, random);
    }

    /**
     * Generate a new random key
     * The generated key contains only displayable characters
     *
     * @param size The key size
     * @param random The random number generator
     *
     * @return The generated key
     */
    public static Key generate(int size, SecureRandom random) {
        final char[] keyArr = new char[size];

        for (int i = 0; i < size; ++i) {
            keyArr[i] = (char) (random.nextInt(127 - 32) + 32);
        }

        return new Key(new String(keyArr));
    }
}
