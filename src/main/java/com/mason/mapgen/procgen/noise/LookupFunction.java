package com.mason.mapgen.procgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

public interface LookupFunction{


    public abstract Coord lookup(Coord coord, Double underlying, Size gridSize);


    static Coord defaultLookupFunction(Coord coord, Double underlying, Size gridSize){
        int x = coord.y();
        int y = (int)(coord.x() + gridSize.height() * underlying) % gridSize.height();
        return new Coord(x, y);
    }

}
