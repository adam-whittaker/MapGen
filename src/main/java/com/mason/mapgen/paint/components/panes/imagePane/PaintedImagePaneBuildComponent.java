package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.behaviour.camera.Zoom;
import com.mason.libgui.components.panes.construction.PanZoomPaneBuilder;
import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent;
import com.mason.mapgen.paint.logic.canvas.CanvasController;

import java.util.Set;

import static java.lang.Math.max;

public class PaintedImagePaneBuildComponent extends ComponentBuildResource<PaintedImagePane>{


    public final static PaintedImagePaneBuildComponent INSTANCE = new PaintedImagePaneBuildComponent();


    private PaintedImagePaneBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(VersionedCanvasControllerBuildComponent.INSTANCE,
                PaintGUIStateLayoutBuildComponent.INSTANCE);
    }

    @Override
    public PaintedImagePane build(){
        CanvasController canvasController = VersionedCanvasControllerBuildComponent.INSTANCE.get();
        HitboxRect boundary = buildPaneBoundary();
        RectQuery initialView = constructCentredInitialView(boundary.getSize(), canvasController.getSize());
        RectQuery clampingRect = Rect.buildRect(new Coord(0, 0), canvasController.getSize());
        Zoom zoom = constructZoom(boundary.getSize(), canvasController.getSize());
        PanZoomPaneSkeleton panZoomPaneSkeleton = PanZoomPaneBuilder.buildSkeleton(boundary, initialView, clampingRect, zoom);
        canvasController.addToContainer(panZoomPaneSkeleton);
        return new PaintedImagePane(panZoomPaneSkeleton);
    }

    private static HitboxRect buildPaneBoundary(){
        PaneLayout layout = PaintGUIStateLayoutBuildComponent.INSTANCE.get();
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


    @Override
    public String toString(){
        return "PAINTED_IMAGE_PANE";
    }

}
