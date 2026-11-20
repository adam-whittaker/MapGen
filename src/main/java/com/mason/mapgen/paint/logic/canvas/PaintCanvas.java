package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.geo.*;
import com.mason.libstruct.interfaces.Boundable;

import java.awt.*;

public interface PaintCanvas extends Boundable{


    public Short getCentroidIDFromCoord(Coord coord);

    public Color getChunkColor(Coord coord);

    public Color getChunkColorFromCentroidID(Short centroidID);

    public void changeChunkColor(Color newColor, Short centroidID);

    public Iterable<Short> centroidNeighbourhood(Short centroidID, int searchDepth);


    public Coord getCoord();

    public Size getSize();

    public UIComponent getImageComponent();

}
