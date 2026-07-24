package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;

import java.awt.*;
import java.util.*;

public class BrushStroke implements PaintAction{


    private record ColorChange(Color currentColor, Color nextColor){

    }

    private final Map<Short, ColorChange> colorChangeMap;


    BrushStroke(){
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
            canvas.changeChunkColor(colorChange.nextColor(), centroidID);
        });
    }

    @Override
    public void undo(PaintCanvas canvas){
        colorChangeMap.forEach((centroidID, colorChange) -> {
            canvas.changeChunkColor(colorChange.currentColor(), centroidID);
        });
    }

}
