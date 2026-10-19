package com.mason.mapgen.paint.logic.tools;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.onOff.OnOffState;
import com.mason.mapgen.paint.logic.tools.brush.*;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;
import com.mason.mapgen.paint.skeletons.PaintToolQuery;

public class PaintToolKit{


    private final BrushTool brush;
    private final ColorPicker colorPicker;
    private final OnOffState colorPickerState;


    public PaintToolKit(PaintControlSettingsSkeleton skeleton){
        skeleton.setPaintToolKit(this);
        skeleton.setCurrentPaintToolQuery(this::getCurrentTool);
        this.brush = new BrushTool(skeleton);
        this.colorPickerState = skeleton.getColorPickerState();
        this.colorPicker = new ColorPicker(skeleton.getPrimaryColorState(), skeleton.getSecondaryColorState());
    }

    public PaintToolKit(PaintToolQuery.PaintToolQuerySetter paintToolQuerySetter,
                        IntQuery sizeQuery,
                        ColorMixer colorMixer,
                        OnOffState colorPickerState,
                        RGBState primaryColorState,
                        RGBState secondaryColorState){
        paintToolQuerySetter.setPaintToolQuery(this::getCurrentTool);
        this.brush = new BrushTool(sizeQuery, colorMixer);
        this.colorPickerState = colorPickerState;
        this.colorPicker = new ColorPicker(primaryColorState, secondaryColorState);
    }

    private PaintTool getCurrentTool(){
        if(colorPickerState.isOn()){
            return colorPicker;
        }
        return brush;
    }

}
