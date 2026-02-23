package com.mason.mapgen.structures.direction;

import com.mason.libgui.utils.structures.Coord;

public enum CompassDirection implements Direction{


    NW(-1, -1), N(0, -1), NE(1,-1), E(1, 0),
    SE(1, 1), S(0, 1), SW(-1, 1), W(-1, 0);


    private final int x, y;

    CompassDirection(int x, int y){
        this.x = x;
        this.y = y;
    }


    @Override
    public int x(){
        return x;
    }

    @Override
    public int y(){
        return y;
    }

    @Override
    public int index(){
        return ordinal();
    }

    @Override
    public int size(){
        return values().length;
    }

    public Coord neighbour(Coord c){
        return new Coord(c.x() + x, c.y() + y);
    }

}
