package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;

import java.awt.*;

public class BasicBrushTool implements BrushTool{


    private BrushStroke stroke;
    private boolean active;

    private int brushSize;
    private final BrushColor brushColor;


    public BasicBrushTool(){
        active = false;
        brushSize = 50;
        this.brushColor = new BrushColor();
    }


    @Override
    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
    }

    @Override
    public int getBrushSize(){
        return brushSize;
    }

    @Override
    public void setCertainty(double certainty){
        brushColor.setCertainty(certainty);
    }

    @Override
    public double getCertainty(){
        return brushColor.getCertainty();
    }

    @Override
    public int getAlpha(){
        return brushColor.getAlpha();
    }

    @Override
    public void setAlpha(int alpha){
        brushColor.setAlpha(alpha);
    }

    @Override
    public boolean getChannelIndependence(){
        return brushColor.getChannelIndependence();
    }

    @Override
    public void setChannelIndependence(boolean channelIndependence){
        brushColor.setChannelIndependence(channelIndependence);
    }

    @Override
    public void setPrimaryColor(Color color){
        brushColor.setPrimaryColor(color);
    }

    @Override
    public void setSecondaryColor(Color color){
        brushColor.setSecondaryColor(color);
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
    public boolean shouldAcceptMouseInput(MouseInputEvent event){
        return event.isMouseButtonOneDown();
    }

}
