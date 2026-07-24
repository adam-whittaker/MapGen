package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.canvas.CanvasController;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;
import com.mason.mapgen.paint.logic.history.PaintHistory;
import com.mason.mapgen.paint.logic.tools.PaintTool;

import java.util.function.Supplier;

public class VersionedCanvasController extends CanvasController{


    private final PaintHistory history;


    public VersionedCanvasController(PaintCanvas canvas, Supplier<PaintTool> currentToolQuery, int maxHistoryLength){
        super(canvas, currentToolQuery);
        this.history = new PaintHistory(canvas, maxHistoryLength);
    }


    @Override
    public void onMouseReleased(MouseInputEvent event){
        PaintTool currentTool = getCurrentPaintTool();
        if(currentTool.isActive()){
            currentTool.releaseTool();
            PaintAction action = currentTool.obtainAction();
            history.registerAction(action);
        }
    }


    void undo(){
        history.undo();
    }

    void redo(){
        history.redo();
    }

}
