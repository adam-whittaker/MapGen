package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.settings.BrushSettingsModel;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;

public class BrushTool implements PaintTool{


    private BrushStroke stroke;
    private boolean active;

    private final BrushSettingsModel settings;


    public BrushTool(PaintControlSettingsSkeleton skeleton){
        active = false;
        this.settings = new BrushSettingsModel(skeleton);
    }


    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void apply(PaintCanvas canvas, MouseInputEvent event){
        if(!active){
            startBrush();
        }
        Short centroidID = canvas.getCentroidIDFromCoord(event.getCoord());
        Iterable<Short> recoloredCentroids = canvas.centroidNeighbourhood(centroidID, settings.getBrushSize());
        changeColor(canvas, recoloredCentroids);
    }

    private void startBrush(){
        active = true;
        stroke = new BrushStroke();
    }

    private void changeColor(PaintCanvas canvas, Iterable<Short> recoloredCentroids){
        for(Short centroidID : recoloredCentroids){
            if(stroke.isInStroke(centroidID)){
                continue;
            }
            stroke.addToStroke(centroidID);
            canvas.changeChunkColor(settings.nextRandomColor(), centroidID);
        }
    }

    @Override
    public void releaseTool(){
        active = false;
    }

    @Override
    public boolean shouldAcceptMouseInput(MouseInputEvent event){
        return event.isMouseButtonOneDown();
    }

}
