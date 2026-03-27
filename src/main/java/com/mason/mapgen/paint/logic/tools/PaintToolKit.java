package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.utils.structures.states.onOff.OnOffState;
import com.mason.mapgen.paint.logic.tools.brush.*;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

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

    private PaintTool getCurrentTool(){
        if(colorPickerState.isOn()){
            return colorPicker;
        }
        return brush;
    }

}
