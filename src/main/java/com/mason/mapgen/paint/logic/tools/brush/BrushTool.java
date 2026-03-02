package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.logic.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;

public class BrushTool implements PaintTool{


    private BrushStroke stroke;
    private boolean active;

    private int brushSize;
    private final BrushColor brushColor;


    public BrushTool(BrushColor brushColor){
        active = false;
        brushSize = 50;
        this.brushColor = brushColor;
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
    }

    public void setCertainty(double certainty){
        brushColor.setCertainty(certainty);
    }

    public double getCertainty(){
        return brushColor.getCertainty();
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
        Iterable<PaintCentroidData> recoloredCentroids = canvas.centroidNeighbourhood(data, brushSize);
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
            canvas.changeChunkColor(brushColor.nextColor(), target.getCoord());
        }
    }

    @Override
    public void releaseTool(){
        active = false;
    }

    @Override
    public boolean acceptMouseInput(MouseInputEvent event){
        return event.isMouseButtonOneDown();
    }

}
