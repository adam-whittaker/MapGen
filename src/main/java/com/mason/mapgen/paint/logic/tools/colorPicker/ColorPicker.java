package com.mason.mapgen.paint.logic.tools.colorPicker;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;

import java.awt.*;

public class ColorPicker implements PaintTool{


    private final RGBState primaryColor;
    private final RGBState secondaryColor;
    private boolean active = false;



    public ColorPicker(RGBState primaryColor, RGBState secondaryColor){
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
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
            secondaryColor.setColor(color);
            return;
        }
        primaryColor.setColor(color);
    }

    public static boolean secondaryColorMask(MouseInputEvent event){
        return event.isMouseButtonTwoDown();
    }

    @Override
    public void releaseTool(){
        active = false;
    }

    @Override
    public PaintAction obtainAction(){
        return PaintAction.EMPTY_ACTION;
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
