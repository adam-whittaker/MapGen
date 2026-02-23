package com.mason.mapgen.structures.iterators;


import com.mason.libgui.utils.structures.Size;

import java.util.Iterator;

public abstract class GridIterator<T> implements Iterator<T>{


    private int x = 0, y = 0;
    private final Size size;


    public GridIterator(Size size){
        this.size = size;
    }


    @Override
    public boolean hasNext(){
        return y<size.height();
    }

    @Override
    public T next(){
        T current = current();
        incrementXY();
        return current;
    }

    public abstract T current();

    protected int x(){
        return x;
    }

    protected int y(){
        return y;
    }

    private void incrementXY(){
        x++;
        if(x == size.width()){
            x = 0;
            y++;
        }
    }

}
