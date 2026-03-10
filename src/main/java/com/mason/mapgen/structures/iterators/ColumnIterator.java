package com.mason.mapgen.structures.iterators;

import com.mason.mapgen.core.Utils;

import java.util.Iterator;

public class ColumnIterator<T> implements Iterator<T>{


    private final T[][] grid;
    private final int colIndex;
    private final int height;
    private int y = 0;


    public ColumnIterator(T[][] grid, int colIndex){
        this.grid = grid;
        this.colIndex = colIndex;
        this.height = grid.length;
        Utils.verifyArrayIndexWithinBounds(colIndex, grid[0]);
    }


    @Override
    public boolean hasNext(){
        return y<height;
    }

    @Override
    public T next(){
        T current = grid[y][colIndex];
        y++;
        return current;
    }

}
