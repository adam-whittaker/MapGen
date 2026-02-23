package com.mason.mapgen.structures.grids;

import com.mason.libgui.utils.structures.Coord;

import java.util.function.BiFunction;
import java.util.function.Function;

public class MutableGrid<T> extends Grid<T>{


    public MutableGrid(T[][] grid){
        super(grid);
    }

    public MutableGrid(Grid<T> grid){
        super(grid);
    }

    public static <E> MutableGrid<E> buildGrid(E[][] emptyGrid, Function<Coord, E> builder){
        MutableGrid<E> grid = new MutableGrid<>(emptyGrid);
        for(Coord c : grid.coordIterable()){
            grid.setValue(c, builder.apply(c));
        }
        return grid;
    }


    public void setValue(Coord c, T val){
        grid[c.y()][c.x()] = val;
    }

    public void transformSelf(BiFunction<Coord, T, T> transform){
        for(Coord c : coordIterable()){
            setValue(c, transform.apply(c, getValue(c)));
        }
    }

}
