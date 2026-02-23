package com.mason.mapgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.noise.LookupFunction;
import com.mason.mapgen.procgen.noise.LookupNoise;
import com.mason.mapgen.procgen.noise.Noise;
import com.mason.mapgen.structures.grids.MutableGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LookupNoiseTest{

    private static final double EPS = 1e-9;

    /**
     * Simple deterministic Noise implementation for testing LookupNoise.
     * - Owns its own MutableGrid<Double>.
     * - generateNoise() sets a known pattern exactly once.
     * - normalise() is overridden only to count calls, then delegates to Noise.super.normalise().
     */
    private static class StubNoise implements Noise{
        private final MutableGrid<Double> grid;
        int generateCalls = 0;
        int normaliseCalls = 0;
        private boolean generated = false;

        StubNoise(Size size) {
            this.grid = new MutableGrid<>(new Double[size.height()][size.width()]);
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
            // Only generate once; subsequent calls are no-op
            if (generated) return;
            generateCalls++;
            generated = true;
            // Fill with simple pattern: val = y*10 + x
            for (Coord c : grid.coordIterable()) {
                grid.setValue(c, (double) (c.y() * 10 + c.x()));
            }
        }

        @Override
        public void normalise() {
            normaliseCalls++;
            Noise.super.normalise();
        }
    }

    @Test
    void constructorUsesNoiseSizeAndDoesNotGenerate() {
        Size size = new Size(3, 2);
        StubNoise baseNoise = new StubNoise(size);
        LookupFunction identityLookup = (coord, underlying, gridSize) -> coord;

        LookupNoise lookupNoise = new LookupNoise(baseNoise, identityLookup);

        assertEquals(size, lookupNoise.getSize(), "LookupNoise size should match underlying Noise size");
        assertEquals(size, lookupNoise.getGrid().getSize(), "Internal grid should have same size");
        assertFalse(lookupNoise.isNoiseGenerated(), "LookupNoise should not be generated initially");

        // Underlying noise should also still be ungenerated at this point
        assertFalse(baseNoise.isNoiseGenerated());
    }

    @Test
    void generateNoiseCallsUnderlyingNoiseAndNormaliseAndAppliesIdentityLookup() {
        Size size = new Size(2, 2);
        StubNoise baseNoise = new StubNoise(size);
        LookupFunction identityLookup = (coord, underlying, gridSize) -> coord;

        LookupNoise lookupNoise = new LookupNoise(baseNoise, identityLookup);

        // Before: underlying grid has pattern after generateNoise, but we rely on generateNoise being called by LookupNoise
        lookupNoise.generateNoise();

        assertTrue(lookupNoise.isNoiseGenerated(), "LookupNoise should be marked generated");
        assertTrue(baseNoise.isNoiseGenerated(), "Underlying Noise should be marked generated");
        assertEquals(1, baseNoise.generateCalls, "Underlying generateNoise should be called exactly once");
        assertEquals(1, baseNoise.normaliseCalls, "Underlying normalise should be called once");

        MutableGrid<Double> perlinGrid = baseNoise.getGrid();
        MutableGrid<Double> lookupGrid = lookupNoise.getGrid();

        // Underlying pattern was: 0,1 / 10,11 → after normalise: 0, 1/11, 10/11, 1
        // (min=0, max=11)
        assertEquals(0.0, perlinGrid.getValue(new Coord(0, 0)), EPS);
        assertEquals(1.0 / 11.0, perlinGrid.getValue(new Coord(1, 0)), EPS);
        assertEquals(10.0 / 11.0, perlinGrid.getValue(new Coord(0, 1)), EPS);
        assertEquals(1.0, perlinGrid.getValue(new Coord(1, 1)), EPS);

        // Identity lookup: lookupNoise grid should match normalised underlying grid
        for (Coord c : lookupGrid.coordIterable()) {
            Double expected = perlinGrid.getValue(c);
            Double actual = lookupGrid.getValue(c);
            assertNotNull(actual, "LookupNoise grid should be fully populated at " + c);
            assertEquals(expected, actual, EPS, "LookupNoise grid should equal underlying at " + c);
        }
    }

    @Test
    void generateNoiseUsesCustomLookupFunction() {
        Size size = new Size(2, 2);
        StubNoise baseNoise = new StubNoise(size);

        // Lookup that swaps x and y, ignoring underlying & size.
        LookupFunction swapXY = (coord, underlying, gridSize) ->
                new Coord(coord.y(), coord.x());

        LookupNoise lookupNoise = new LookupNoise(baseNoise, swapXY);

        lookupNoise.generateNoise();

        MutableGrid<Double> perlinGrid = baseNoise.getGrid();
        MutableGrid<Double> lookupGrid = lookupNoise.getGrid();

        // Underlying pattern before normalise: [ (0,0)=0, (1,0)=1, (0,1)=10, (1,1)=11 ]
        // After normalise (min=0, max=11): [0, 1/11, 10/11, 1]
        double v00 = perlinGrid.getValue(new Coord(0, 0));
        double v10 = perlinGrid.getValue(new Coord(1, 0));
        double v01 = perlinGrid.getValue(new Coord(0, 1));
        double v11 = perlinGrid.getValue(new Coord(1, 1));

        assertEquals(0.0, v00, EPS);
        assertEquals(1.0 / 11.0, v10, EPS);
        assertEquals(10.0 / 11.0, v01, EPS);
        assertEquals(1.0, v11, EPS);

        // Now check lookupGrid with swapXY:
        // At (0,0): lookupCoord=(0,0) → 0
        assertEquals(v00, lookupGrid.getValue(new Coord(0, 0)), EPS);
        // At (1,0): lookupCoord=(0,1) → 10/11
        assertEquals(v01, lookupGrid.getValue(new Coord(1, 0)), EPS);
        // At (0,1): lookupCoord=(1,0) → 1/11
        assertEquals(v10, lookupGrid.getValue(new Coord(0, 1)), EPS);
        // At (1,1): lookupCoord=(1,1) → 1
        assertEquals(v11, lookupGrid.getValue(new Coord(1, 1)), EPS);
    }

    @Test
    void secondGenerateNoiseCallThrowsIllegalStateException() {
        Size size = new Size(3, 3);
        StubNoise baseNoise = new StubNoise(size);
        LookupFunction identityLookup = (coord, underlying, gridSize) -> coord;

        LookupNoise lookupNoise = new LookupNoise(baseNoise, identityLookup);

        lookupNoise.generateNoise();
        assertTrue(lookupNoise.isNoiseGenerated());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                lookupNoise::generateNoise,
                "Second call to generateNoise() should throw (AbstractNoise contract)"
        );
        assertEquals("Noise has already been generated!", ex.getMessage());
    }

    @Test
    void buildPerlinLookupNoiseProducesCorrectSizeAndGeneratesFiniteValues() {
        Size size = new Size(8, 8);
        LookupFunction identityLookup = (coord, underlying, gridSize) -> coord;

        LookupNoise lookupNoise = LookupNoise.buildPerlinLookupNoise(
                size, 1.0, 3, 2.0, 0.5, identityLookup);

        assertEquals(size, lookupNoise.getSize());
        assertEquals(size, lookupNoise.getGrid().getSize());
        assertFalse(lookupNoise.isNoiseGenerated());

        lookupNoise.generateNoise();
        assertTrue(lookupNoise.isNoiseGenerated());

        for (Coord c : lookupNoise.getGrid().coordIterable()) {
            Double v = lookupNoise.getGrid().getValue(c);
            assertNotNull(v, "Grid value should not be null after generation at " + c);
            assertTrue(Double.isFinite(v), "Grid value must be finite at " + c);
        }
    }

    @Test
    void buildWithDefaultPerlinLookupFunctionAlsoWorks() {
        Size size = new Size(8, 8);

        LookupNoise lookupNoise = LookupNoise.buildWithDefaultPerlinLookupFunction(
                size, 1.0, 3, 2.0, 0.5);

        assertEquals(size, lookupNoise.getSize());
        assertEquals(size, lookupNoise.getGrid().getSize());
        assertFalse(lookupNoise.isNoiseGenerated());

        lookupNoise.generateNoise();
        assertTrue(lookupNoise.isNoiseGenerated());

        for (Coord c : lookupNoise.getGrid().coordIterable()) {
            Double v = lookupNoise.getGrid().getValue(c);
            assertNotNull(v, "Grid value should not be null after generation at " + c);
            assertTrue(Double.isFinite(v), "Grid value must be finite at " + c);
        }
    }

}