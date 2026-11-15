package com.mason.mapgen.paint.logic.keyProcessor;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.REDO_CALLABLE;
import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.UNDO_CALLABLE;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.rightPane.referenceButtons.ReferenceTweakablesBuildComponent.GRAB_REFERENCE_ONLY_STATE;

public class PaintKeyProcessorBuildComponent extends ComponentBuildResource<PaintKeyProcessor>{


    public final static PaintKeyProcessorBuildComponent INSTANCE = new PaintKeyProcessorBuildComponent();


    private PaintKeyProcessorBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                COLOR_PICKER_STATE,
                GRAB_REFERENCE_ONLY_STATE,
                BRUSH_NUM_STATE,
                NUM_BRUSHES,
                REDO_CALLABLE,
                UNDO_CALLABLE
        );
    }

    @Override
    public PaintKeyProcessor build(){
        PaintKeyProcessorSkeleton skeleton = new PaintKeyProcessorSkeleton();
        skeleton.setColorPickerState(COLOR_PICKER_STATE.get());
        skeleton.setBrushNumState(BRUSH_NUM_STATE.get());
        skeleton.setGrabReferenceOnlyState(GRAB_REFERENCE_ONLY_STATE.get());
        skeleton.setNumBrushes(NUM_BRUSHES.get());
        skeleton.setRedoCallable(REDO_CALLABLE.get());
        skeleton.setUndoCallable(UNDO_CALLABLE.get());

        return new PaintKeyProcessor(skeleton);
    }

    @Override
    public void postBuild(){

    }


    @Override
    public String toString(){
        return "PAINT_KEY_PROCESSOR";
    }

}
