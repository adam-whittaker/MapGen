package com.mason.mapgen.paint.components.panes.rightPane.reference;

import com.mason.libgui.components.behaviour.camera.Zoom;
import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.construction.PanZoomPaneBuilder;
import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent;
import com.mason.mapgen.paint.logic.canvas.CanvasController;

import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class ReferenceImagePaneBuildComponent extends ComponentBuildResource<ReferenceImagePane>{


    public final static ReferenceImagePaneBuildComponent INSTANCE = new ReferenceImagePaneBuildComponent();
    public static final SettableBuildResource<RectQuery> CLAMPING_RECT = new SettableBuildResource<>("REFERENCE_IMAGE_PANE_CLAMPING_RECT");

    public static final BuildResource<ImageUpdate> REFERENCE_IMAGE_UPDATE;
    public static final BuildResource<ImageUpdate.ImageUpdateSetter> REFERENCE_IMAGE_UPDATE_SETTER;
    static{
        ImageUpdate.ImageUpdateSlot updateSlot = new ImageUpdate.ImageUpdateSlot();
        REFERENCE_IMAGE_UPDATE = BuildResource.getPreBuiltResource(updateSlot, "REFERENCE_IMAGE_UPDATE");
        REFERENCE_IMAGE_UPDATE_SETTER = BuildResource.getPreBuiltResource(updateSlot, "REFERENCE_IMAGE_UPDATE_SETTER");
    }


    private ReferenceImagePaneBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                VersionedCanvasControllerBuildComponent.INSTANCE,
                PaintGUIStateLayoutBuildComponent.INSTANCE
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                INSTANCE,
                CLAMPING_RECT,
                REFERENCE_IMAGE_UPDATE,
                REFERENCE_IMAGE_UPDATE_SETTER
        );
    }

    @Override
    public ReferenceImagePane build(){
        RectQuery clampingRect = buildClampingRect();
        HitboxRect boundary = buildPaneBoundary();
        RectQuery initialView = constructCentredInitialView(boundary.getSize(), clampingRect.getSize());
        Zoom zoom = constructZoom(boundary.getSize(), clampingRect.getSize());
        PanZoomPaneSkeleton panZoomPaneSkeleton = PanZoomPaneBuilder.buildSkeletonWithUnclampedBehaviour(boundary, initialView, clampingRect, zoom);
        CLAMPING_RECT.setResource(clampingRect);
        return new ReferenceImagePane(panZoomPaneSkeleton);
    }

    private RectQuery buildClampingRect(){
        CanvasController canvasController = VersionedCanvasControllerBuildComponent.INSTANCE.get();
        Size canvasSize = canvasController.getSize();
        int scaleFactor = 1;
        return Rect.buildRect(
                new Coord(0, 0),
                new Size(canvasSize.width()*scaleFactor, canvasSize.height()*scaleFactor)
        );
    }

    private HitboxRect buildPaneBoundary(){
        PaneLayout layout = PaintGUIStateLayoutBuildComponent.INSTANCE.get();
        return HitboxRect.fromRect(layout.getBounds("IMAGE_PAINT_PANE"));
    }

    private RectQuery constructCentredInitialView(Size boundarySize, Size compSize){
        int viewX = centreDimension(boundarySize.width(), compSize.width());
        int viewY = centreDimension(boundarySize.height(), compSize.height());
        return Rect.buildRect(new Coord(viewX, viewY), boundarySize);
    }

    private int centreDimension(int boundaryDim, int compDim){
        return abs(boundaryDim - compDim)/2;
    }

    private Zoom constructZoom(Size boundarySize, Size compSize){
        double minZoom = calculateMinZoom(boundarySize, compSize);
        double maxZoom = minZoom * 48;
        double initialZoom = max(1, minZoom);
        return Zoom.buildZoom(minZoom, maxZoom, 24, initialZoom);
    }

    private double calculateMinZoom(Size boundarySize, Size compSize){
        double widthFactor = (double)boundarySize.width()/compSize.width();
        double heightFactor = (double)boundarySize.height()/compSize.height();
        return max(widthFactor, heightFactor);
    }


    @Override
    public void postBuild(){
        Pane pane = this.get();
        pane.addComponent(ReferenceImageBuildComponent.INSTANCE.get());
    }

    @Override
    public String toString(){
        return "REFERENCE_IMAGE_PANE";
    }

}
