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

/**
 * Implementation of the xor cipher in Dofus 1
 * Note: This cipher is not secure, the key can be easily retrieved.
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Aks.as#L297
 */
public final class XorCipher {
    private final String key;

    public XorCipher(String key) {
        this.key = key;
    }

    /**
     * Get the key used by the cipher
     *
     * @return The key
     */
    public String key() {
        return key;
    }

    /**
     * Encrypt the value using using the current key
     * The encrypted value is an upper case hexadecimal string
     *
     * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Aks.as#L297
     *
     * @param value Value to encrypt
     * @param keyOffset Offset to use for the key
     *
     * @return Encrypted value
     */
    public String encrypt(String value, int keyOffset) {
        final String plain = escape(value);
        final StringBuilder encrypted = new StringBuilder(plain.length() * 2);

        for (int i = 0; i < plain.length(); ++i) {
            final char c = plain.charAt(i);
            final char k = key.charAt((i + keyOffset) % key.length());

            final char e = (char) (c ^ k);

            if (e < 16) {
                encrypted.append('0');
            }

            encrypted.append(Integer.toHexString(e));
        }

        return encrypted.toString().toUpperCase();
    }

    /**
     * Decrypt the value using the current key
     *
     * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Aks.as#L314
     *
     * @param value Value to decrypt. Must be a valid hexadecimal string
     * @param keyOffset Offset to use on the key. Must be the same used for encryption.
     *
     * @return The decrypted value
     *
     * @throws IllegalArgumentException When an invalid string is given
     * @throws NumberFormatException When an invalid hexadecimal string is given
     */
    public String decrypt(String value, int keyOffset) {
        if (value.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid encrypted value");
        }

        final char[] decrypted = new char[value.length() / 2];

        for (int i = 0; i < value.length(); i += 2) {
            final char k = key.charAt((i / 2 + keyOffset) % key.length());
            final char c = (char) Integer.parseInt(value.substring(i, i + 2), 16);

            decrypted[i / 2] = (char) (c ^ k);
        }

        try {
            return URLDecoder.decode(new String(decrypted), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid UTF-8 character", e);
        }
    }

    /**
     * Escape value using URL encode
     *
     * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Aks.as#L275
     */
    private static String escape(String value) {
        final StringBuilder escaped = new StringBuilder(value.length());

        try {
            for (int i = 0; i < value.length(); ++i) {
                final char c = value.charAt(i);

                if (c < 32 || c > 127 || c == '%' || c == '+') {
                    escaped.append(URLEncoder.encode(String.valueOf(c), StandardCharsets.UTF_8.toString()));
                } else {
                    escaped.append(c);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid UTF-8 character", e);
        }

        return escaped.toString();
    }
}
