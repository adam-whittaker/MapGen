package com.mason.mapgen.paint.logic.keyProcessor;

import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libstruct.states.onOff.OnOffState;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.COLOR_PICKER_STATE;

public class PaintKeyProcessorBuildComponent extends ComponentBuildResource<PaintKeyProcessor>{


    public final static PaintKeyProcessorBuildComponent INSTANCE = new PaintKeyProcessorBuildComponent();


    private PaintKeyProcessorBuildComponent(){
        super();
    }

    @Override
    public PaintKeyProcessor build(){
       return new PaintKeyProcessor();
    }

    @Override
    public void postBuild(){
        OnOffState colorPickerState = COLOR_PICKER_STATE.get();
        this.get().registerColorPickerState(colorPickerState);
    }


    @Override
    public String toString(){
        return "PAINT_KEY_PROCESSOR";
    }

}
