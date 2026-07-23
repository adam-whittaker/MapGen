package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.behaviour.camera.Zoom;
import com.mason.libgui.components.panes.construction.PanZoomPaneBuilder;
import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.geo.*;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.logic.canvas.CanvasController;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

import java.util.function.Supplier;

import static java.lang.Math.max;

public class PaintedImagePaneBuilder{


    public static PaintedImagePaneSkeleton buildSkeleton(PaintGUIStateSkeleton skeleton){
        PaintedImagePaneSkeleton paintedImagePaneSkeleton = new PaintedImagePaneSkeleton();
        paintedImagePaneSkeleton.setPanZoomPaneSkeleton(buildPanZoomPaneSkeleton(skeleton));
        return paintedImagePaneSkeleton;
    }

    private static PanZoomPaneSkeleton buildPanZoomPaneSkeleton(PaintGUIStateSkeleton skeleton){
        CanvasController canvasController = buildCanvasController(skeleton);
        HitboxRect boundary = buildPaneBoundary(skeleton);
        RectQuery initialView = constructCentredInitialView(boundary.getSize(), canvasController.getSize());
        RectQuery clampingRect = Rect.buildRect(new Coord(0, 0), canvasController.getSize());
        Zoom zoom = constructZoom(boundary.getSize(), canvasController.getSize());
        PanZoomPaneSkeleton panZoomPaneSkeleton = PanZoomPaneBuilder.buildSkeleton(boundary, initialView, clampingRect, zoom);
        canvasController.addToContainer(panZoomPaneSkeleton);
        return panZoomPaneSkeleton;
    }

    private static CanvasController buildCanvasController(PaintGUIStateSkeleton skeleton){
        PaintCanvas canvas = new PaintCanvas(new Coord(0, 0), skeleton.getMainCanvasChunkingGrid());
        Supplier<PaintTool> currentPaintToolQuery = skeleton.getCurrentPaintToolQuery();
        return new CanvasController(canvas, currentPaintToolQuery);
    }

    private static HitboxRect buildPaneBoundary(PaintGUIStateSkeleton skeleton){
        PaneLayout layout = skeleton.getLayout();
        return HitboxRect.fromRect(layout.getBounds("IMAGE_PAINT_PANE"));
    }

    private static RectQuery constructCentredInitialView(Size boundarySize, Size compSize){
        int viewX = centreDimension(boundarySize.width(), compSize.width());
        int viewY = centreDimension(boundarySize.height(), compSize.height());
        return Rect.buildRect(new Coord(viewX, viewY), boundarySize);
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


    //@Unfinished Probably a better place to place this.
    public static PaintedImagePaneSkeleton buildSkeletonWithTestComponent(HitboxRect boundary,
                                                                          UIComponent component,
                                                                          Size componentSize){
        component.setCoord(new Coord(0, 0));
        RectQuery initialView = constructCentredInitialView(boundary.getSize(), componentSize);
        RectQuery clampingRect = Rect.buildRect(new Coord(0, 0), componentSize);
        Zoom zoom = constructZoom(boundary.getSize(), componentSize);
        PanZoomPaneSkeleton panZoomPaneSkeleton = PanZoomPaneBuilder.buildSkeleton(boundary, initialView, clampingRect, zoom);
        panZoomPaneSkeleton.addComponent(component);
        PaintedImagePaneSkeleton skeleton = new PaintedImagePaneSkeleton();
        skeleton.setPanZoomPaneSkeleton(panZoomPaneSkeleton);

        return skeleton;
    }

}
