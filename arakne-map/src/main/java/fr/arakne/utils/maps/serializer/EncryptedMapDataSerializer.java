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

package fr.arakne.utils.maps.serializer;

import fr.arakne.utils.encoding.Checksum;
import fr.arakne.utils.encoding.Key;

/**
 * Implementation of the map serializer for encrypted map data
 * Encrypted maps are maps with file name ending with X.swf (ex: 10340_0706131721X.swf)
 * and require a key sent by the server with the GDM packet
 *
 * <code>
 *     MapDataSerializer serializer = new EncryptedMapDataSerializer(Key.parse(gdm.key()));
 *     CellData[] cells = serializer.deserialize(encryptedMapData);
 * </code>
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/managers/MapsServersManager.as#L137
 */
public final class EncryptedMapDataSerializer implements MapDataSerializer {
    private final Key key;
    private final MapDataSerializer plainDataSerializer;

    public EncryptedMapDataSerializer(Key key) {
        this(key, new DefaultMapDataSerializer());
    }

    public EncryptedMapDataSerializer(Key key, MapDataSerializer plainDataSerializer) {
        this.key = key;
        this.plainDataSerializer = plainDataSerializer;
    }

    @Override
    public CellData[] deserialize(String mapData) {
        return plainDataSerializer.deserialize(key.cipher().decrypt(mapData, Checksum.integer(key.toString()) * 2));
    }

    @Override
    public String serialize(CellData[] cells) {
        return key.cipher().encrypt(plainDataSerializer.serialize(cells), Checksum.integer(key.toString()) * 2);
    }
}
