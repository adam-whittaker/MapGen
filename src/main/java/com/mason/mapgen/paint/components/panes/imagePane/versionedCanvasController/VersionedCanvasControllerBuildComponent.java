package com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.setters.QuerySetter;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.imagePane.MainPaintGridBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.canvas.PersistentLayeredPaintCanvas;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;
import com.mason.mapgen.paint.logic.tools.PaintTool;

import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.PAINT_TOOL_QUERY;
import static com.mason.mapgen.paint.components.panes.rightPane.layersPane.PaintLayersPaneBuildComponent.CURRENT_LAYER_STATE;

public class VersionedCanvasControllerBuildComponent extends ComponentBuildResource<VersionedCanvasController>{


    public final static VersionedCanvasControllerBuildComponent INSTANCE = new VersionedCanvasControllerBuildComponent();

    public final static SettableBuildResource<Runnable> UNDO_CALLABLE = new SettableBuildResource<>("UNDO_CALLABLE");
    public final static SettableBuildResource<Runnable> REDO_CALLABLE = new SettableBuildResource<>("REDO_CALLABLE");

    private final static CanvasViewSlot CANVAS_VIEW_SLOT = new CanvasViewSlot();
    public final static BuildResource<Consumer<PaintGridSaveData>> SAVE_DATA_LOADER = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::loadSaveData, "SAVE_DATA_LOADER");
    public final static BuildResource<Supplier<PaintGridSaveData>> SAVE_DATA_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getSaveData, "SAVE_DATA_QUERY");
    public final static BuildResource<Supplier<BufferedImage>> PAINT_IMAGE_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getPaintImage, "PAINT_IMAGE_QUERY");
    public static final BuildResource<IntQuery> LAYER_COUNT_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getLayerCount, "LAYER_COUNT_QUERY");
    public static final BuildResource<IntQuery> CURRENT_LAYER_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getCurrentLayer, "CURRENT_LAYER_QUERY");
    public static final BuildResource<Supplier<LayerManager>> LAYER_MANAGER_QUERY = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::getLayerManager, "LAYER_MANAGER_QUERY");
    public static final BuildResource<Runnable> CREATE_LAYER_UPDATE = BuildResource.getPreBuiltResource(CANVAS_VIEW_SLOT::createLayer, "CREATE_LAYER_UPDATE");

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
                SAVE_DATA_LOADER,
                SAVE_DATA_QUERY,
                PAINT_IMAGE_QUERY,
                LAYER_COUNT_QUERY,
                CURRENT_LAYER_QUERY,
                LAYER_MANAGER_QUERY,
                CREATE_LAYER_UPDATE,
                UNDO_CALLABLE,
                REDO_CALLABLE,
                INSTANCE
        );
    }

    @Override
    public VersionedCanvasController build(){
        PaintGrid paintGrid = MainPaintGridBuildComponent.INSTANCE.get();
        PaintCanvas canvas = new PersistentLayeredPaintCanvas(paintGrid, CANVAS_VIEW_SLOT);
        VersionedCanvasController canvasController = new VersionedCanvasController(canvas, toolQuerySlot, 100);
        UNDO_CALLABLE.setResource(canvasController::undo);
        REDO_CALLABLE.setResource(canvasController::redo);
        return canvasController;
    }

    @Override
    public void postBuild(){
        Supplier<PaintTool> paintToolQuery = PAINT_TOOL_QUERY.get();
        toolQuerySlot.setQuery(paintToolQuery);

        IntState currentLayerState = CURRENT_LAYER_STATE.get();
        CANVAS_VIEW_SLOT.setCurrentLayerQuery(currentLayerState::getState);
        CANVAS_VIEW_SLOT.setCurrentLayerResetter(() -> currentLayerState.setState(0));
    }


    @Override
    public String toString(){
        return "VERSIONED_CANVAS_CONTROLLER";
    }

}
