package com.mason.mapgen.paint.components.panes.leftPane.toggles.brushToggles;

import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;

import java.awt.*;

public class ColorIcon{


    private final RectQuery bounds;
    private final RGBQuery colorQuery;


    ColorIcon(RectQuery bounds, RGBQuery colorQuery){
        this.bounds = bounds;
        this.colorQuery = colorQuery;
    }


    void render(Graphics2D g){
        g.setColor(colorQuery.sampleRGBColor());
        g.fillOval(bounds.x(), bounds.y(), bounds.width(), bounds.height());
    }

}
