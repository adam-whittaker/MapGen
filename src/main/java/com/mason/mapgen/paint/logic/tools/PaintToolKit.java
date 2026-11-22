package com.mason.mapgen.paint.logic.tools;

import com.mason.libstruct.setters.QuerySetter;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.mapgen.paint.logic.tools.brush.*;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

public class PaintToolKit{


    private final BrushTool brush;
    private final Eraser eraser;
    private final ColorPicker colorPicker;
    private final OnOffQuery colorPickerQuery;
    private final OnOffQuery eraserQuery;


    public PaintToolKit(PaintToolKitSkeleton skeleton){
        QuerySetter<PaintTool> paintToolQuerySetter = skeleton.getPaintToolQuerySetter();
        paintToolQuerySetter.setQuery(this::getCurrentTool);
        this.brush = new BrushTool(
                skeleton.getCurrentLayerQuery(),
                skeleton.getBrushSizeQuery(),
                skeleton.getSetColorQuery(),
                skeleton.getColorMixer()
        );
        this.eraserQuery = skeleton.getEraserQuery();
        this.eraser = new Eraser(
                skeleton.getCurrentLayerQuery(),
                skeleton.getBrushSizeQuery()
        );
        this.colorPickerQuery = skeleton.getColorPickerQuery();
        this.colorPicker = new ColorPicker(
                skeleton.getPrimaryColorState(),
                skeleton.getSecondaryColorState(),
                skeleton.getColorSetterSlot()
        );
    }

    private PaintTool getCurrentTool(){
        if(colorPickerQuery.isOn()){
            return colorPicker;
        }
        if(eraserQuery.isOn()){
            return eraser;
        }
        return brush;
    }

}
