package com.mason.mapgen.structures.iterators;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

public class CoordIterator extends GridIterator<Coord>{


    public CoordIterator(Size size){
        super(size);
    }


    @Override
    public Coord current(){
        return new Coord(x(), y());
    }

}
