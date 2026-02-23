package com.mason.mapgen.paint.builders;

import com.mason.libgui.components.behaviour.camera.Zoom;
import com.mason.libgui.components.panes.PanZoomPaneBuilder;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.RectQuery;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.PaintedImageComponent;
import com.mason.mapgen.paint.components.panes.PaintedImagePane;

import static java.lang.Math.max;

public class PaintedImagePaneBuilder{


    public static PaintedImagePane build(PanZoomPaneBuilder.PaneConstructor<PaintedImagePane> constructor,
                                         HitboxRect boundary,
                                         PaintedImageComponent component){
        PanZoomPaneBuilder<PaintedImagePane> builder = new PanZoomPaneBuilder<>(constructor);
        component.setCoord(new Coord(0, 0));
        RectQuery initialView = constructCentredInitialView(boundary.getSize(), component.getSize());
        RectQuery clampingRect = new HitboxRect(new Coord(0, 0), component.getSize());
        Zoom zoom = constructZoom(boundary.getSize(), component.getSize());
        PaintedImagePane pane = builder.build(boundary, initialView, clampingRect, zoom);
        pane.addComponent(component);
        return pane;
    }

    public static PaintedImagePane buildWithTestComponent(PanZoomPaneBuilder.PaneConstructor<PaintedImagePane> constructor,
                                          HitboxRect boundary,
                                          UIComponent component,
                                          Size componentSize){
        PanZoomPaneBuilder<PaintedImagePane> builder = new PanZoomPaneBuilder<>(constructor);
        component.setCoord(new Coord(0, 0));
        RectQuery initialView = constructCentredInitialView(boundary.getSize(), componentSize);
        RectQuery clampingRect = new HitboxRect(new Coord(0, 0), componentSize);
        Zoom zoom = constructZoom(boundary.getSize(), componentSize);
        PaintedImagePane pane = builder.build(boundary, initialView, clampingRect, zoom);
        pane.addComponent(component);
        return pane;
    }

    private static RectQuery constructCentredInitialView(Size boundarySize, Size compSize){
        int viewX = centreDimension(boundarySize.width(), compSize.width());
        int viewY = centreDimension(boundarySize.height(), compSize.height());
        return new HitboxRect(new Coord(viewX, viewY), boundarySize);
    }

    private static int centreDimension(int boundaryDim, int compDim){
        if(compDim > boundaryDim){
            return (compDim - boundaryDim)/2;
        }
        return (boundaryDim - compDim)/2;
    }

    private static Zoom constructZoom(Size boundarySize, Size compSize){
        double minZoom = calculateMinZoom(boundarySize, compSize);
        double maxZoom = minZoom * 16;
        double initialZoom = max(1, minZoom);
        return Zoom.buildZoom(minZoom, maxZoom, 16, initialZoom);
    }

    private static double calculateMinZoom(Size boundarySize, Size compSize){
        double widthFactor = (double)boundarySize.width()/compSize.width();
        double heightFactor = (double)boundarySize.height()/compSize.height();
        return max(widthFactor, heightFactor);
    }

}
