package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.geo.*;
import com.mason.libstruct.interfaces.Boundable;

import java.awt.*;

public interface PaintCanvas extends Boundable{


    Short getCentroidIDFromCoord(Coord coord);


    default Color getChunkColor(Coord coord){
        return getChunkColorFromLayer(coord, 0);
    }

    Color getChunkColorFromLayer(Coord coord, int layerID);


    default Color getChunkColorFromCentroidID(Short centroidID){
        return getChunkColorFromCentroidAndLayerID(centroidID, 0);
    }

    Color getChunkColorFromCentroidAndLayerID(Short centroidID, int layerID);


    void paintChunkColor(Color paintColor, Short centroidID);

    default void setChunkColor(Color newColor, Short centroidID){
        setChunkColorOnLayer(newColor, centroidID, 0);
    }

    void setChunkColorOnLayer(Color newColor, Short centroidID, int layerID);



    Iterable<Short> centroidNeighbourhood(Short centroidID, int searchDepth);


    Coord getCoord();

    Size getSize();

    UIComponent getImageComponent();

}
