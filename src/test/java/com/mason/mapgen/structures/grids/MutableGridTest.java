package com.mason.mapgen.structures.grids;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class MutableGridTest{

    @Test
    void constructorFromArraySetsSizeAndAllowsMutation() {
        Integer[][] data = {
                {0, 1, 2},
                {3, 4, 5}
        };

        MutableGrid<Integer> grid = new MutableGrid<>(data);

        // Size from super
        Size size = grid.getSize();
        assertEquals(3, size.width());
        assertEquals(2, size.height());

        // Initial values
        assertEquals(0, grid.getValue(new Coord(0, 0)));
        assertEquals(4, grid.getValue(new Coord(1, 1)));

        // Mutation via setValue
        Coord c = new Coord(1, 0);
        grid.setValue(c, 99);
        assertEquals(99, grid.getValue(c));
        assertEquals(99, data[c.y()][c.x()], "Underlying array should also reflect mutation");
    }

    @Test
    void copyConstructorSharesUnderlyingGridAndSize() {
        Integer[][] data = {
                {10, 11},
                {12, 13}
        };

        MutableGrid<Integer> original = new MutableGrid<>(data);
        MutableGrid<Integer> copy = new MutableGrid<>(original);

        // Same size instance due to super(grid) behaviour
        assertSame(original.getSize(), copy.getSize());

        // Changing copy affects original
        Coord c = new Coord(1, 0);
        assertEquals(11, original.getValue(c));
        copy.setValue(c, 42);

        assertEquals(42, copy.getValue(c));
        assertEquals(42, original.getValue(c));
        assertEquals(42, data[c.y()][c.x()]);
    }

    @Test
    void buildGridUsesBuilderForEveryCell() {
        Integer[][] empty = new Integer[2][3]; // height=2, width=3

        Function<Coord, Integer> builder =
                coord -> coord.y() * 10 + coord.x();

        MutableGrid<Integer> grid = MutableGrid.buildGrid(empty, builder);

        Size size = grid.getSize();
        assertEquals(3, size.width());
        assertEquals(2, size.height());

        // Check a few values
        assertEquals(0, grid.getValue(new Coord(0, 0)));
        assertEquals(2, grid.getValue(new Coord(2, 0)));
        assertEquals(10, grid.getValue(new Coord(0, 1)));
        assertEquals(12, grid.getValue(new Coord(2, 1)));
    }

    @Test
    void transformAppliesFunctionToEachCell() {
        Integer[][] data = {
                {1, 2, 3},
                {4, 5, 6}
        };
        MutableGrid<Integer> grid = new MutableGrid<>(data);

        BiFunction<Coord, Integer, Integer> incrementByCoordSum =
                (coord, value) -> value + coord.x() + coord.y();

        grid.transformSelf(incrementByCoordSum);

        // After transform: newValue = oldValue + x + y
        assertEquals(1 + 0 + 0, grid.getValue(new Coord(0, 0)));
        assertEquals(2 + 1 + 0, grid.getValue(new Coord(1, 0)));
        assertEquals(3 + 2 + 0, grid.getValue(new Coord(2, 0)));

        assertEquals(4 + 0 + 1, grid.getValue(new Coord(0, 1)));
        assertEquals(5 + 1 + 1, grid.getValue(new Coord(1, 1)));
        assertEquals(6 + 2 + 1, grid.getValue(new Coord(2, 1)));
    }

}