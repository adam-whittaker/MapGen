package com.mason.mapgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.noise.MidpointDisplacementNoise;
import com.mason.mapgen.structures.grids.MutableGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MidpointDisplacementNoiseTest{

    private static final double EPS = 1e-9;

    @Test
    void buildInstanceCreatesGridOfGivenSize() {
        Size size = new Size(5, 7);
        MidpointDisplacementNoise noise =
                MidpointDisplacementNoise.buildInstance(size, 1.0, 0.5);

        MutableGrid<Double> grid = noise.getGrid();
        assertNotNull(grid);
        assertEquals(size, grid.getSize());
    }

    @Test
    void buildInstanceWithTileArtefactsCreatesGridOfGivenSize() {
        Size size = new Size(4, 4);
        MidpointDisplacementNoise noise =
                MidpointDisplacementNoise.buildInstanceWithTileArtefacts(size, 1.0, 0.5);

        MutableGrid<Double> grid = noise.getGrid();
        assertNotNull(grid);
        assertEquals(size, grid.getSize());
    }

    @Test
    void generateNoiseFillsAllCellsAndClampsValuesFor2x2Grid() {
        Size size = new Size(2, 2);
        MidpointDisplacementNoise noise =
                MidpointDisplacementNoise.buildInstance(size, 1.0, 0.5);

        MutableGrid<Double> grid = noise.getGrid();

        // Precondition: grid starts zero
        for (Coord c : grid.coordIterable()) {
            assertEquals(0, grid.getValue(c));
        }
        assertFalse(noise.isNoiseGenerated());

        noise.generateNoise();

        // Flag should flip
        assertTrue(noise.isNoiseGenerated());

        // Postcondition: all cells non-null and within [-1, 1]
        for (Coord c : grid.coordIterable()) {
            Double v = grid.getValue(c);
            assertNotNull(v, "Value should be set after generateNoise at " + c);
            assertTrue(v >= -1.0 - EPS && v <= 1.0 + EPS,
                    "Value out of [-1,1]: " + v + " at " + c);
        }
    }

    @Test
    void generateNoiseFillsAllCellsAndClampsValuesFor3x3Grid() {
        Size size = new Size(3, 3);
        MidpointDisplacementNoise noise =
                MidpointDisplacementNoise.buildInstance(size, 1.0, 0.5);

        MutableGrid<Double> grid = noise.getGrid();

        noise.generateNoise();

        for (Coord c : grid.coordIterable()) {
            Double v = grid.getValue(c);
            assertNotNull(v, "Value should be set after generateNoise at " + c);
            assertTrue(v >= -1.0 - EPS && v <= 1.0 + EPS,
                    "Value out of [-1,1]: " + v + " at " + c);
        }
    }

    @Test
    void generateNoiseWithTileArtefactsAlsoFillsCellsAndClamps() {
        Size size = new Size(3, 3);
        MidpointDisplacementNoise noise =
                MidpointDisplacementNoise.buildInstanceWithTileArtefacts(size, 1.0, 0.5);

        MutableGrid<Double> grid = noise.getGrid();

        noise.generateNoise();

        for (Coord c : grid.coordIterable()) {
            Double v = grid.getValue(c);
            assertNotNull(v, "Value should be set after generateNoise at " + c);
            assertTrue(v >= -1.0 - EPS && v <= 1.0 + EPS,
                    "Value out of [-1,1]: " + v + " at " + c);
        }
    }

    @Test
    void secondCallToGenerateNoiseThrowsIllegalStateException() {
        Size size = new Size(3, 3);
        MidpointDisplacementNoise noise =
                MidpointDisplacementNoise.buildInstance(size, 1.0, 0.5);

        noise.generateNoise();
        assertTrue(noise.isNoiseGenerated());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                noise::generateNoise,
                "Second call to generateNoise() should throw"
        );
        assertEquals("Noise has already been generated!", ex.getMessage());
    }

}