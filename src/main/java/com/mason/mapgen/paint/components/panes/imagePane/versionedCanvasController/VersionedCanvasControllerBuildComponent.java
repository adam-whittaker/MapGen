package com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.geo.Coord;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.imagePane.MainPaintGridBuildComponent;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.setters.QuerySetter;

import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.PAINT_TOOL_QUERY;

public class VersionedCanvasControllerBuildComponent extends ComponentBuildResource<VersionedCanvasController>{


    public final static VersionedCanvasControllerBuildComponent INSTANCE = new VersionedCanvasControllerBuildComponent();

    public final static SettableBuildResource<Runnable> UNDO_CALLABLE = new SettableBuildResource<>("UNDO_CALLABLE");
    public final static SettableBuildResource<Runnable> REDO_CALLABLE = new SettableBuildResource<>("REDO_CALLABLE");

    private final static CanvasViewSlot CANVAS_VIEW_SLOT = new CanvasViewSlot();
    public final static BuildResource<Consumer<PaintGrid>> PAINT_GRID_LOADER = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::loadPaintGrid, "PAINT_GRID_LOADER");
    public final static BuildResource<Supplier<PaintGrid>> PAINT_GRID_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getPaintGrid, "PAINT_GRID_QUERY");
    public final static BuildResource<Supplier<BufferedImage>> PAINT_IMAGE_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getPaintImage, "PAINT_IMAGE_QUERY");

    private final QuerySetter.QuerySlot<PaintTool> toolQuerySlot = new QuerySetter.QuerySlot<>();


    private VersionedCanvasControllerBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                MainPaintGridBuildComponent.INSTANCE
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                PAINT_GRID_LOADER,
                PAINT_GRID_QUERY,
                PAINT_IMAGE_QUERY,
                UNDO_CALLABLE,
                REDO_CALLABLE,
                INSTANCE
        );
    }

    @Override
    public VersionedCanvasController build(){
        PaintGrid paintGrid = MainPaintGridBuildComponent.INSTANCE.get();
        PaintCanvas canvas = new PaintCanvas(new Coord(0, 0), paintGrid, CANVAS_VIEW_SLOT);
        VersionedCanvasController canvasController = new VersionedCanvasController(canvas, toolQuerySlot, 100);
        UNDO_CALLABLE.setResource(canvasController::undo);
        REDO_CALLABLE.setResource(canvasController::redo);
        return canvasController;
    }

    @Override
    public void postBuild(){
        Supplier<PaintTool> paintToolQuery = PAINT_TOOL_QUERY.get();
        toolQuerySlot.setQuery(paintToolQuery);
    }


    @Override
    public String toString(){
        return "VERSIONED_CANVAS_CONTROLLER";
    }

}
