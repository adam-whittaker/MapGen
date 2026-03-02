package com.mason.mapgen.paint.components.panes;

import com.mason.libgui.components.panes.PanZoomPane;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.builders.PaintedImagePaneBuilder;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.paint.skeletons.PaintedImagePaneSkeleton;

import java.awt.*;

public class PaintedImagePane extends PanZoomPane{


    private static final Color BACKGROUND_COLOR = new Color(40, 40, 40);

    private final GridImageComponent paintedImageComponent;


    private PaintedImagePane(PaintedImagePaneSkeleton skeleton){
        super(skeleton.getPanZoomPaneSkeleton());
        paintedImageComponent = skeleton.getPaintedImageComponent();
    }

    public static PaintedImagePane build(HitboxRect boundary, GridImageComponent component){
        return new PaintedImagePane(PaintedImagePaneBuilder.buildSkeleton(boundary, component));
    }

    public static PaintedImagePane buildWithTestComponent(HitboxRect boundary, UIComponent component, Size componentSize){
        return new PaintedImagePane(PaintedImagePaneBuilder.buildSkeletonWithTestComponent(boundary, component, componentSize));
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
