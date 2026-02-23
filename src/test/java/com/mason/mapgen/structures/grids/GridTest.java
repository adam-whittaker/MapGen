package com.mason.mapgen.structures.grids;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class GridTest{

    @Test
    void constructorFromArraySetsSizeAndValues() {
        Integer[][] data = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };

        Grid<Integer> grid = new Grid<>(data);

        Size size = grid.getSize();
        assertEquals(3, size.width(), "Width should be number of columns");
        assertEquals(3, size.height(), "Height should be number of rows");

        assertEquals(0, grid.getValue(new Coord(0, 0)));
        assertEquals(4, grid.getValue(new Coord(1, 1)));
        assertEquals(8, grid.getValue(new Coord(2, 2)));
    }

    @Test
    void copyConstructorSharesSizeAndValues() {
        Integer[][] data = {
                {1, 2},
                {3, 4}
        };

        Grid<Integer> original = new Grid<>(data);
        Grid<Integer> copy = new Grid<>(original);

        // Same size object (as in your implementation)
        assertSame(original.getSize(), copy.getSize());

        // Same values
        assertEquals(original.getValue(new Coord(0, 0)),
                copy.getValue(new Coord(0, 0)));
        assertEquals(original.getValue(new Coord(1, 1)),
                copy.getValue(new Coord(1, 1)));
    }

    @Test
    void buildGridUsesBuilderForEveryCell() {
        Integer[][] empty = new Integer[2][3]; // height=2, width=3

        Function<Coord, Integer> builder =
                c -> c.y() * 10 + c.x(); // unique, easy-to-check pattern

        Grid<Integer> grid = Grid.buildGrid(empty, builder);

        Size size = grid.getSize();
        assertEquals(3, size.width());
        assertEquals(2, size.height());

        assertEquals(0, grid.getValue(new Coord(0, 0)));
        assertEquals(1, grid.getValue(new Coord(1, 0)));
        assertEquals(2, grid.getValue(new Coord(2, 0)));
        assertEquals(10, grid.getValue(new Coord(0, 1)));
        assertEquals(11, grid.getValue(new Coord(1, 1)));
        assertEquals(12, grid.getValue(new Coord(2, 1)));
    }

    @Test
    void withinBoundsMatchesGridSize() {
        Integer[][] data = {
                {0, 1, 2},
                {3, 4, 5}
        };
        Grid<Integer> grid = new Grid<>(data);
        Size size = grid.getSize();

        // Inside
        assertTrue(grid.withinBounds(new Coord(0, 0)));
        assertTrue(grid.withinBounds(new Coord(size.width() - 1, size.height() - 1)));

        // Outside (negative)
        assertFalse(grid.withinBounds(new Coord(-1, 0)));
        assertFalse(grid.withinBounds(new Coord(0, -1)));

        // Outside (beyond limits)
        assertFalse(grid.withinBounds(new Coord(size.width(), 0)));
        assertFalse(grid.withinBounds(new Coord(0, size.height())));
    }

    @Test
    void numCardinalNeighboursIn3x3Grid() {
        Integer[][] data = new Integer[3][3];
        Grid<Integer> grid = new Grid<>(data);

        // Corner (0,0): right + down
        assertEquals(2, grid.numCardinalNeighbours(new Coord(0, 0)));

        // Corner (2,2): left + up
        assertEquals(2, grid.numCardinalNeighbours(new Coord(2, 2)));

        // Edge (top middle): left + right + down
        assertEquals(3, grid.numCardinalNeighbours(new Coord(1, 0)));

        // Edge (middle right): up + down + left
        assertEquals(3, grid.numCardinalNeighbours(new Coord(2, 1)));

        // Centre: all four cardinals
        assertEquals(4, grid.numCardinalNeighbours(new Coord(1, 1)));
    }

    @Test
    void numNeighboursIn3x3Grid() {
        Integer[][] data = new Integer[3][3];
        Grid<Integer> grid = new Grid<>(data);

        // Standard 8-neighbourhood expectations
        // Corner: 3, edge non-corner: 5, centre: 8

        assertEquals(3, grid.numNeighbours(new Coord(0, 0)));
        assertEquals(3, grid.numNeighbours(new Coord(2, 2)));

        assertEquals(5, grid.numNeighbours(new Coord(1, 0))); // top middle
        assertEquals(5, grid.numNeighbours(new Coord(0, 1))); // middle left

        assertEquals(8, grid.numNeighbours(new Coord(1, 1))); // centre
    }

    @Test
    void numNeighboursInFlatGrid1xN() {
        Integer[][] data = new Integer[3][1]; // width=1, height=3
        Grid<Integer> grid = new Grid<>(data);

        // Line of 3 cells: endpoints have 1 neighbour, middle has 2
        assertEquals(1, grid.numNeighbours(new Coord(0, 0)));
        assertEquals(2, grid.numNeighbours(new Coord(0, 1)));
        assertEquals(1, grid.numNeighbours(new Coord(0, 2)));
    }

    @Test
    void numNeighboursInFlatGridNx1() {
        Integer[][] data = new Integer[1][3]; // width=3, height=1
        Grid<Integer> grid = new Grid<>(data);

        // Line of 3 cells horizontally
        assertEquals(1, grid.numNeighbours(new Coord(0, 0)));
        assertEquals(2, grid.numNeighbours(new Coord(1, 0)));
        assertEquals(1, grid.numNeighbours(new Coord(2, 0)));
    }

    @Test
    void iteratorTraversesAllElementsRowMajor() {
        Integer[][] data = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Grid<Integer> grid = new Grid<>(data);

        List<Integer> seen = new ArrayList<>();
        for (Integer val : grid) {
            seen.add(val);
        }

        assertEquals(9, seen.size());
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8), seen);
    }

    @Test
    void coordIterableCoversAllCoordsWithinBounds() {
        Integer[][] data = new Integer[2][3]; // height=2, width=3
        Grid<Integer> grid = new Grid<>(data);
        Size size = grid.getSize();

        List<Coord> coords = new ArrayList<>();
        for (Coord c : grid.coordIterable()) {
            coords.add(c);
            assertTrue(grid.withinBounds(c), "coordIterable produced out-of-bounds coord: " + c);
        }

        assertEquals(size.width() * size.height(), coords.size());

        // Expect row-major order (0..width-1, 0..height-1)
        List<Coord> expected = List.of(
                new Coord(0, 0), new Coord(1, 0), new Coord(2, 0),
                new Coord(0, 1), new Coord(1, 1), new Coord(2, 1)
        );
        assertEquals(expected, coords);
    }

    @Test
    void rowReturnsCorrectRow() {
        Integer[][] data = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Grid<Integer> grid = new Grid<>(data);

        List<Integer> row1 = new ArrayList<>();
        for (Integer v : grid.row(1)) {
            row1.add(v);
        }

        assertEquals(List.of(3, 4, 5), row1);
    }

    @Test
    void colReturnsCorrectColumn() {
        Integer[][] data = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Grid<Integer> grid = new Grid<>(data);

        List<Integer> col1 = new ArrayList<>();
        for (Integer v : grid.col(1)) {
            col1.add(v);
        }

        assertEquals(List.of(1, 4, 7), col1);
    }

    @Test
    void neighboursIterablesReturnExpectedValues() {
        Integer[][] empty = new Integer[3][3];
        Grid<Integer> grid = Grid.buildGrid(
                empty,
                c -> c.y() * 10 + c.x()
        );
        Coord centre = new Coord(1, 1);

        // Cardinal neighbours of centre: (1,0)=1, (0,1)=10, (2,1)=12, (1,2)=21
        List<Integer> cardinals = new ArrayList<>();
        for (Integer v : grid.cardinalNeighbours(centre)) {
            cardinals.add(v);
        }
        assertEquals(4, cardinals.size());
        assertTrue(cardinals.containsAll(List.of(1, 10, 12, 21)));

        // All neighbours of centre: all except (1,1)=11
        List<Integer> all = new ArrayList<>();
        for (Integer v : grid.neighbours(centre)) {
            all.add(v);
        }
        assertEquals(8, all.size());
        assertTrue(all.containsAll(List.of(0, 1, 2, 10, 12, 20, 21, 22)));
        assertFalse(all.contains(11));
    }

    @Test
    void validRectangularGridIsAccepted() {
        Integer[][] data = {
                {1, 2, 3},
                {4, 5, 6}
        };

        assertDoesNotThrow(() -> new Grid<>(data));
    }

    @Test
    void emptyOuterArrayIsRejectedAsDegenerate() {
        Integer[][] emptyOuter = new Integer[0][0];

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Grid<>(emptyOuter)
        );
        assertEquals("The grid is degenerate!", ex.getMessage());
    }

    @Test
    void rowWithZeroLengthIsRejectedAsDegenerate() {
        Integer[][] degenerate = {
                new Integer[0],       // row with zero columns
                new Integer[0]
        };

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Grid<>(degenerate)
        );
        assertEquals("The grid is degenerate!", ex.getMessage());
    }

    @Test
    void nonRectangularGridWithShortRowIsRejected() {
        Integer[][] nonRect = {
                {1, 2, 3},
                {4, 5}      // shorter
        };

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Grid<>(nonRect)
        );
        assertEquals("The grid is not rectangular!", ex.getMessage());
    }

    @Test
    void nonRectangularGridWithLongRowIsRejected() {
        Integer[][] nonRect = {
                {1, 2},
                {3, 4, 5}   // longer
        };

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Grid<>(nonRect)
        );
        assertEquals("The grid is not rectangular!", ex.getMessage());
    }

    @Test
    void buildGridAlsoRejectsDegenerateGrid() {
        Integer[][] emptyOuter = new Integer[0][0];

        assertThrows(
                IllegalArgumentException.class,
                () -> Grid.buildGrid(emptyOuter, c -> 0)
        );
    }

    @Test
    void buildGridAlsoRejectsNonRectangularGrid() {
        Integer[][] nonRect = new Integer[2][];
        nonRect[0] = new Integer[3];
        nonRect[1] = new Integer[1]; // different length

        assertThrows(
                IllegalArgumentException.class,
                () -> Grid.buildGrid(nonRect, c -> 0)
        );
    }

}