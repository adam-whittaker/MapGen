package com.mason.mapgen.procgen.noise;

import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;

public interface LookupFunction{


    public abstract Coord lookup(Coord coord, Double underlying, Size gridSize);


    static Coord defaultLookupFunction(Coord coord, Double underlying, Size gridSize){
        int x = coord.y();
        int y = (int)(coord.x() + gridSize.height() * underlying) % gridSize.height();
        return new Coord(x, y);
    }

}
