package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;

public interface PaintTool{


    void apply(PaintCanvas canvas, MouseInputEvent event);

    void releaseTool();

    boolean isActive();

    PaintAction obtainAction();

    boolean shouldAcceptMouseInput(MouseInputEvent event);

}
