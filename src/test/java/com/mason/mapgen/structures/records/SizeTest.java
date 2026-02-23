package com.mason.mapgen.structures.records;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Rect;
import com.mason.libgui.utils.structures.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeTest{

    @Test
    void toRectCreatesRectAtOriginWithSameDimensions() {
        Size size = new Size(5, 7);
        Rect rect = size.toRect();

        assertEquals(0, rect.x());
        assertEquals(0, rect.y());
        assertEquals(5, rect.width());
        assertEquals(7, rect.height());
    }

    @Test
    void withinBoundsMatchesRectFromOrigin() {
        Size size = new Size(4, 3);
        // Logical region: x ∈ [0,4), y ∈ [0,3)

        // Inside
        assertTrue(size.withinBounds(new Coord(0, 0)));
        assertTrue(size.withinBounds(new Coord(3, 2)));

        // On right/bottom edges: out of bounds
        assertFalse(size.withinBounds(new Coord(4, 0)));
        assertFalse(size.withinBounds(new Coord(0, 3)));

        // Negative: out of bounds
        assertFalse(size.withinBounds(new Coord(-1, 0)));
        assertFalse(size.withinBounds(new Coord(0, -1)));
    }

}