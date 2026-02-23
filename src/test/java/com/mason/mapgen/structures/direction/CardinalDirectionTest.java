package com.mason.mapgen.structures.direction;

import com.mason.mapgen.structures.direction.CardinalDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardinalDirectionTest{

    @Test
    void testCoordinates() {
        Assertions.assertEquals(0, CardinalDirection.N.x());
        assertEquals(-1, CardinalDirection.N.y());

        assertEquals(1, CardinalDirection.E.x());
        assertEquals(0, CardinalDirection.E.y());

        assertEquals(0, CardinalDirection.S.x());
        assertEquals(1, CardinalDirection.S.y());

        assertEquals(-1, CardinalDirection.W.x());
        assertEquals(0, CardinalDirection.W.y());
    }

    @Test
    void testIndexMatchesOrdinal() {
        for (CardinalDirection dir : CardinalDirection.values()) {
            assertEquals(dir.ordinal(), dir.index(),
                    () -> "index() should match ordinal() for " + dir);
        }
    }

    @Test
    void testSizeIsConstantForAllValues() {
        int expectedSize = CardinalDirection.values().length;
        assertEquals(4, expectedSize, "There should be 4 cardinal directions");

        for (CardinalDirection dir : CardinalDirection.values()) {
            assertEquals(expectedSize, dir.size(),
                    () -> "size() should be " + expectedSize + " for " + dir);
        }
    }

    @Test
    void testEnumOrderIsNESW() {
        CardinalDirection[] values = CardinalDirection.values();
        assertArrayEquals(
                new CardinalDirection[]{CardinalDirection.N, CardinalDirection.E,
                        CardinalDirection.S, CardinalDirection.W},
                values
        );
    }

}