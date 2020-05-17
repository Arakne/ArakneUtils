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
import fr.arakne.utils.maps.DofusMap;
import fr.arakne.utils.maps.MapCell;
import fr.arakne.utils.maps.constant.Direction;

import java.util.Optional;

/**
 * Decode map data like paths or directions
 */
final public class Decoder<C extends MapCell> {
    final private DofusMap<C> map;

    /**
     * @param map The map to handle
     */
    public Decoder(DofusMap<C> map) {
        this.map = map;
    }

    /**
     * Get the immediately next cell if we move by the given direction
     * If the next cell is out of the map, an empty optional is returned
     *
     * @param start The start cell
     * @param direction The direction to follow
     *
     * @return The next cell, wrapped into an optional. If the next cell is outside map, return an empty optional
     */
    public Optional<C> nextCellByDirection(C start, Direction direction) {
        int nextId = start.id() + direction.nextCellIncrement(map.dimensions().width());

        if (nextId >= map.size() || nextId < 0) {
            return Optional.empty();
        }

        return Optional.of(map.get(nextId));
    }

    /**
     * Decode compressed path
     *
     * @param encoded The encoded path
     * @param start The start cell. Can be null.
     *              Set a value is the encoded path do not contains the start cell (ex: use {@link Decoder#encode(Path, boolean)} with includeStartCell)
     *
     * @return The path, with list of cells
     *
     * @throws PathException When an invalid path is given
     */
    public Path<C> decode(String encoded, C start) throws PathException {
        if (encoded.length() % 3 != 0) {
            throw new PathException("Invalid path : bad length");
        }

        final Path<C> path = new Path<>(this);

        // Add the start cell on the path
        if (start != null) {
            path.add(new PathStep<>(start, Direction.EAST));
        }

        for (int i = 0; i < encoded.length(); i += 3) {
            final Direction direction = Direction.byChar(encoded.charAt(i));
            final int cell = (Base64.ord(encoded.charAt(i + 1)) & 15) << 6 | Base64.ord(encoded.charAt(i + 2));

            if (cell >= map.size()) {
                throw new PathException("Invalid cell number");
            }

            // First cell of the path, without a start cell : add it to the path
            if (path.isEmpty()) {
                path.add(new PathStep<>(map.get(cell), Direction.EAST));
                continue;
            }

            expandRectilinearMove(path, path.target(), map.get(cell), direction);
        }

        return path;
    }

    /**
     * Decode compressed path without a start cell
     *
     * @param encoded The encoded path. Must contains the start cell

     * @return The path, with list of cells
     *
     * @throws PathException When an invalid path is given
     */
    public Path<C> decode(String encoded) throws PathException {
        return decode(encoded, null);
    }

    /**
     * Encode the computed path, including the start cell
     * To decode this path, the start cell should not be provided on the decode method
     *
     * This method should be used by server to send a path to the client
     *
     * <code>
     *     String encoded = encoder.encodeWithStartCell(myPath);
     *     Path decoded = encoder.decode(encoded);
     * </code>
     *
     * @param path The path to encode
     *
     * @return The encoded path
     */
    public String encodeWithStartCell(Path<C> path) {
        return encode(path, true);
    }

    /**
     * Encode the computed path, without the start cell
     * To decode this path, the start cell must be provided on the decode method
     *
     * This method should be used by the client to send a path to the server
     *
     * <code>
     *     String encoded = encoder.encode(myPath);
     *     Path decoded = encoder.decode(encoded, startCell);
     * </code>
     *
     * @param path The path to encode
     *
     * @return The encoded path
     *
     * @see Pathfinder#addFirstCell(boolean) Set to false to generate a path without the start cell
     */
    public String encode(Path<C> path) {
        return encode(path, false);
    }

    /**
     * Get the pathfinder related to this decoder
     *
     * @return The pathfinder instance
     */
    public Pathfinder<C> pathfinder() {
        return new Pathfinder<>(this);
    }

    private void expandRectilinearMove(Path<C> path, C start, C target, Direction direction) throws PathException {
        int stepsLimit =  2 * map.dimensions().width() + 1;

        while (!start.equals(target)) {
            start = nextCellByDirection(start, direction).orElseThrow(() -> new PathException("Invalid cell number"));
            path.add(new PathStep<>(start, direction));

            if (--stepsLimit < 0) {
                throw new PathException("Invalid path : bad direction");
            }
        }
    }

    /**
     * Encode the computed path
     *
     * @param path The path to encode
     * @param includeStartCell Does the encoded path should contains the start cell or not ?
     *
     * @return The encoded path
     */
    private String encode(Path<C> path, boolean includeStartCell) {
        final StringBuilder encoded = new StringBuilder(path.size() * 3);

        // The start cell must be added to path without compression
        if (includeStartCell) {
            encoded
                .append(Direction.EAST.toChar())
                .append(Base64.encode(path.get(0).cell().id(), 2))
            ;
        }

        // Start the path at step 1 : the start cell is added before
        for (int i = (includeStartCell ? 1 : 0); i < path.size(); ++i) {
            final PathStep<C> step = path.get(i);

            encoded.append(step.direction().toChar());

            // Skip cells on same direction
            while (i + 1 < path.size() && path.get(i + 1).direction() == step.direction()) {
                ++i;
            }

            encoded.append(Base64.encode(path.get(i).cell().id(), 2));
        }

        return encoded.toString();
    }
}
