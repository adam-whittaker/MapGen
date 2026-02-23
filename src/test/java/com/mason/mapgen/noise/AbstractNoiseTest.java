package com.mason.mapgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.noise.AbstractNoise;
import com.mason.mapgen.structures.grids.MutableGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractNoiseTest{

    private static final double EPS = 1e-9;

    /**
     * Simple concrete subclass for testing AbstractNoise.
     * It fills the grid with a deterministic pattern and counts how many times
     * generateNoiseSafely() is called.
     */
    private static class TestNoise extends AbstractNoise{

        int generateCalls = 0;

        TestNoise(Size size) {
            super(size);
        }

        @Override
        public void generateNoiseSafely() {
            generateCalls++;
            // Simple pattern: value = y * 10 + x
            for (Coord c : getGrid().coordIterable()) {
                getGrid().setValue(c, (double) (c.y() * 10 + c.x()));
            }
        }
    }

    @Test
    void constructorCreatesGridOfGivenSize() {
        Size size = new Size(4, 3);
        TestNoise noise = new TestNoise(size);

        MutableGrid<Double> grid = noise.getGrid();
        assertNotNull(grid);
        assertEquals(size, grid.getSize());
    }

    @Test
    void isNoiseGeneratedInitiallyFalseAndBecomesTrueAfterGenerate() {
        Size size = new Size(2, 2);
        TestNoise noise = new TestNoise(size);

        assertFalse(noise.isNoiseGenerated(), "Noise should not be generated initially");

        noise.generateNoise();

        assertTrue(noise.isNoiseGenerated(), "Noise flag should be true after generateNoise()");
    }

    @Test
    void generateNoiseCallsGenerateNoiseSafelyOnceAndFillsGrid() {
        Size size = new Size(2, 2);
        TestNoise noise = new TestNoise(size);
        MutableGrid<Double> grid = noise.getGrid();

        // Before generation: all values should be zero
        for (Coord c : grid.coordIterable()) {
            assertEquals(0, grid.getValue(c));
        }
        assertEquals(0, noise.generateCalls);

        noise.generateNoise();

        assertEquals(1, noise.generateCalls, "generateNoiseSafely() should be called exactly once");

        // After generation: all values should be non-null and match the pattern
        for (Coord c : grid.coordIterable()) {
            Double v = grid.getValue(c);
            assertNotNull(v, "Value should be set after generateNoise at " + c);
            assertEquals(c.y() * 10.0 + c.x(), v, EPS);
        }
    }

    @Test
    void secondCallToGenerateNoiseThrowsAndDoesNotCallGenerateNoiseSafelyAgain() {
        Size size = new Size(3, 3);
        TestNoise noise = new TestNoise(size);

        noise.generateNoise();
        assertEquals(1, noise.generateCalls);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                noise::generateNoise,
                "Second call to generateNoise() should throw"
        );
        assertEquals("Noise has already been generated!", ex.getMessage());

        // Ensure generateNoiseSafely was not called again
        assertEquals(1, noise.generateCalls, "generateNoiseSafely() must not be called on second generate");
    }

    @Test
    void createDoubleGridOfZerosCreatesGridWithCorrectSizeAndZeroValues() {
        Size size = new Size(3, 2);
        MutableGrid<Double> grid = AbstractNoise.createDoubleGridOfZeros(size);

        assertEquals(size, grid.getSize());

        for (Coord c : grid.coordIterable()) {
            assertEquals(0, grid.getValue(c));
        }
    }

}