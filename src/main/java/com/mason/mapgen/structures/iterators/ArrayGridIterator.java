package com.mason.mapgen.structures.iterators;

import com.mason.libgui.utils.structures.Size;

public class ArrayGridIterator<T> extends GridIterator<T>{

    private final T[][] grid;


    public ArrayGridIterator(T[][] grid){
        super(new Size(grid[0].length, grid.length));
        this.grid = grid;
    }


    @Override
    public T current(){
        return grid[y()][x()];
    }

}
