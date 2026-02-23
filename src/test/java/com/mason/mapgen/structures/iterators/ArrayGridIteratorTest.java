package com.mason.mapgen.structures.iterators;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayGridIteratorTest{

    @Test
    void iteratesRowMajorOverRectangularGrid() {
        Integer[][] grid = {
                {0, 1, 2},
                {3, 4, 5}
        };
        ArrayGridIterator<Integer> it = new ArrayGridIterator<>(grid);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // Row-major: (0,0)=0, (1,0)=1, (2,0)=2, (0,1)=3, (1,1)=4, (2,1)=5
        assertEquals(List.of(0, 1, 2, 3, 4, 5), seen);
        assertFalse(it.hasNext());
    }

    @Test
    void oneByOneGridYieldsSingleElement() {
        Integer[][] grid = {
                {42}
        };
        ArrayGridIterator<Integer> it = new ArrayGridIterator<>(grid);

        assertTrue(it.hasNext(), "Iterator should have one element");
        assertEquals(42, it.next());
        assertFalse(it.hasNext(), "Iterator should be exhausted after one element");
    }

    @Test
    void hasNextIsFalseAfterConsumingAllElements() {
        Integer[][] grid = {
                {10, 11},
                {20, 21}
        };
        ArrayGridIterator<Integer> it = new ArrayGridIterator<>(grid);

        assertTrue(it.hasNext());
        assertEquals(10, it.next());

        assertTrue(it.hasNext());
        assertEquals(11, it.next());

        assertTrue(it.hasNext());
        assertEquals(20, it.next());

        assertTrue(it.hasNext());
        assertEquals(21, it.next());

        assertFalse(it.hasNext(), "No more elements after width*height items");
    }

    @Test
    void worksWithSingleRowGrid() {
        Integer[][] grid = {
                {5, 6, 7}
        };
        ArrayGridIterator<Integer> it = new ArrayGridIterator<>(grid);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        assertEquals(List.of(5, 6, 7), seen);
    }

    @Test
    void worksWithSingleColumnGrid() {
        Integer[][] grid = {
                {1},
                {2},
                {3}
        };
        ArrayGridIterator<Integer> it = new ArrayGridIterator<>(grid);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // Row-major over a 3x1 is just 1,2,3 top-to-bottom
        assertEquals(List.of(1, 2, 3), seen);
    }

    @Test
    void emptyOuterArrayThrowsWhenConstructing() {
        Integer[][] empty = new Integer[0][0];

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> new ArrayGridIterator<>(empty),
                "ArrayGridIterator uses grid[0].length, so an empty outer array should fail fast"
        );
    }

}