package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.mapgen.paint.logic.tools.PaintTool;

public interface BrushTool extends PaintTool, DualColorable{

    void setBrushSize(int brushSize);
    int getBrushSize();

    void setCertainty(double certainty);
    double getCertainty();

    void setAlpha(int alpha);
    int getAlpha();

    void setChannelIndependence(boolean channelIndependence);
    boolean getChannelIndependence();

}
