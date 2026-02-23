package com.mason.mapgen.structures.direction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompassDirectionTest{

    @Test
    void testCoordinates() {
        assertEquals(-1, CompassDirection.NW.x());
        assertEquals(-1, CompassDirection.NW.y());

        assertEquals(0, CompassDirection.N.x());
        assertEquals(-1, CompassDirection.N.y());

        assertEquals(1, CompassDirection.NE.x());
        assertEquals(-1, CompassDirection.NE.y());

        assertEquals(1, CompassDirection.E.x());
        assertEquals(0, CompassDirection.E.y());

        assertEquals(1, CompassDirection.SE.x());
        assertEquals(1, CompassDirection.SE.y());

        assertEquals(0, CompassDirection.S.x());
        assertEquals(1, CompassDirection.S.y());

        assertEquals(-1, CompassDirection.SW.x());
        assertEquals(1, CompassDirection.SW.y());

        assertEquals(-1, CompassDirection.W.x());
        assertEquals(0, CompassDirection.W.y());
    }

    @Test
    void testIndexMatchesOrdinal() {
        for (CompassDirection dir : CompassDirection.values()) {
            assertEquals(dir.ordinal(), dir.index(),
                    () -> "index() should match ordinal() for " + dir);
        }
    }

    @Test
    void testSizeIsConstantForAllValues() {
        int expectedSize = CompassDirection.values().length;
        assertEquals(8, expectedSize, "There should be 8 compass directions");

        for (CompassDirection dir : CompassDirection.values()) {
            assertEquals(expectedSize, dir.size(),
                    () -> "size() should be " + expectedSize + " for " + dir);
        }
    }

    @Test
    void testEnumOrderIsCorrect() {
        CompassDirection[] values = CompassDirection.values();
        assertArrayEquals(
                new CompassDirection[]{
                        CompassDirection.NW,
                        CompassDirection.N,
                        CompassDirection.NE,
                        CompassDirection.E,
                        CompassDirection.SE,
                        CompassDirection.S,
                        CompassDirection.SW,
                        CompassDirection.W
                },
                values
        );
    }

}
