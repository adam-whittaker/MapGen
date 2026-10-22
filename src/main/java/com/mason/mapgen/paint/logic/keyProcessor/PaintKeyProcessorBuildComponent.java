package com.mason.mapgen.paint.logic.keyProcessor;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.onOff.OnOffState;

import java.util.Set;

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
                NUM_BRUSHES
        );
    }

    @Override
    public PaintKeyProcessor build(){
        OnOffState colorPickerState = COLOR_PICKER_STATE.get();
        OnOffState grabReferenceOnlyState = GRAB_REFERENCE_ONLY_STATE.get();
        IntState brushNumState = BRUSH_NUM_STATE.get();
        int numBrushes = NUM_BRUSHES.get();
        return new PaintKeyProcessor(colorPickerState, grabReferenceOnlyState, brushNumState, numBrushes);
    }

    @Override
    public void postBuild(){

    }


    @Override
    public String toString(){
        return "PAINT_KEY_PROCESSOR";
    }

}
