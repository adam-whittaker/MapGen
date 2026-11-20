package com.mason.mapgen.paint.components.panes.rightPane.layersPane;

import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.libgui.components.deco.PaneDeco;
import com.mason.libgui.components.panes.PanZoomPane;
import com.mason.libgui.components.panes.ScrollPanPane;
import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;
import com.mason.libgui.components.panes.construction.ScrollPanPaneSkeleton;

import java.awt.*;

public class PaintLayersPane extends ScrollPanPane{


    private final PaneDeco deco;


    protected PaintLayersPane(ScrollPanPaneSkeleton skeleton){
        super(skeleton);
        deco = new BasicPaneDeco();
    }


    @Override
    protected void drawBackground(Graphics2D g){
        deco.drawBackground(g, getSize());
    }

    @Override
    protected void drawForeground(Graphics2D g){
        deco.drawForeground(g, getSize());
    }

}
