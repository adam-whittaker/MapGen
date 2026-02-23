package com.mason.mapgen.structures.iterators;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoordIteratorTest{

    @Test
    void oneByOneSizeYieldsSingleCoord() {
        Size size = new Size(1, 1);
        CoordIterator it = new CoordIterator(size);

        assertTrue(it.hasNext(), "Iterator should have one element for 1x1 size");

        Coord c = it.next();
        assertEquals(0, c.x());
        assertEquals(0, c.y());

        assertFalse(it.hasNext(), "Iterator should be exhausted after yielding (0,0)");
    }

    @Test
    void iteratesRowMajorForRectangularSize() {
        Size size = new Size(3, 2); // width=3, height=2
        CoordIterator it = new CoordIterator(size);

        List<Coord> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // Expect row-major: (0,0), (1,0), (2,0), (0,1), (1,1), (2,1)
        assertEquals(6, seen.size());

        assertEquals(0, seen.get(0).x());
        assertEquals(0, seen.get(0).y());

        assertEquals(1, seen.get(1).x());
        assertEquals(0, seen.get(1).y());

        assertEquals(2, seen.get(2).x());
        assertEquals(0, seen.get(2).y());

        assertEquals(0, seen.get(3).x());
        assertEquals(1, seen.get(3).y());

        assertEquals(1, seen.get(4).x());
        assertEquals(1, seen.get(4).y());

        assertEquals(2, seen.get(5).x());
        assertEquals(1, seen.get(5).y());
    }

    @Test
    void hasNextIsFalseAfterLastCoord() {
        Size size = new Size(2, 1); // width=2, height=1
        CoordIterator it = new CoordIterator(size);

        assertTrue(it.hasNext());
        Coord first = it.next();
        assertEquals(0, first.x());
        assertEquals(0, first.y());

        assertTrue(it.hasNext());
        Coord second = it.next();
        assertEquals(1, second.x());
        assertEquals(0, second.y());

        assertFalse(it.hasNext(), "No more coords after width*height elements");
    }

}