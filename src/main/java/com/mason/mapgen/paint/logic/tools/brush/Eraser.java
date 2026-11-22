package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;

import java.awt.*;

public class Eraser extends AbstractBrushTool{


    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);


    public Eraser(IntQuery currentLayerQuery, IntQuery sizeQuery){
        super(currentLayerQuery, sizeQuery);
    }

    @Override
    protected void changeColorOfChunk(PaintCanvas canvas, Short centroidID){
        canvas.setChunkColor(TRANSPARENT, centroidID);
    }

}
