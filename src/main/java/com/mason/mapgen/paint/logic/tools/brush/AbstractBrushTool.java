package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;
import com.mason.mapgen.paint.logic.tools.PaintTool;

import java.awt.*;

public abstract class AbstractBrushTool implements PaintTool{


    private BrushStroke stroke;
    private boolean active;

    private final IntQuery currentLayerQuery;
    private final IntQuery brushSizeQuery;


    public AbstractBrushTool(IntQuery currentLayerQuery, IntQuery brushSizeQuery){
        this.currentLayerQuery = currentLayerQuery;
        this.brushSizeQuery = brushSizeQuery;
        active = false;
    }


    @Override
    public void apply(PaintCanvas canvas, MouseInputEvent event){
        if(!active){
            startBrush(currentLayerQuery.getState());
        }
        Short centroidID = canvas.getCentroidIDFromCoord(event.getCoord());
        Iterable<Short> recoloredCentroids = canvas.centroidNeighbourhood(centroidID, brushSizeQuery.getState());
        changeColorOfAllCentroids(canvas, recoloredCentroids);
    }

    private void startBrush(int layerID){
        active = true;
        stroke = new BrushStroke(layerID);
    }

    private void changeColorOfAllCentroids(PaintCanvas canvas, Iterable<Short> recoloredCentroids){
        for(Short centroidID : recoloredCentroids){
            if(stroke.isInStroke(centroidID)){
                continue;
            }
            Color currentColor = canvas.getChunkColorFromCentroidID(centroidID);
            changeColorOfChunk(canvas, centroidID);
            Color nextColor = canvas.getChunkColorFromCentroidID(centroidID);
            stroke.addToStroke(centroidID, currentColor, nextColor);
        }
    }

    protected abstract void changeColorOfChunk(PaintCanvas canvas, Short centroidID);

    @Override
    public void releaseTool(){
        active = false;
    }

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public PaintAction obtainAction(){
        return stroke;
    }

    @Override
    public boolean shouldAcceptMouseInput(MouseInputEvent event){
        return event.isMouseButtonOneDown();
    }

}
