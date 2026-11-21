package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;

import java.awt.*;
import java.util.*;

public class BrushStroke implements PaintAction{


    private record ColorChange(Color currentColor, Color nextColor){

    }

    private final Map<Short, ColorChange> colorChangeMap;
    private final int layerID;


    BrushStroke(int layerID){
        this.layerID = layerID;
        colorChangeMap = new HashMap<>();
    }


    void addToStroke(Short centroidID, Color currentColor, Color nextColor){
        colorChangeMap.put(centroidID, new ColorChange(currentColor, nextColor));
    }

    boolean isInStroke(Short centroidID){
        return colorChangeMap.containsKey(centroidID);
    }


    @Override
    public void apply(PaintCanvas canvas){
        colorChangeMap.forEach((centroidID, colorChange) -> {
            canvas.setChunkColorOnLayer(colorChange.nextColor(), centroidID, layerID);
        });
    }

    @Override
    public void undo(PaintCanvas canvas){
        colorChangeMap.forEach((centroidID, colorChange) -> {
            canvas.setChunkColorOnLayer(colorChange.currentColor(), centroidID, layerID);
        });
    }


    @Override
    public String toString(){
        return "BRUSH_STROKE: layerID: " + layerID + ", hash: " + hashCode();
    }

}
