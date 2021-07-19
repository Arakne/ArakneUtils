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

import fr.arakne.utils.maps.CoordinateCell;
import fr.arakne.utils.maps.MapCell;
import fr.arakne.utils.maps.constant.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Find the shortest path between two cells
 *
 * <code>
 *     decoder
 *         .pathfinder()
 *         .targetDistance(2)
 *         .directions(Direction.values())
 *         .findPath(fighter.cell(), target)
 *     ;
 * </code>
 */
public final class Pathfinder<C extends MapCell<C>> {
    private final Decoder<C> decoder;

    /**
     * Minimal target distance to consider the path as reached
     *
     * @see Pathfinder#targetDistance(int)
     * @see Automaton#hasReachTarget()
     */
    private int targetDistance = 0;

    /**
     * Predicate for check if the cell is walkable
     */
    private Predicate<C> walkablePredicated = C::walkable;

    /**
     * Function for compute the cell cost
     */
    private Function<C, Integer> cellWeightFunction = cell -> 1;

    /**
     * Available movements directions
     */
    private Direction[] directions = Direction.restrictedDirections();

    /**
     * Maximum number of explored cells
     * Allow to fail when finding too complex path
     */
    private int exploredCellLimit = Integer.MAX_VALUE;

    /**
     * Does the first cell (source) should be added to the path ?
     */
    private boolean addFirstCell = true;

    public Pathfinder(Decoder<C> decoder) {
        this.decoder = decoder;
    }

    /**
     * Define the minimal target distance to consider the path as reached
     *
     * A distance of 0 means that the end of the path must be the target cell
     * A distance of 1 means that the end of the path must be an adjacent cell of the target
     *
     * By default the value is 0 (the end must be the target)
     *
     * @param distance The distance in number of cells
     *
     * @return this instance
     */
    public Pathfinder<C> targetDistance(int distance) {
        this.targetDistance = distance;

        return this;
    }

    /**
     * Define the predicate for check if the cell is walkable
     *
     * By default the predicate will call {@link MapCell#walkable()}
     *
     * @param predicate The predicate to use
     *
     * @return this instance
     */
    public Pathfinder<C> walkablePredicate(Predicate<C> predicate) {
        this.walkablePredicated = predicate;

        return this;
    }

    /**
     * Define the function for compute the cell cost
     * This function can add penalty on some cells. For example on adjacent cell of an enemy.
     *
     * By default the function always returns 1
     *
     * @param function The function to use
     *
     * @return this instance
     */
    public Pathfinder<C> cellWeightFunction(Function<C, Integer> function) {
        this.cellWeightFunction = function;

        return this;
    }

    /**
     * Define the available movements directions
     *
     * Note : Adding all directions will permit to move in diagonal,
     *        But the pathfinder is not optimal, because the distance is not computed using pythagoras distance
     *
     * By default, the direction are the restricted directions
     *
     * @param directions Allowed directions
     *
     * @return this instance
     *
     * @see Direction#restricted()
     */
    public Pathfinder<C> directions(Direction[] directions) {
        this.directions = directions;

        return this;
    }

    /**
     * Define the maximum number of cells to explore before fail
     *
     * A lower limit will fail faster, but do not permit complex path
     * The limit cannot be higher than walkable cells number
     *
     * @param limit The cells number. Must be a positive integer
     *
     * @return this instance
     */
    public Pathfinder<C> exploredCellLimit(int limit) {
        this.exploredCellLimit = limit;

        return this;
    }

    /**
     * Does the first cell (source) should be added to the path ?
     * The source cell should be added by path generated by the server, but it's not added by the client.
     *
     * @param addFirstCell true to add the first cell (default), or false to disable
     *
     * @return this instance
     */
    public Pathfinder<C> addFirstCell(boolean addFirstCell) {
        this.addFirstCell = addFirstCell;

        return this;
    }

    /**
     * Find the shortest path between source and target cells
     *
     * @param source The source (start) cell
     * @param target The target (end) cell
     *
     * @return The path, including source
     *
     * @throws PathException When cannot found any valid path
     */
    public Path<C> findPath(C source, C target) {
        final Automaton automaton = new Automaton(source, target);

        while (!automaton.hasReachTarget()) {
            automaton.pushPossibleMovements();
            automaton.move();
        }

        return new Path<>(decoder, automaton.buildPath());
    }

    /**
     * Path finding state
     */
    private class Automaton {
        /**
         * The start cell of the path
         */
        private final CoordinateCell<C> source;

        /**
         * The end cell of the path
         */
        private final CoordinateCell<C> target;

        /**
         * The current step where automaton is located
         */
        private Step current;

        /**
         * List of possible movements, sorted by the heuristic (distance + cost)
         * The head of this list is the step with the lowest cost and distance
         *
         * This list is also known as "openList" on A* algorithm
         *
         * @see Step#heuristic()
         */
        private final PriorityQueue<Step> movements = new PriorityQueue<>();

        /**
         * Store the best (i.e. lowest) Step for a given Cell.
         * The score is computed in the same way as {@link Automaton#movements}.
         *
         * This map is used to ensure that a better step is not present on available movements
         * when pushing all possible movements.
         *
         * This map is always synchronized with {@link Automaton#movements} :
         * any writes must be applied on both structures.
         */
        private final Map<C, Step> bestStepByCell = new HashMap<>();

        /**
         * Set of already explored steps
         * This set ensure that the pathfinder will not returns to an already explored cell
         * which can cause infinity loops
         *
         * This set is also known as "closedList" on A* algorithm
         */
        private final Set<C> explored = new HashSet<>();

        public Automaton(C source, C target) {
            this.source = source.coordinate();
            this.target = target.coordinate();

            this.current = new Step(this.source);
            explored.add(source);
        }

        /**
         * Push all possible movements from the current step
         *
         * Will push all walkable adjacent cells, which are not yet explored
         * The possible moves depends of the possible directions
         */
        public void pushPossibleMovements() {
            for (Direction direction : directions) {
                nextCellByDirection(direction)
                    .filter(cell -> !explored.contains(cell))
                    .filter(walkablePredicated)
                    .map(cell -> current.next(cell, direction))
                    .ifPresent(this::push)
                ;
            }
        }

        /**
         * Move the automaton (i.e. change the current step) to the best step
         * The selected step cell is added to explored cells
         *
         * @throws PathException When cannot found any valid movements
         */
        public void move() {
            if (movements.isEmpty()) {
                throw new PathException("Cannot find any valid path between " + source.cell().id() + " and " + target.cell().id());
            }

            if (explored.size() > exploredCellLimit) {
                throw new PathException("Limit exceeded for finding path");
            }

            current = movements.poll();

            final C cell = current.cell.cell();

            bestStepByCell.remove(cell);
            explored.add(cell);
        }

        /**
         * Check if the automaton has reach the target cell, or has reach the required minimal distance
         */
        public boolean hasReachTarget() {
            return current.distance <= targetDistance;
        }

        /**
         * Build the path from the current step
         */
        public List<PathStep<C>> buildPath() {
            final List<PathStep<C>> path = new ArrayList<>();

            // Build the path from the end
            for (Step step = current; step.previous != null; step = step.previous) {
                // Remove all steps after an unwalkable cell
                // Do not use the predicate, but the real walkable method,
                // to ensure that the real walkable state is used
                if (!step.cell.cell().walkable()) {
                    path.clear();
                    continue;
                }

                path.add(step.toPathStep());
            }

            // Always add the source cell even if not walkable
            if (addFirstCell) {
                path.add(new PathStep<>(source.cell(), Direction.EAST));
            }

            // The path is in reverse order (starts by the end)
            Collections.reverse(path);

            return path;
        }

        /**
         * Resolve the adjacent cell by a direction
         * This method can return a null object if the cell is out of the map
         */
        private Optional<C> nextCellByDirection(Direction direction) {
            return decoder.nextCellByDirection(current.cell.cell(), direction);
        }

        /**
         * Try to push a new step on possible movements
         * If a better step (i.e. with lower score) exists, the new step will be ignored
         *
         * @param step The new possible step to add
         */
        private void push(Step step) {
            final C cell = step.cell.cell();
            final Step bestStep = bestStepByCell.get(cell);

            // A step with a lower cost is found (do not compare distance because both steps have the same distance)
            if (bestStep != null && bestStep.cost <= step.cost) {
                return;
            }

            bestStepByCell.put(cell, step);
            movements.add(step);
        }

        /**
         * Step of the path
         *
         * The state contains the previous step (for building path), distance to target and its cost
         */
        private class Step implements Comparable<Step> {
            /**
             * The step cell
             */
            private final CoordinateCell<C> cell;

            /**
             * Direction used for reach this step
             */
            private final Direction direction;

            /**
             * Previous step
             * Can be null in case of the first step (with cell = source)
             */
            private final Step previous;

            /**
             * Distance to the target cell, in number of cells
             */
            private final int distance;

            /**
             * Cost of the step
             *
             * The cost is the sum of the previous step cost and the cell cost
             * The step cost is used to discourage usage of some cells (ex: cells near enemies, which can tackle),
             * and to store the total path cost to reach this step
             */
            private final int cost;

            /**
             * Creates the source cell
             * The cost is set to zero, and without previous step
             *
             * @param cell The source cell
             */
            public Step(CoordinateCell<C> cell) {
                this.cell = cell;
                this.previous = null;
                this.direction = Direction.EAST;
                this.cost = 0;

                distance = cell.distance(target);
            }

            /**
             * Creates a new step, following the current step
             *
             * @param cell The step cell
             * @param previous The current step
             * @param cost The step cell cost. Should be 1 for normal cell
             */
            private Step(CoordinateCell<C> cell, Direction direction, Step previous, int cost) {
                this.cell = cell;
                this.direction = direction;
                this.previous = previous;
                this.cost = previous.cost + cost;

                distance = cell.distance(target);
            }

            /**
             * Creates the next step for a given cell
             *
             * @param cell The step cell
             * @param direction The direction use to reach the cell
             */
            public Step next(C cell, Direction direction) {
                return new Step(cell.coordinate(), direction, this, cellWeightFunction.apply(cell));
            }

            /**
             * The step heuristic
             * Steps with lower heuristics are selected first
             *
             * The heuristic is the total steps costs (from the begin, to the current step) + the remaining distance
             */
            public int heuristic() {
                return distance + cost;
            }

            /**
             * Convert current pathfinder step to a PathStep
             */
            public PathStep<C> toPathStep() {
                return new PathStep<>(cell.cell(), direction);
            }

            @Override
            public int compareTo(Step o) {
                return heuristic() - o.heuristic();
            }
        }
    }
}
