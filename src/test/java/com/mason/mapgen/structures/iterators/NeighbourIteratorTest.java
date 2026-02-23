package com.mason.mapgen.structures.iterators;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.structures.direction.CardinalDirection;
import com.mason.mapgen.structures.direction.CompassDirection;
import com.mason.mapgen.structures.grids.Grid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourIteratorTest{

    private Grid<Integer> create3x3Grid() {
        Integer[][] backing = new Integer[3][3];
        return Grid.buildGrid(backing, c -> c.y() * 10 + c.x());
    }

    @Test
    void overCardinalsFromCentreReturnsFourNeighboursInOrder() {
        Grid<Integer> grid = create3x3Grid();
        Coord centre = new Coord(1, 1);

        NeighbourIterator<Integer, CardinalDirection> it =
                NeighbourIterator.overCardinals(grid, centre);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // CardinalDirection order: N, E, S, W
        // From (1,1): N=(1,0)=1, E=(2,1)=12, S=(1,2)=21, W=(0,1)=10
        assertEquals(List.of(1, 12, 21, 10), seen);
        assertFalse(it.hasNext());
    }

    @Test
    void overWholeCompassFromCentreReturnsEightNeighboursInOrder() {
        Grid<Integer> grid = create3x3Grid();
        Coord centre = new Coord(1, 1);

        NeighbourIterator<Integer, CompassDirection> it =
                NeighbourIterator.overWholeCompass(grid, centre);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // CompassDirection order: NW, N, NE, E, SE, S, SW, W
        // From (1,1):
        // NW=(0,0)=0, N=(1,0)=1, NE=(2,0)=2,
        // E=(2,1)=12, SE=(2,2)=22, S=(1,2)=21,
        // SW=(0,2)=20, W=(0,1)=10
        assertEquals(List.of(0, 1, 2, 12, 22, 21, 20, 10), seen);
        assertFalse(it.hasNext());
    }

    @Test
    void overWholeCompassFromCornerYieldsOnlyInBoundsNeighbours() {
        Grid<Integer> grid = create3x3Grid();
        Coord corner = new Coord(0, 0); // top-left

        NeighbourIterator<Integer, CompassDirection> it =
                NeighbourIterator.overWholeCompass(grid, corner);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // From (0,0), in-bounds neighbours: E=(1,0)=1, SE=(1,1)=11, S=(0,1)=10
        // (NW,N,NE,SW,W are all out-of-bounds and must be skipped)
        assertEquals(3, seen.size());
        assertEquals(List.of(1, 11, 10), seen);
        assertFalse(it.hasNext());
    }

    @Test
    void overCardinalsFromEdgeYieldsOnlyInBoundsNeighbours() {
        Grid<Integer> grid = create3x3Grid();
        Coord topMiddle = new Coord(1, 0); // (1,0)

        NeighbourIterator<Integer, CardinalDirection> it =
                NeighbourIterator.overCardinals(grid, topMiddle);

        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }

        // Directions: N,E,S,W
        // From (1,0): N out-of-bounds
        // E=(2,0)=2, S=(1,1)=11, W=(0,0)=0
        assertEquals(3, seen.size());
        assertEquals(List.of(2, 11, 0), seen);
        assertFalse(it.hasNext());
    }

    @Test
    void overWholeCompassOn1x1GridHasNoNeighbours() {
        Integer[][] backing = new Integer[1][1];
        Grid<Integer> grid = Grid.buildGrid(backing, c -> 42);

        Coord onlyCell = new Coord(0, 0);

        NeighbourIterator<Integer, CompassDirection> it =
                NeighbourIterator.overWholeCompass(grid, onlyCell);

        // Expected: no valid neighbours
        assertFalse(it.hasNext(), "1x1 grid cell should have no neighbours");
    }

}