package com.mason.mapgen.paint.components.panes;

import com.mason.libgui.components.panes.PanZoomPaneSkeleton;
import com.mason.libgui.components.panes.RenderReadyPanZoomPane;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.builders.PaintedImagePaneBuilder;
import com.mason.mapgen.paint.components.PaintedImageComponent;

import java.awt.*;

public class PaintedImagePane extends RenderReadyPanZoomPane{


    private static final Color BACKGROUND_COLOR = new Color(40, 40, 40);


    private PaintedImagePane(PanZoomPaneSkeleton skeleton){
        super(skeleton);
    }

    public static PaintedImagePane build(HitboxRect boundary, PaintedImageComponent component){
        return PaintedImagePaneBuilder.build(PaintedImagePane::new, boundary, component);
    }

    public static PaintedImagePane buildWithTestComponent(HitboxRect boundary, UIComponent component, Size componentSize){
        return PaintedImagePaneBuilder.buildWithTestComponent(PaintedImagePane::new, boundary, component, componentSize);
    }


    @Override
    protected void drawBackground(Graphics2D g){
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getSize().width(), getSize().height());
    }

    @Override
    protected void drawForeground(Graphics2D g){

    }

}
