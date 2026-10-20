package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.ComponentIDRegister;
import com.mason.libgui.components.Identifiable;
import com.mason.libgui.components.panes.PanZoomPane;
import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;

import java.awt.*;

public class PaintedImagePane extends PanZoomPane implements Identifiable{


    private final String name;
    private final int id;


    protected PaintedImagePane(PanZoomPaneSkeleton skeleton){
        super(skeleton);
        name = "IMAGE_PAINT_PANE";
        id = ComponentIDRegister.registerComponent(this);
    }


    @Override
    protected void drawBackground(Graphics2D g){}

    @Override
    protected void drawForeground(Graphics2D g){}


    @Override
    public int getID(){
        return id;
    }

    @Override
    public String getName(){
        return name;
    }

}
