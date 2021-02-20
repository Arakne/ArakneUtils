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

package fr.arakne.utils.value;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {
    @Test
    void badConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Interval(5, 2));
    }

    @Test
    void contains() {
        Interval interval = new Interval(5, 10);

        assertTrue(interval.contains(5));
        assertTrue(interval.contains(10));
        assertTrue(interval.contains(7));

        assertFalse(interval.contains(2));
        assertFalse(interval.contains(11));
    }

    @Test
    void average() {
        assertEquals(12.5, new Interval(10, 15).average(), 0.001);
    }

    @Test
    void amplitude() {
        assertEquals(5, new Interval(10, 15).amplitude());
        assertEquals(0, new Interval(10, 10).amplitude());
        assertEquals(20, new Interval(0, 20).amplitude());
        assertEquals(15, new Interval(-20, -5).amplitude());
    }

    @Test
    void isSingleton() {
        assertTrue(new Interval(5, 5).isSingleton());
        assertFalse(new Interval(5, 6).isSingleton());
    }

    @Test
    void modify() {
        Interval interval = new Interval(5, 10);

        assertSame(interval, interval.modify(0));
        assertEquals(new Interval(5, 15), interval.modify(5));
        assertEquals(new Interval(5, 7), interval.modify(-3));
        assertEquals(new Interval(5, 5), interval.modify(-10));

        interval = new Interval(5, 5);

        assertSame(interval, interval.modify(-2));
        assertEquals(new Interval(5, 7), interval.modify(2));
    }

    @Test
    void mapWithSingletonShouldCallOnceTransformer() {
        AtomicInteger ai = new AtomicInteger();

        Interval res = Interval.of(42).map(i -> {
            ai.incrementAndGet();

            return i * 2 + 2;
        });

        assertEquals(1, ai.get());
        assertTrue(res.isSingleton());
        assertEquals(86, res.max());
    }

    @Test
    void map() {
        AtomicInteger ai = new AtomicInteger();

        Interval res = Interval.of(10, 20).map(i -> {
            ai.incrementAndGet();

            return i * 2 + 2;
        });

        assertEquals(2, ai.get());
        assertEquals(new Interval(22, 42), res);
    }

    @Test
    void mapShouldReorderTransformedBoundaries() {
        Interval res = Interval.of(10, 20).map(i -> -i);

        assertEquals(new Interval(-20, -10), res);
    }

    @Test
    void string() {
        assertEquals("[5, 7]", new Interval(5, 7).toString());
    }

    @Test
    void equals() {
        assertEquals(
            new Interval(5, 7),
            new Interval(5, 7)
        );

        assertNotEquals(
            new Interval(5, 7),
            new Interval(5, 6)
        );

        assertNotEquals(
            new Interval(4, 7),
            new Interval(5, 7)
        );

        assertNotEquals(
            new Interval(4, 7),
            new Object()
        );
    }

    @Test
    void hash() {
        assertEquals(new Interval(5, 7).hashCode(), new Interval(5, 7).hashCode());
        assertNotEquals(new Interval(5, 7).hashCode(), new Interval(4, 7).hashCode());
    }

    @Test
    void ofSingleton() {
        assertEquals(new Interval(42, 42), Interval.of(42));
        assertTrue(Interval.of(42).isSingleton());
    }

    @Test
    void of() {
        assertEquals(new Interval(42, 42), Interval.of(42, 42));
        assertEquals(new Interval(11, 42), Interval.of(11, 42));
        assertEquals(new Interval(42, 55), Interval.of(55, 42));
    }
}
