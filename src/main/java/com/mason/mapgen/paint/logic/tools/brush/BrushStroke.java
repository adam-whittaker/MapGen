package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.mapgen.paint.components.PaintCentroidData;

import java.util.HashSet;
import java.util.Set;

public class BrushStroke{


    private final Set<PaintCentroidData> centroidDataSet;


    BrushStroke(){
        centroidDataSet = new HashSet<>();
    }


    void addToStroke(PaintCentroidData data){
        centroidDataSet.add(data);
    }

    boolean isInStroke(PaintCentroidData data){
        return centroidDataSet.contains(data);
    }

}
