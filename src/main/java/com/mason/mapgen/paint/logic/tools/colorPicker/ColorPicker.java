package com.mason.mapgen.paint.logic.tools.colorPicker;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.BrushColor;

import java.awt.*;

public class ColorPicker implements PaintTool{


    private final BrushColor brushColor;
    private boolean active = false;


    public ColorPicker(BrushColor brushColor){
        this.brushColor = brushColor;
    }


    @Override
    public void apply(PaintCanvas canvas, MouseInputEvent event){
        if(!active){
            active = true;
            chooseColor(canvas, event);
        }
    }

    private void chooseColor(PaintCanvas canvas, MouseInputEvent event){
        Color color = canvas.getChunkColor(event.getCoord());
        if(secondaryColorMask(event)){
            brushColor.setSecondaryColor(color);
            return;
        }
        brushColor.setPrimaryColor(color);
    }

    public static boolean secondaryColorMask(MouseInputEvent event){
        return event.isMouseButtonTwoDown();
    }

    @Override
    public void releaseTool(){
        active = false;
    }

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public boolean acceptMouseInput(MouseInputEvent event){
        return true;
    }

}
