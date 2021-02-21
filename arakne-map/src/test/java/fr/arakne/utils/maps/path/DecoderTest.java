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

package fr.arakne.utils.maps.path;

import fr.arakne.utils.encoding.Base64;
import fr.arakne.utils.maps._test.MyDofusCell;
import fr.arakne.utils.maps._test.MyDofusMap;
import fr.arakne.utils.maps.constant.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DecoderTest {
    private MyDofusMap map;
    private Decoder<MyDofusCell> decoder;

    @BeforeEach
    void setUp() {
        map = MyDofusMap.parse("HhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaae7MaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaX8HhGae5QiaaGhaaeaaa7zHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaX7HhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeJgaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeJgaaaHhaaeJgaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaae7MaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeHlaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaa5iHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeJga5iHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa7MHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaae5ma7AHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeJga7HHhGaeaaaaaHhGae5UaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaX7HhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaa5jHhGaeaaaaaHhqaeaaa_4HhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaa7RHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa5jHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaae5qiaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaae5maaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaa");
        decoder = new Decoder<>(map);
    }

    @Test
    void nextCellByDirection() throws PathException {
        assertEquals(map.get(101), decoder.nextCellByDirection(map.get(100), Direction.EAST).get());
        assertEquals(map.get(115), decoder.nextCellByDirection(map.get(100), Direction.SOUTH_EAST).get());
        assertEquals(map.get(129), decoder.nextCellByDirection(map.get(100), Direction.SOUTH).get());
        assertEquals(map.get(114), decoder.nextCellByDirection(map.get(100), Direction.SOUTH_WEST).get());
        assertEquals(map.get(99), decoder.nextCellByDirection(map.get(100), Direction.WEST).get());
        assertEquals(map.get(85), decoder.nextCellByDirection(map.get(100), Direction.NORTH_WEST).get());
        assertEquals(map.get(71), decoder.nextCellByDirection(map.get(100), Direction.NORTH).get());
        assertEquals(map.get(86), decoder.nextCellByDirection(map.get(100), Direction.NORTH_EAST).get());
        assertEquals(map.get(0), decoder.nextCellByDirection(map.get(1), Direction.WEST).get());
    }

    @Test
    void nextCellByDirectionOutOfLimit() {
        assertFalse(decoder.nextCellByDirection(map.get(470), Direction.SOUTH).isPresent());
        assertFalse(decoder.nextCellByDirection(map.get(478), Direction.EAST).isPresent());
        assertFalse(decoder.nextCellByDirection(map.get(0), Direction.WEST).isPresent());
    }

    @Test
    void decodePathInvalidBadLength() {
        assertThrows(PathException.class, () -> decoder.decode("abcd", map.get(123)), "Invalid path : bad length");
    }

    @Test
    void decodePathBadDirection() {
        assertThrows(PathException.class, () -> decoder.decode("aaJ", map.get(100)), "Invalid path : bad direction");
    }

    @Test
    void decodePathCellNotFound() {
        assertThrows(PathException.class, () -> decoder.decode("aZZ", map.get(100)), "Invalid cell number");
        assertThrows(PathException.class, () -> decoder.decode("ebH", map.get(0)), "Invalid cell number");
        assertThrows(PathException.class, () -> decoder.decode("a__"), "Invalid cell number");
        assertThrows(PathException.class, () -> decoder.decode("ahF"), "Invalid cell number");
    }

    @Test
    void decodePathOneCell() throws PathException {
        Path path = decoder.decode("ebJ", map.get(100));

        assertEquals(2, path.size());
        assertEquals(map.get(100), path.get(0).cell());
        assertEquals(Direction.WEST, path.get(1).direction());
        assertEquals(map.get(99), path.get(1).cell());
    }

    @Test
    void decodePathRectilinear() throws PathException {
        Path path = decoder.decode("ebH", map.get(100));

        assertEquals(4, path.size());
        assertEquals(map.get(100), path.get(0).cell());
        assertEquals(map.get(99), path.get(1).cell());
        assertEquals(map.get(98), path.get(2).cell());
        assertEquals(map.get(97), path.get(3).cell());
    }

    @Test
    void encodePathOneCell() {
        assertEquals(
            "abKebJ",
            decoder.encode(
                new Path(
                    decoder,
                    Arrays.asList(
                        new PathStep(map.get(100), Direction.EAST),
                        new PathStep(map.get(99), Direction.WEST)
                    )
                )
            )
        );
    }

    @Test
    void encodeRectilinearPathWillCompress() {
        assertEquals(
            "abKebH",
            decoder.encode(
                new Path(
                    decoder,
                    Arrays.asList(
                        new PathStep(map.get(100), Direction.EAST),
                        new PathStep(map.get(99), Direction.WEST),
                        new PathStep(map.get(98), Direction.WEST),
                        new PathStep(map.get(97), Direction.WEST)
                    )
                )
            )
        );
    }

    @Test
    void encodeComplexPath() {
        assertEquals(
            "abKebIgbf",
            decoder.encode(
                new Path(
                    decoder,
                    Arrays.asList(
                        new PathStep(map.get(100), Direction.EAST),
                        new PathStep(map.get(99), Direction.WEST),
                        new PathStep(map.get(98), Direction.WEST),
                        new PathStep(map.get(69), Direction.NORTH)
                    )
                )
            )
        );
    }

    @Test
    void encodeWithStartCellComplexPath() {
        assertEquals(
            "abKebIgbf",
            decoder.encode(
                new Path(
                    decoder,
                    Arrays.asList(
                        new PathStep(map.get(100), Direction.EAST),
                        new PathStep(map.get(99), Direction.WEST),
                        new PathStep(map.get(98), Direction.WEST),
                        new PathStep(map.get(69), Direction.NORTH)
                    )
                )
            )
        );
    }

    @Test
    void encodeWithStartCellRectilinearPathWillNotCompressTheStartCellOnEast() {
        assertEquals(
            "abKabN",
            decoder.encodeWithStartCell(
                new Path(
                    decoder,
                    Arrays.asList(
                        new PathStep(map.get(100), Direction.EAST),
                        new PathStep(map.get(101), Direction.EAST),
                        new PathStep(map.get(102), Direction.EAST),
                        new PathStep(map.get(103), Direction.EAST)
                    )
                )
            )
        );
    }

    @Test
    void decodeComplex() throws PathException {
        Path path = decoder.decode("ebIgbf", map.get(100));

        assertEquals(4, path.size());
        assertEquals(map.get(100), path.get(0).cell());
        assertEquals(map.get(99), path.get(1).cell());
        assertEquals(map.get(98), path.get(2).cell());
        assertEquals(map.get(69), path.get(3).cell());
    }

    @Test
    void decodeWithoutStartCell() throws PathException {
        Path path = decoder.decode("abKebIgbf");

        assertEquals(4, path.size());
        assertEquals(map.get(100), path.get(0).cell());
        assertEquals(map.get(99), path.get(1).cell());
        assertEquals(map.get(98), path.get(2).cell());
        assertEquals(map.get(69), path.get(3).cell());
    }

    @Test
    void pathfinder() {
        assertNotNull(decoder.pathfinder());
    }
}
