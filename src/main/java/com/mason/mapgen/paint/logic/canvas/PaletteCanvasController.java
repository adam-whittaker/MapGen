package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.BrushTool;

import java.util.function.Supplier;

public class PaletteCanvasController extends CanvasController{


    public PaletteCanvasController(PaintCanvas canvas, Supplier<PaintTool> currentToolQuery){
        super(canvas, currentToolQuery);
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
        if(currentTool instanceof BrushTool brush){
            onMousePressedWithTemporaryBrushSettings(brush, event);
        }else{
            super.onMousePressed(event);
        }
    }

    private void onMousePressedWithTemporaryBrushSettings(BrushTool brush, MouseInputEvent event){
        double certainty = brush.getCertainty();
        int alpha = brush.getAlpha();
        brush.setCertainty(1);
        brush.setAlpha(255);
        super.onMousePressed(event);
        brush.setCertainty(certainty);
        brush.setAlpha(alpha);
    }

}
