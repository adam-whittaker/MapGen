package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
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
        PaintCentroidData data = canvas.getCentroidData(event.getCoord());
        Iterable<PaintCentroidData> recoloredCentroids = canvas.centroidNeighbourhood(data, settings.getBrushSize());
        changeColor(canvas, recoloredCentroids);
    }

    private void startBrush(){
        active = true;
        stroke = new BrushStroke();
    }

    private void changeColor(PaintCanvas canvas, Iterable<PaintCentroidData> recoloredCentroids){
        for(PaintCentroidData target : recoloredCentroids){
            if(stroke.isInStroke(target)){
                continue;
            }
            stroke.addToStroke(target);
            canvas.changeChunkColor(settings.nextRandomColor(), target.getCoord());
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
