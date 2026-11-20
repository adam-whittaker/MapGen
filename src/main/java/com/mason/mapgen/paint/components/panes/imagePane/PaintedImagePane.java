package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.panes.PanZoomPane;
import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;

import java.awt.*;

public class PaintedImagePane extends PanZoomPane{


    protected PaintedImagePane(PanZoomPaneSkeleton skeleton){
        super(skeleton);
    }


    @Override
    protected void drawBackground(Graphics2D g){}

    @Override
    protected void drawForeground(Graphics2D g){}

}
