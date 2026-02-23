package com.mason.mapgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.noise.Noise;
import com.mason.mapgen.structures.grids.MutableGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoiseTest{

    private static final double EPS = 1e-9;

    /**
     * Simple test implementation of Noise.
     * - Owns a MutableGrid<Double>
     * - generateNoise() fills each cell with a deterministic pattern,
     *   but only on the first call.
     */
    private static class StubNoise implements Noise{
        private final MutableGrid<Double> grid;
        private boolean generated = false;

        StubNoise(Size size) {
            this.grid = new MutableGrid<>(new Double[size.height()][size.width()]);
        }

        StubNoise(MutableGrid<Double> grid) {
            this.grid = grid;
        }

        @Override
        public MutableGrid<Double> getGrid() {
            return grid;
        }

        @Override
        public boolean isNoiseGenerated() {
            return generated;
        }

        @Override
        public void generateNoise() {
            if (generated) {
                // second and subsequent calls are no-ops
                return;
            }
            // Simple deterministic pattern: value = y*10 + x
            for (Coord c : grid.coordIterable()) {
                grid.setValue(c, (double) (c.y() * 10 + c.x()));
            }
            generated = true;
        }
    }

    @Test
    void getSizeDelegatesToUnderlyingGrid() {
        Size size = new Size(4, 3);
        Noise noise = new StubNoise(size);

        Size s = noise.getSize();
        assertEquals(size.width(), s.width());
        assertEquals(size.height(), s.height());
        assertEquals(size, s);
    }

    @Test
    void normaliseMapsMinToZeroAndMaxToOne() {
        Size size = new Size(3, 1);
        MutableGrid<Double> grid = new MutableGrid<>(new Double[size.height()][size.width()]);

        // Values: [-2, 0, 4]
        grid.setValue(new Coord(0, 0), -2.0);
        grid.setValue(new Coord(1, 0), 0.0);
        grid.setValue(new Coord(2, 0), 4.0);

        Noise noise = new StubNoise(grid);
        noise.normalise();

        double v0 = grid.getValue(new Coord(0, 0));
        double v1 = grid.getValue(new Coord(1, 0));
        double v2 = grid.getValue(new Coord(2, 0));

        // min=-2, max=4 → range=6 → (val+2)/6
        assertEquals(0.0, v0, EPS);          // min → 0
        assertEquals(2.0 / 6.0, v1, EPS);    // middle
        assertEquals(1.0, v2, EPS);          // max → 1
    }

    @Test
    void normaliseHandlesAllNegativeValues() {
        Size size = new Size(2, 1);
        MutableGrid<Double> grid = new MutableGrid<>(new Double[size.height()][size.width()]);

        // Values: [-5, -3]
        grid.setValue(new Coord(0, 0), -5.0);
        grid.setValue(new Coord(1, 0), -3.0);

        Noise noise = new StubNoise(grid);
        noise.normalise();

        double a = grid.getValue(new Coord(0, 0));
        double b = grid.getValue(new Coord(1, 0));

        // min=-5, max=-3 → range=2 → -5→0, -3→1
        assertEquals(0.0, a, EPS);
        assertEquals(1.0, b, EPS);
    }

    @Test
    void normaliseTurnsConstantGridIntoAllHalf() {
        Size size = new Size(3, 2);
        MutableGrid<Double> grid = new MutableGrid<>(new Double[size.height()][size.width()]);

        // All same value
        for (Coord c : grid.coordIterable()) {
            grid.setValue(c, 7.0);
        }

        Noise noise = new StubNoise(grid);
        noise.normalise();

        for (Coord c : grid.coordIterable()) {
            double v = grid.getValue(c);
            assertEquals(0.5, v, EPS, "Expected 0.5 at " + c);
        }
    }

    @Test
    void normaliseKeepsAllValuesWithinZeroAndOne() {
        Size size = new Size(2, 2);
        MutableGrid<Double> grid = new MutableGrid<>(new Double[size.height()][size.width()]);

        grid.setValue(new Coord(0, 0), -10.0);
        grid.setValue(new Coord(1, 0), 0.5);
        grid.setValue(new Coord(0, 1), 3.0);
        grid.setValue(new Coord(1, 1), 7.0);

        Noise noise = new StubNoise(grid);
        noise.normalise();

        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (Coord c : grid.coordIterable()) {
            double v = grid.getValue(c);
            assertTrue(v >= 0.0 - EPS && v <= 1.0 + EPS,
                    "Value out of [0,1]: " + v + " at " + c);
            min = Math.min(min, v);
            max = Math.max(max, v);
        }

        // For a non-constant grid we expect 0 and 1 to appear
        assertEquals(0.0, min, EPS);
        assertEquals(1.0, max, EPS);
    }

    @Test
    void generateNoiseRunsOnlyOncePerInstanceWithoutError() {
        Size size = new Size(3, 3);
        StubNoise noise = new StubNoise(size);
        MutableGrid<Double> grid = noise.getGrid();

        // Initially not generated
        assertFalse(noise.isNoiseGenerated());

        // First call: should generate
        noise.generateNoise();
        assertTrue(noise.isNoiseGenerated());

        // Capture some values
        double before = grid.getValue(new Coord(1, 1));

        // Second call: should NOT throw, and should not change values
        assertDoesNotThrow(noise::generateNoise);
        double after = grid.getValue(new Coord(1, 1));

        assertEquals(before, after, EPS, "Second generateNoise() call should be a no-op");
    }

}