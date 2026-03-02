package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.PaintCanvas;

public interface PaintTool{


    void apply(PaintCanvas canvas, MouseInputEvent event);

    void releaseTool();

    boolean isActive();

    boolean acceptMouseInput(MouseInputEvent event);

}
