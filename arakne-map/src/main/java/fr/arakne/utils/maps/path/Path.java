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

import fr.arakne.utils.maps.MapCell;
import org.checkerframework.checker.index.qual.LengthOf;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Path for dofus map
 * @param <C> The cell type
 */
public final class Path<C extends @NonNull MapCell> implements Collection<PathStep<C>> {
    private final Decoder<C> decoder;
    private final List<PathStep<C>> steps;

    /**
     * @param decoder The decoder instance
     * @param steps The list of steps
     */
    public Path(Decoder<C> decoder, List<PathStep<C>> steps) {
        this.decoder = decoder;
        this.steps = steps;
    }

    /**
     * Create an empty path
     *
     * @param decoder The decoder instance
     */
    public Path(Decoder<C> decoder) {
        this(decoder, new ArrayList<>());
    }

    /**
     * Get one step
     *
     * @param step The step number. The step zero is the current cell, The step (size - 1) is the last step (target)
     * @return The required step
     */
    @Pure
    public PathStep<C> get(int step) {
        return steps.get(step);
    }

    /**
     * Get the first step of the path
     *
     * @return The first step
     * @see Path#start() For get the start cell
     */
    @Pure
    public PathStep<C> first() {
        return steps.get(0);
    }

    /**
     * Get the last step
     *
     * @return The last step
     * @see Path#target() For get the last cell
     */
    @Pure
    public PathStep<C> last() {
        return steps.get(steps.size() - 1);
    }

    /**
     * Get the start cell
     *
     * @return The start cell
     */
    @Pure
    public C start() {
        return first().cell();
    }

    /**
     * Get the last cell
     *
     * @return The end cell
     */
    @Pure
    public C target() {
        return last().cell();
    }

    /**
     * Encode the path to string
     *
     * @return The encoded path
     */
    @SideEffectFree
    public String encode() {
        return decoder.encode(this);
    }

    /**
     * Encode the path to string, including the start cell
     *
     * @return The encoded path
     * @see Decoder#encodeWithStartCell(Path)
     */
    @SideEffectFree
    public String encodeWithStartCell() {
        return decoder.encodeWithStartCell(this);
    }

    /**
     * Keep the path steps while the condition is valid
     * The path will be stop when an invalid step is found
     *
     * @param condition The condition to apply on all steps. If returns false, the path will be stopped
     *
     * @return The new path instance
     */
    public Path<C> keepWhile(Predicate<PathStep<C>> condition) {
        final Path<C> newPath = new Path<>(decoder, new ArrayList<>(size()));

        for (PathStep<C> step : steps) {
            if (!condition.test(step)) {
                break;
            }

            newPath.add(step);
        }

        return newPath;
    }

    /**
     * Truncate the path, to the new size
     *
     * @param newSize The required size
     * @return The new path instance
     */
    public Path<C> truncate(int newSize) {
        if (newSize >= size()) {
            return this;
        }

        return new Path<>(decoder, steps.subList(0, newSize));
    }

    @Pure
    @Override
    public @LengthOf("this") int size() {
        return steps.size();
    }

    @Pure
    @Override
    public boolean isEmpty() {
        return steps.isEmpty();
    }

    @Pure
    @Override
    public boolean contains(Object o) {
        return steps.contains(o);
    }

    @Override
    public Iterator<PathStep<C>> iterator() {
        return steps.iterator();
    }

    @Override
    public Object[] toArray() {
        return steps.toArray();
    }

    @Override
    @SuppressWarnings({"return", "toarray.nullable.elements.not.newarray"})
    public <T> @PolyNull T[] toArray(@PolyNull T @NonNull[] a) {
        return steps.toArray(a);
    }

    @Override
    public boolean add(PathStep<C> step) {
        return steps.add(step);
    }

    @Override
    public boolean remove(Object o) {
        return steps.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return steps.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends PathStep<C>> c) {
        return steps.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return steps.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return steps.retainAll(c);
    }

    @Override
    public void clear() {
        steps.clear();
    }
}
