package com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.geo.Coord;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.mapgen.paint.buildResources.MainChunkingGridBuildComponent;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.panes.topPane.resources.ImageQuery;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridQuery;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridReceiver;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.skeletons.PaintToolQuery;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.PAINT_TOOL_QUERY;

public class VersionedCanvasControllerBuildComponent extends ComponentBuildResource<VersionedCanvasController>{


    public final static VersionedCanvasControllerBuildComponent INSTANCE = new VersionedCanvasControllerBuildComponent();

    public final static SettableBuildResource<PaintGridReceiver> PAINT_GRID_RECEIVER = new SettableBuildResource<>("PAINT_GRID_RECEIVER");
    public final static SettableBuildResource<PaintGridQuery> PAINT_GRID_QUERY = new SettableBuildResource<>("PAINT_GRID_QUERY");
    public final static SettableBuildResource<ImageQuery> PAINT_IMAGE_QUERY = new SettableBuildResource<>("PAINT_IMAGE_QUERY");
    public final static SettableBuildResource<Runnable> UNDO_CALLABLE = new SettableBuildResource<>("UNDO_CALLABLE");
    public final static SettableBuildResource<Runnable> REDO_CALLABLE = new SettableBuildResource<>("REDO_CALLABLE");

    private final PaintToolQuery.PaintToolQuerySlot toolQuerySlot = new PaintToolQuery.PaintToolQuerySlot();


    private VersionedCanvasControllerBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                MainChunkingGridBuildComponent.INSTANCE
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                PAINT_GRID_RECEIVER,
                PAINT_GRID_QUERY,
                PAINT_IMAGE_QUERY,
                UNDO_CALLABLE,
                REDO_CALLABLE,
                this
        );
    }

    @Override
    public VersionedCanvasController build(){
        ChunkingGrid<PaintCentroidData> chunkingGrid = MainChunkingGridBuildComponent.INSTANCE.get();
        PaintCanvas canvas = new PaintCanvas(new Coord(0, 0), chunkingGrid);
        PAINT_GRID_RECEIVER.setResource(canvas::loadInNewPaintGrid);
        PAINT_GRID_QUERY.setResource(canvas.getGridQueryForSaving());
        PAINT_IMAGE_QUERY.setResource(canvas.getImageQueryForExporting());
        VersionedCanvasController canvasController = new VersionedCanvasController(canvas, toolQuerySlot, 100);
        UNDO_CALLABLE.setResource(canvasController::undo);
        REDO_CALLABLE.setResource(canvasController::redo);
        return canvasController;
    }

    @Override
    public void postBuild(){
        PaintToolQuery paintToolQuery = PAINT_TOOL_QUERY.get();
        toolQuerySlot.setPaintToolQuery(paintToolQuery);
    }


    @Override
    public String toString(){
        return "VERSIONED_CANVAS_CONTROLLER";
    }

}
