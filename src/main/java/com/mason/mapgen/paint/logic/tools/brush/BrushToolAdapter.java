package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.core.Utils;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;

import java.awt.*;
import java.util.Arrays;

public class BrushToolAdapter implements BrushTool{


    private final BrushTool[] brushes;
    private int currentBrushIndex;


    public BrushToolAdapter(int numBrushes){
        brushes = constructBrushArray(numBrushes);
        currentBrushIndex = 0;
    }

    private static BrushTool[] constructBrushArray(int numBrushes){
        BrushTool[] brushes = new BrushTool[numBrushes];
        Arrays.setAll(brushes, i -> new BasicBrushTool());
        return brushes;
    }


    public void setCurrentBrushIndex(int index){
        Utils.verifyArrayIndexWithinBounds(index, brushes);
        currentBrushIndex = index;
    }


    @Override
    public void apply(PaintCanvas canvas, MouseInputEvent event){
        brushes[currentBrushIndex].apply(canvas, event);
    }

    @Override
    public void releaseTool(){
        brushes[currentBrushIndex].releaseTool();
    }

    @Override
    public boolean isActive(){
        return brushes[currentBrushIndex].isActive();
    }

    @Override
    public boolean shouldAcceptMouseInput(MouseInputEvent event){
        return brushes[currentBrushIndex].shouldAcceptMouseInput(event);
    }

    @Override
    public void setBrushSize(int brushSize){
        brushes[currentBrushIndex].setBrushSize(brushSize);
    }

    @Override
    public int getBrushSize(){
        return brushes[currentBrushIndex].getBrushSize();
    }

    @Override
    public void setCertainty(double certainty){
        brushes[currentBrushIndex].setCertainty(certainty);
    }

    @Override
    public double getCertainty(){
        return brushes[currentBrushIndex].getCertainty();
    }

    @Override
    public void setAlpha(int alpha){
        brushes[currentBrushIndex].setAlpha(alpha);
    }

    @Override
    public int getAlpha(){
        return brushes[currentBrushIndex].getAlpha();
    }

    @Override
    public void setChannelIndependence(boolean channelIndependence){
        brushes[currentBrushIndex].setChannelIndependence(channelIndependence);
    }

    @Override
    public boolean getChannelIndependence(){
        return brushes[currentBrushIndex].getChannelIndependence();
    }

    @Override
    public void setPrimaryColor(Color color){
        brushes[currentBrushIndex].setPrimaryColor(color);
    }

    @Override
    public void setSecondaryColor(Color color){
        brushes[currentBrushIndex].setSecondaryColor(color);
    }

}
