package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;

import java.awt.*;

public class BrushTool extends AbstractBrushTool{


    private final ColorMixer colorMixer;
    private final OnOffQuery setColorQuery;


    public BrushTool(IntQuery currentLayerQuery, IntQuery sizeQuery, OnOffQuery setColorQuery, ColorMixer colorMixer){
        super(currentLayerQuery, sizeQuery);
        this.setColorQuery = setColorQuery;
        this.colorMixer = colorMixer;
    }


    @Override
    public void changeColorOfChunk(PaintCanvas canvas, Short centroidID){
        Color paintColor = colorMixer.nextRandomColor();
        if(setColorQuery.isOn()){
            canvas.setChunkColor(paintColor, centroidID);
        }else{
            canvas.paintChunkColor(paintColor, centroidID);
        }
    }

}
