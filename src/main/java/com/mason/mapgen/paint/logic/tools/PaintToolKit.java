package com.mason.mapgen.paint.logic.tools;

import com.mason.mapgen.paint.logic.tools.brush.BrushToolAdapter;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

public class PaintToolKit{


    private final BrushToolAdapter brushAdapter;
    private final ColorPicker colorPicker;
    private PaintTool currentTool;


    public PaintToolKit(int numBrushes){
        this.brushAdapter = new BrushToolAdapter(numBrushes);
        this.currentTool = brushAdapter;
        this.colorPicker = new ColorPicker(brushAdapter);
    }


    public PaintTool getCurrentTool(){
        return currentTool;
    }

    public void setToolToBrushWithNumber(int number){
        currentTool = brushAdapter;
        brushAdapter.setCurrentBrushIndex(number);
    }

    public void setToolToColorPicker(){
        currentTool = colorPicker;
    }

}
