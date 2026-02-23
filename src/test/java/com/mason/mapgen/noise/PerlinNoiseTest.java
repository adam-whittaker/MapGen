package com.mason.mapgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.noise.PerlinNoise;
import com.mason.mapgen.structures.grids.MutableGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerlinNoiseTest{

    private static final double EPS = 1e-9;

    @Test
    void constructorCreatesGridOfGivenSizeAndNotGeneratedYet() {
        Size size = new Size(8, 8);
        PerlinNoise noise = new PerlinNoise(size, 1.0, 3, 2.0, 0.5);

        MutableGrid<Double> grid = noise.getGrid();
        assertNotNull(grid, "Grid should not be null");
        assertEquals(size, grid.getSize(), "Grid size should match constructor argument");

        // From AbstractNoise / Noise
        assertEquals(size, noise.getSize(), "getSize() should delegate to grid size");
        assertFalse(noise.isNoiseGenerated(), "Noise should not be generated initially");
    }

    @Test
    void generateNoisePopulatesGridWithFiniteValues() {
        Size size = new Size(8, 8);
        PerlinNoise noise = new PerlinNoise(size, 1.0, 3, 2.0, 0.5);
        MutableGrid<Double> grid = noise.getGrid();

        noise.generateNoise();

        assertTrue(noise.isNoiseGenerated(), "isNoiseGenerated() should be true after generateNoise()");

        boolean anyNonZero = false;

        for (Coord c : grid.coordIterable()) {
            Double v = grid.getValue(c);
            assertNotNull(v, "Grid value must not be null after generateNoise at " + c);
            assertTrue(Double.isFinite(v), "Grid value must be finite at " + c);
            if (Math.abs(v) > 1e-9) {
                anyNonZero = true;
            }
        }

        // Sanity check: noise should have actually changed something
        assertTrue(anyNonZero, "At least one grid value should be non-zero after Perlin noise generation");
    }

    @Test
    void secondGenerateNoiseCallThrowsIllegalStateException() {
        Size size = new Size(8, 8);
        PerlinNoise noise = new PerlinNoise(size, 1.0, 2, 2.0, 0.5);

        noise.generateNoise();
        assertTrue(noise.isNoiseGenerated());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                noise::generateNoise,
                "Second call to generateNoise() should throw"
        );
        assertEquals("Noise has already been generated!", ex.getMessage());
    }

    @Test
    void normaliseMapsNoiseValuesIntoUnitInterval() {
        Size size = new Size(8, 8);
        PerlinNoise noise = new PerlinNoise(size, 1.0, 3, 2.0, 0.5);
        MutableGrid<Double> grid = noise.getGrid();

        noise.generateNoise();
        noise.normalise();  // default method from Noise

        for (Coord c : grid.coordIterable()) {
            double v = grid.getValue(c);
            assertTrue(v >= -EPS && v <= 1.0 + EPS,
                    "Normalised value must be in [0,1]: " + v + " at " + c);
            assertTrue(Double.isFinite(v), "Normalised value must be finite at " + c);
        }
    }

    @Test
    void normaliseOnConstantGridProducesAllHalves() {
        Size size = new Size(4, 4);
        PerlinNoise noise = new PerlinNoise(size, 1.0, 1, 2.0, 0.5);
        MutableGrid<Double> grid = noise.getGrid();

        // Fill grid with a constant value
        for (Coord c : grid.coordIterable()) {
            grid.setValue(c, 7.0);
        }

        // According to Noise.normalise(): constant grid → all 0.5
        noise.normalise();

        for (Coord c : grid.coordIterable()) {
            double v = grid.getValue(c);
            assertEquals(0.5, v, EPS,
                    "Expected 0.5 after normalising constant grid at " + c);
        }
    }

}