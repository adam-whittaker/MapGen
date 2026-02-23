package com.mason.mapgen.structures.iterators;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.structures.grids.Grid;
import com.mason.mapgen.structures.direction.CardinalDirection;
import com.mason.mapgen.structures.direction.CompassDirection;
import com.mason.mapgen.structures.direction.Direction;

import java.util.Iterator;

public class NeighbourIterator<T, D extends Direction> implements Iterator<T>{


    private final Grid<T> grid;
    private final Coord coord;
    private final D[] directions;
    private int index = -1;



    private NeighbourIterator(Grid<T> grid, D[] directions, Coord c){
        this.grid = grid;
        this.directions = directions;
        coord = c;
        moveIndexToNextValidDirection();
    }

    private void moveIndexToNextValidDirection(){
        do{
            index++;
        }while(index < directions.length && !grid.withinBounds(directions[index].neighbour(coord)));
    }

    public static <E> NeighbourIterator<E, CardinalDirection> overCardinals(Grid<E> grid, Coord c){
        return new NeighbourIterator<>(grid, CardinalDirection.values(), c);
    }

    public static <E> NeighbourIterator<E, CompassDirection> overWholeCompass(Grid<E> grid, Coord c){
        return new NeighbourIterator<>(grid, CompassDirection.values(), c);
    }


    @Override
    public boolean hasNext(){
        return index < directions.length;
    }

    @Override
    public T next(){
        Coord current = directions[index].neighbour(coord);
        moveIndexToNextValidDirection();
        return grid.getValue(current);
    }

}
