package com.mason.mapgen.structures.iterators;

import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;

public class CoordIterator extends GridIterator<Coord>{


    public CoordIterator(Size size){
        super(size);
    }


    @Override
    public Coord current(){
        return new Coord(x(), y());
    }

}
