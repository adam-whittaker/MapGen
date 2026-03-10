package com.mason.mapgen.paint.logic.tools.colorPicker;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.DualColorable;

import java.awt.*;

public class ColorPicker implements PaintTool{


    private boolean active = false;
    private final DualColorable colorable;


    public ColorPicker(DualColorable colorable){
        this.colorable = colorable;
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
            colorable.setSecondaryColor(color);
            return;
        }
        colorable.setPrimaryColor(color);
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
    public boolean shouldAcceptMouseInput(MouseInputEvent event){
        return true;
    }

}
