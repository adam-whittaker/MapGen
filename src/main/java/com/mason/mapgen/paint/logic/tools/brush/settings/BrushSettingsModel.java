package com.mason.mapgen.paint.logic.tools.brush.settings;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.BrushSettingsSkeleton;

import java.awt.*;

public class BrushSettingsModel{


    private final IntQuery sizeState;

    private final ColorMixer colorMixer;


    public BrushSettingsModel(BrushSettingsSkeleton skeleton){
        sizeState = skeleton.getSizeState();
        colorMixer = skeleton.getColorMixer();
    }

    public BrushSettingsModel(IntQuery sizeState, ColorMixer colorMixer){
        this.sizeState = sizeState;
        this.colorMixer = colorMixer;
    }


    public int getBrushSize(){
        return sizeState.getState();
    }

    public Color nextRandomColor(){
        return colorMixer.nextRandomColor();
    }

}
