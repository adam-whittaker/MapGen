package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.history.PaintAction;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.settings.BrushSettingsModel;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;

import java.awt.*;

public class BrushTool implements PaintTool{


    private BrushStroke stroke;
    private boolean active;

    private final BrushSettingsModel settings;
    private final IntQuery currentLayerQuery;


    public BrushTool(IntQuery sizeQuery, IntQuery currentLayerQuery, ColorMixer colorMixer){
        active = false;
        this.settings = new BrushSettingsModel(sizeQuery, colorMixer);
        this.currentLayerQuery = currentLayerQuery;
    }


    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void apply(PaintCanvas canvas, MouseInputEvent event){
        if(!active){
            startBrush(currentLayerQuery.getState());
        }
        Short centroidID = canvas.getCentroidIDFromCoord(event.getCoord());
        Iterable<Short> recoloredCentroids = canvas.centroidNeighbourhood(centroidID, settings.getBrushSize());
        changeColor(canvas, recoloredCentroids);
    }

    private void startBrush(int layerID){
        active = true;
        stroke = new BrushStroke(layerID);
    }

    private void changeColor(PaintCanvas canvas, Iterable<Short> recoloredCentroids){
        for(Short centroidID : recoloredCentroids){
            if(stroke.isInStroke(centroidID)){
                continue;
            }
            Color currentColor = canvas.getChunkColorFromCentroidID(centroidID);
            Color paintColor = settings.nextRandomColor();
            canvas.paintChunkColor(paintColor, centroidID);
            Color nextColor = canvas.getChunkColorFromCentroidID(centroidID);
            stroke.addToStroke(centroidID, currentColor, nextColor);
        }
    }

    @Override
    public void releaseTool(){
        active = false;
    }

    public PaintAction obtainAction(){
        return stroke;
    }

    @Override
    public boolean shouldAcceptMouseInput(MouseInputEvent event){
        return event.isMouseButtonOneDown();
    }

}
