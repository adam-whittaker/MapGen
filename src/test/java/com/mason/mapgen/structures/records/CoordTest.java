package com.mason.mapgen.structures.records;

import com.mason.libgui.utils.structures.Coord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordTest{

    @Test
    void midpointOfHorizontalSegmentIsAverageXSameY() {
        Coord a = new Coord(0, 10);
        Coord b = new Coord(4, 10);

        Coord mid = Coord.midpoint(a, b);
        assertEquals(2, mid.x());
        assertEquals(10, mid.y());
    }

    @Test
    void midpointOfVerticalSegmentIsAverageYSameX() {
        Coord a = new Coord(5, 0);
        Coord b = new Coord(5, 4);

        Coord mid = Coord.midpoint(a, b);
        assertEquals(5, mid.x());
        assertEquals(2, mid.y());
    }

    @Test
    void midpointUsesIntegerDivisionTruncatingTowardZero() {
        Coord a = new Coord(0, 0);
        Coord b = new Coord(1, 1);

        // (0+1)/2 = 0 in integer arithmetic
        Coord mid = Coord.midpoint(a, b);
        assertEquals(0, mid.x());
        assertEquals(0, mid.y());

        Coord c = new Coord(-1, -1);
        Coord d = new Coord(0, 0);

        // (-1+0)/2 = 0 in Java integer arithmetic (truncates toward zero)
        Coord mid2 = Coord.midpoint(c, d);
        assertEquals(0, mid2.x());
        assertEquals(0, mid2.y());
    }

    @Test
    void midpointIsSymmetricInArguments() {
        Coord a = new Coord(2, 7);
        Coord b = new Coord(8, 3);

        Coord m1 = Coord.midpoint(a, b);
        Coord m2 = Coord.midpoint(b, a);

        assertEquals(m1, m2);
    }

    @Test
    void midpointOfSamePointIsThatPoint() {
        Coord a = new Coord(3, 4);
        Coord mid = Coord.midpoint(a, a);

        assertEquals(a, mid);
    }

    @Test
    void recordEqualityWorksAsExpected() {
        Coord c1 = new Coord(1, 2);
        Coord c2 = new Coord(1, 2);
        Coord c3 = new Coord(2, 1);

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }

}