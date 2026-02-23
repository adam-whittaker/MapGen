package com.mason.mapgen.structures.iterators;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnIteratorTest{

    @Test
    void iteratesDownSpecifiedColumn() {
        Integer[][] grid = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };

        ColumnIterator<Integer> it = new ColumnIterator<>(grid, 1); // middle column

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // Column 1 is: 1, 4, 7
        assertEquals(List.of(1, 4, 7), seen);
    }

    @Test
    void hasNextIsFalseAfterLastRow() {
        Integer[][] grid = {
                {10, 11},
                {20, 21}
        };

        ColumnIterator<Integer> it = new ColumnIterator<>(grid, 0);

        assertTrue(it.hasNext());
        assertEquals(10, it.next());

        assertTrue(it.hasNext());
        assertEquals(20, it.next());

        assertFalse(it.hasNext(), "No more elements after reaching height");
    }

    @Test
    void worksWithSingleRowGrid() {
        Integer[][] grid = {
                {42, 43, 44}
        };

        ColumnIterator<Integer> it = new ColumnIterator<>(grid, 2);

        assertTrue(it.hasNext());
        assertEquals(44, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void throwsIfColumnIndexNegative() {
        Integer[][] grid = {
                {0, 1},
                {2, 3}
        };

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> new ColumnIterator<>(grid, -1)
        );
        assertEquals("Column out of bounds", ex.getMessage());
    }

    @Test
    void throwsIfColumnIndexTooLarge() {
        Integer[][] grid = {
                {0, 1},
                {2, 3}
        };

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> new ColumnIterator<>(grid, 2) // valid indices: 0,1
        );
        assertEquals("Column out of bounds", ex.getMessage());
    }

}