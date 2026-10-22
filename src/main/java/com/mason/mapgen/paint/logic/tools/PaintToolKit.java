package com.mason.mapgen.paint.logic.tools;

import com.mason.libstruct.states.onOff.OnOffState;
import com.mason.mapgen.paint.logic.tools.brush.*;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

public class PaintToolKit{


    private final BrushTool brush;
    private final ColorPicker colorPicker;
    private final OnOffState colorPickerState;


    public PaintToolKit(PaintToolKitSkeleton skeleton){
        skeleton.getPaintToolQuerySetter().setQuery(this::getCurrentTool);
        this.brush = new BrushTool(skeleton.getBrushSizeQuery(), skeleton.getColorMixer());
        this.colorPickerState = skeleton.getColorPickerState();
        this.colorPicker = new ColorPicker(
                skeleton.getPrimaryColorState(),
                skeleton.getSecondaryColorState(),
                skeleton.getColorSetterSlot()
        );
    }

    private PaintTool getCurrentTool(){
        if(colorPickerState.isOn()){
            return colorPicker;
        }
        return brush;
    }

}
