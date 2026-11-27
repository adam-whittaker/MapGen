package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.behaviour.camera.Zoom;
import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.construction.PanZoomPaneFactory;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.core.component.hitbox.DynamicRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageComponent;
import com.mason.mapgen.paint.logic.canvas.CanvasController;

import java.util.Set;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.PAINT_GUI_PARAMS;
import static java.lang.Math.max;
import static java.lang.Math.min;

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
        Zoom zoom = constructZoom(boundary.getSize(), canvasController.getSize()); //TODO make this dependent on the currently loaded image.
        RectQuery clampingRect = buildClampingRect(canvasController::getSize, boundary, zoom::getZoom);

        PanZoomPaneFactory<PaintedImagePane> paneFactory = new PanZoomPaneFactory<>(PaintedImagePane::new);
        PaintedImagePane pane = paneFactory.build(boundary, clampingRect, initialView, zoom);
        canvasController.addToContainer(pane);
        return pane;
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

    private static RectQuery buildClampingRect(
            Supplier<Size> canvasSizeQuery,
            RectQuery paneBoundary,
            DoubleSupplier zoomQuery
    ){
        return DynamicRect.buildWithStaticCoord(
                new Coord(0, 0),
                () -> {
                    Size canvasSize = canvasSizeQuery.get();
                    double zoom = zoomQuery.getAsDouble();
                    double viewW = paneBoundary.width() / zoom;
                    double viewH = paneBoundary.height() / zoom;
                    return new Size(
                            (int) Math.ceil(max(viewW, canvasSize.width())),
                            (int) Math.ceil(max(viewH, canvasSize.height()))
                    );
                }
        );
    }

    private static int centreDimension(int boundaryDim, int compDim){
        if(compDim > boundaryDim){
            return (compDim - boundaryDim)/2;
        }
        return (boundaryDim - compDim)/2;
    }

    private static Zoom constructZoom(Size boundarySize, Size compSize){
        int zoomLevels = PAINT_GUI_PARAMS.get().zoomLevels();
        int zoomIncrements = PAINT_GUI_PARAMS.get().zoomRange();
        double minZoom = calculateMinZoom(boundarySize, compSize);
        double maxZoom = minZoom * zoomIncrements;
        double initialZoom = max(1, minZoom);
        return Zoom.buildZoom(minZoom, maxZoom, zoomLevels, initialZoom);
    }

    private static double calculateMinZoom(Size boundarySize, Size compSize){
        double widthFactor = (double)boundarySize.width()/compSize.width();
        double heightFactor = (double)boundarySize.height()/compSize.height();
        return min(widthFactor, heightFactor);
    }


    @Override
    public void postBuild(){
        Pane pane = this.get();

        ReferenceImageComponent referenceImageComponent = ReferenceImageBuildComponent.INSTANCE.get();
        pane.addComponent(referenceImageComponent);
    }

    @Override
    public String toString(){
        return "PAINTED_IMAGE_PANE";
    }

}
