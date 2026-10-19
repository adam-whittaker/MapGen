package com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.canvas.CanvasController;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.BrushTool;
import com.mason.mapgen.paint.skeletons.PaintToolQuery;

public class PaletteCanvasController extends CanvasController{


    PaletteCanvasController(PaintCanvas canvas, PaintToolQuery toolQuery){
        super(canvas, toolQuery);
    }


    @Override
    public void onMouseReleased(MouseInputEvent event){
        event.setCoordRelativeTo(getCoord());
        super.onMouseReleased(event);
    }

    @Override
    public void onMousePressed(MouseInputEvent event){
        event.setCoordRelativeTo(getCoord());
        PaintTool currentTool = getCurrentPaintTool();
        if(currentTool instanceof BrushTool){
            onMousePressedWithTemporaryBrushSettings(event);
        }else{
            super.onMousePressed(event);
        }
    }

    //@Unfinished refactor
    private void onMousePressedWithTemporaryBrushSettings(MouseInputEvent event){
        //double originalCertainty = certainty.getPosition();
        //int originalAlpha = alpha.getState();
        //certainty.setPosition(1);
        //alpha.setState(255);
        super.onMousePressed(event);
        //certainty.setPosition(originalCertainty);
        //alpha.setState(originalAlpha);
    }

}
