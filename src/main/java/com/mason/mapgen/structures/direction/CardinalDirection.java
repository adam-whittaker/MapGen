package com.mason.mapgen.structures.direction;

public enum CardinalDirection implements Direction{


    N(0, -1), E(1, 0), S(0, 1), W(-1, 0);


    private final int x, y;

    CardinalDirection(int x, int y){
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

}
