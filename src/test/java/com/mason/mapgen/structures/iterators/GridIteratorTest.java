package com.mason.mapgen.structures.iterators;

import com.mason.libgui.utils.structures.Size;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridIteratorTest{

    private static class TestGridIterator extends GridIterator<Integer> {

        TestGridIterator(Size size) {
            super(size);
        }

        @Override
        public Integer current() {
            // Use y*10 + x as an easily checkable pattern
            return y() * 10 + x();
        }
    }


    @Test
    void oneByOneGridYieldsSingleElement() {
        Size size = new Size(1, 1);
        GridIterator<Integer> it = new TestGridIterator(size);

        assertTrue(it.hasNext(), "Iterator should have one element");
        assertEquals(0, it.next(), "First (and only) element should be at (0,0)");
        assertFalse(it.hasNext(), "Iterator should be exhausted after one element");
    }

    @Test
    void iteratesRowMajorForRectangularGrid() {
        // width = 3, height = 2
        Size size = new Size(3, 2);
        GridIterator<Integer> it = new TestGridIterator(size);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // Expected: row 0: (0,0)=0, (1,0)=1, (2,0)=2
        //           row 1: (0,1)=10, (1,1)=11, (2,1)=12
        assertEquals(List.of(0, 1, 2, 10, 11, 12), seen);
    }

    @Test
    void hasNextIsFalseAfterLastElement() {
        Size size = new Size(2, 1);
        GridIterator<Integer> it = new TestGridIterator(size);

        assertTrue(it.hasNext());
        assertEquals(0, it.next());
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertFalse(it.hasNext(), "After consuming all elements, hasNext() must be false");
    }

    @Test
    void internalCoordinatesAdvanceAsExpected() {
        // This test double-checks x/y progression via the encoded pattern y*10 + x
        Size size = new Size(2, 2);
        GridIterator<Integer> it = new TestGridIterator(size);

        // (0,0)
        assertEquals(0, it.next());
        // (1,0)
        assertEquals(1, it.next());
        // (0,1) — x reset, y incremented
        assertEquals(10, it.next());
        // (1,1)
        assertEquals(11, it.next());
        assertFalse(it.hasNext());
    }

}