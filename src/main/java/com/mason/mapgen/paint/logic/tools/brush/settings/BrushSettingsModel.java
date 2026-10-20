package com.mason.mapgen.paint.logic.tools.brush.settings;

import com.mason.libstruct.states.intState.IntQuery;

import java.awt.*;

public class BrushSettingsModel{


    private final IntQuery sizeState;

    private final ColorMixer colorMixer;


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
