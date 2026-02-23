package com.mason.mapgen.structures.grids.lowMemory;

import com.mason.libgui.utils.structures.Size;

public class IntGrid{


    private final Size size;
    private final int[] grid;


    public IntGrid(Size size){
        this.size = size;
        grid = new int[size.width()*size.height()];
    }


    private int index(int x, int y){
        return y * size.width() + x;
    }

    public int get(int x, int y){
        return grid[index(x, y)];
    }

    public void set(int x, int y, int value){
        grid[index(x, y)] = value;
    }

}
