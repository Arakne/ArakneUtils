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

import fr.arakne.utils.encoding.Base64;
import fr.arakne.utils.encoding.Key;
import fr.arakne.utils.maps.constant.CellMovement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of the map data serializer, handling the plain (i.e. not crypted) map data format
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Compressor.as#L54
 */
public final class DefaultMapDataSerializer implements MapDataSerializer {
    private static final int CELL_DATA_LENGTH = 10;

    private Map<String, ByteArrayCell> cache;

    @Override
    public CellData[] deserialize(String mapData) {
        if (mapData.length() % CELL_DATA_LENGTH != 0) {
            throw new IllegalArgumentException("Invalid map data");
        }

        final int size = mapData.length() / CELL_DATA_LENGTH;
        final CellData[] cells = new CellData[size];

        for (int i = 0; i < size; ++i) {
            cells[i] = deserializeCell(mapData.substring(i * CELL_DATA_LENGTH, (i + 1) * CELL_DATA_LENGTH));
        }

        return cells;
    }

    @Override
    public String serialize(CellData[] cells) {
        final StringBuilder sb = new StringBuilder(cells.length * CELL_DATA_LENGTH);

        for (CellData cell : cells) {
            sb.append(serializeCell(cell));
        }

        return sb.toString();
    }

    /**
     * Enable the cell data cache
     * Once enable, deserialize two same cell data will return the same cell instance
     */
    public void enableCache() {
        cache = new ConcurrentHashMap<>();
    }

    /**
     * Disable cell data cache
     */
    public void disableCache() {
        cache = null;
    }

    /**
     * Get a MapDataSerializer for encrypted map with the given key
     * Use the current serializer as inner serializer (and also use the current cache)
     *
     * @param key The encryption key
     *
     * @return The map serializer
     */
    public MapDataSerializer withKey(Key key) {
        return new EncryptedMapDataSerializer(key, this);
    }

    /**
     * Get a MapDataSerializer for encrypted map with the given key
     * This is equivalent to `serializer.withKey(Key.parse(key));`
     *
     * <code>
     *     CellData[] cells = serializer.withKey(gdm.key()).deserialize(mapData);
     * </code>
     *
     * @param key The encryption key as string
     *
     * @return The map serializer
     */
    public MapDataSerializer withKey(String key) {
        return withKey(Key.parse(key));
    }

    private CellData deserializeCell(String cellData) {
        if (cache == null) {
            return new ByteArrayCell(Base64.toBytes(cellData));
        }

        return cache.computeIfAbsent(cellData, data -> new ByteArrayCell(Base64.toBytes(data)));
    }

    private String serializeCell(CellData cell) {
        final byte[] data = new byte[CELL_DATA_LENGTH];

        data[0] = (byte) ((cell.active() ? (1) : (0)) << 5);
        data[0] = (byte) (data[0] | (cell.lineOfSight() ? (1) : (0)));
        data[0] = (byte) (data[0] | (cell.ground().number() & 1536) >> 6);
        data[0] = (byte) (data[0] | (cell.layer1().number() & 8192) >> 11);
        data[0] = (byte) (data[0] | (cell.layer2().number() & 8192) >> 12);
        data[1] = (byte) ((cell.ground().rotation() & 3) << 4);
        data[1] = (byte) (data[1] | cell.ground().level() & 15);
        data[2] = (byte) ((cell.movement().ordinal() & 7) << 3);
        data[2] = (byte) (data[2] | cell.ground().number() >> 6 & 7);
        data[3] = (byte) (cell.ground().number() & 63);
        data[4] = (byte) ((cell.ground().slope() & 15) << 2);
        data[4] = (byte) (data[4] | (cell.ground().flip() ? (1) : (0)) << 1);
        data[4] = (byte) (data[4] | cell.layer1().number() >> 12 & 1);
        data[5] = (byte) (cell.layer1().number() >> 6 & 63);
        data[6] = (byte) (cell.layer1().number() & 63);
        data[7] = (byte) ((cell.layer1().rotation() & 3) << 4);
        data[7] = (byte) (data[7] | (cell.layer1().flip() ? (1) : (0)) << 3);
        data[7] = (byte) (data[7] | (cell.layer2().flip() ? (1) : (0)) << 2);
        data[7] = (byte) (data[7] | (cell.layer2().interactive() ? (1) : (0)) << 1);
        data[7] = (byte) (data[7] | cell.layer2().number() >> 12 & 1);
        data[8] = (byte) (cell.layer2().number() >> 6 & 63);
        data[9] = (byte) (cell.layer2().number() & 63);

        return Base64.encode(data);
    }

    private static class ByteArrayCell implements CellData {
        private final byte[] data;

        public ByteArrayCell(byte[] data) {
            this.data = data;
        }

        @Override
        public boolean lineOfSight() {
            return (data[0] & 1) == 1;
        }

        @Override
        public CellMovement movement() {
            return CellMovement.byValue((data[2] & 56) >> 3);
        }

        @Override
        public boolean active() {
            return (data[0] & 32) >> 5 == 1;
        }

        @Override
        public GroundCellData ground() {
            return new GroundCellData() {
                @Override
                public int level() {
                    return data[1] & 15;
                }

                @Override
                public int slope() {
                    return (data[4] & 60) >> 2;
                }

                @Override
                public int number() {
                    return ((data[0] & 24) << 6) + ((data[2] & 7) << 6) + data[3];
                }

                @Override
                public int rotation() {
                    return (data[1] & 48) >> 4;
                }

                @Override
                public boolean flip() {
                    return (data[4] & 2) >> 1 == 1;
                }
            };
        }

        @Override
        public CellLayerData layer1() {
            return new CellLayerData() {
                @Override
                public int number() {
                    return ((data[0] & 4) << 11) + ((data[4] & 1) << 12) + (data[5] << 6) + data[6];
                }

                @Override
                public int rotation() {
                    return (data[7] & 48) >> 4;
                }

                @Override
                public boolean flip() {
                    return (data[7] & 8) >> 3 == 1;
                }
            };
        }

        @Override
        public InteractiveObjectData layer2() {
            return new InteractiveObjectData() {
                @Override
                public boolean interactive() {
                    return (data[7] & 2) >> 1 == 1;
                }

                @Override
                public int number() {
                    return ((data[0] & 2) << 12) + ((data[7] & 1) << 12) + (data[8] << 6) + data[9];
                }

                @Override
                public boolean flip() {
                    return (data[7] & 4) >> 2 == 1;
                }
            };
        }
    }
}
