package com.mason.mapgen.structures.iterators;

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
        validateColumnIndex();
    }

    private void validateColumnIndex(){
        if(colIndex < 0 || colIndex >= grid[0].length){
            throw new IllegalStateException("Column out of bounds");
        }
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
