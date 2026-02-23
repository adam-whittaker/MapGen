package com.mason.mapgen.structures.grids.lowMemory;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.Arrays;

public class ShortGrid{

    private final Size size;
    private final short[] grid;


    public ShortGrid(Size size){
        this.size = size;
        grid = new short[size.width()*size.height()];
    }

    public ShortGrid(Size size, short initialValue){
        this.size = size;
        grid = new short[size.width()*size.height()];
        Arrays.fill(grid, initialValue);
    }


    public int asIndex(Coord coord){
        return index(coord.x(), coord.y());
    }

    private int index(int x, int y){
        return y * size.width() + x;
    }

    public short get(int x, int y){
        return getByIndex(index(x, y));
    }

    public short getByIndex(int idx){
        return grid[idx];
    }

    public void set(int x, int y, short value){
        setByIndex(index(x, y), value);
    }

    public void setByIndex(int idx, short value){
        grid[idx] = value;
    }

    public Size size(){
        return size;
    }

}
