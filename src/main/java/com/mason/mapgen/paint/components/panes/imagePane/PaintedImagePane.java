package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.ComponentIDRegister;
import com.mason.libgui.components.Identifiable;
import com.mason.libgui.components.panes.PanZoomPane;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

import java.awt.*;

public class PaintedImagePane extends PanZoomPane implements Identifiable{


    private static final Color BACKGROUND_COLOR = new Color(40, 40, 40);

    private final String name;
    private final int id;


    private PaintedImagePane(PaintedImagePaneSkeleton skeleton){
        super(skeleton.getPanZoomPaneSkeleton());
        name = "IMAGE_PAINT_PANE";
        id = ComponentIDRegister.registerComponent(this);
    }

    public static PaintedImagePane build(PaintGUIStateSkeleton paintGUIStateSkeleton){
        return new PaintedImagePane(PaintedImagePaneBuilder.buildSkeleton(paintGUIStateSkeleton));
    }


    @Override
    protected void drawBackground(Graphics2D g){
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getSize().width(), getSize().height());
    }

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
