package com.mason.mapgen.paint.logic.tools.brush;

import java.util.HashSet;
import java.util.Set;

public class BrushStroke{


    private final Set<Short> centroidDataSet;


    BrushStroke(){
        centroidDataSet = new HashSet<>();
    }


    void addToStroke(Short centroidID){
        centroidDataSet.add(centroidID);
    }

    boolean isInStroke(Short centroidID){
        return centroidDataSet.contains(centroidID);
    }

}
