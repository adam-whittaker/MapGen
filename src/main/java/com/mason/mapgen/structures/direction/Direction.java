package com.mason.mapgen.structures.direction;


import com.mason.libgui.utils.structures.Coord;

public sealed interface Direction permits CardinalDirection, CompassDirection{

    int x();
    int y();
    int index();
    int size();


    default Coord neighbour(Coord c){
        return new Coord(x()+c.x(), y()+c.y());
    }

}
