package com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.libgui.utils.structures.states.position.PositionState;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.logic.canvas.CanvasController;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.BrushTool;

public class PaletteCanvasController extends CanvasController{


    private final IntState alpha;
    private final PositionState certainty;


    public PaletteCanvasController(PaletteCanvasControllerSkeleton skeleton){
        super(skeleton.getCanvas(), skeleton.getCurrentToolQuery());
        alpha = skeleton.getAlphaState();
        certainty = skeleton.getCertaintyState();
    }

    public static PaletteCanvasController build(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        return new PaletteCanvasController(PaletteCanvasControllerBuilder.buildSkeleton(leftPaintPaneSkeleton));
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

    private void onMousePressedWithTemporaryBrushSettings(MouseInputEvent event){
        double originalCertainty = certainty.getPosition();
        int originalAlpha = alpha.getState();
        certainty.setPosition(1);
        alpha.setState(255);
        super.onMousePressed(event);
        certainty.setPosition(originalCertainty);
        alpha.setState(originalAlpha);
    }

}
