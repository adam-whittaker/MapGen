package com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas;

import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.position.PositionState;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.libvoronoi.algorithms.FloodFillAnnexQuery;

import java.util.function.Supplier;

public class PaletteCanvasControllerSkeleton{


    private Supplier<PaintTool> currentToolQuery;
    private IntState alphaState;
    private PositionState certaintyState;

    private RectQuery boundary;
    private int numChunks = -1;
    private int lloydRelaxCount = -1;
    private FloodFillAnnexQuery<PaintCentroidData> annexQuery;
    private PaintCanvas canvas;


    public PaletteCanvasControllerSkeleton(){}


    public Supplier<PaintTool> getCurrentToolQuery(){
        if(currentToolQuery == null){
            throw new IllegalStateException("currentToolQuery is not set");
        }
        return currentToolQuery;
    }

    public void setCurrentToolQuery(Supplier<PaintTool> currentToolQuery){
        if(this.currentToolQuery != null){
            throw new IllegalStateException("currentToolQuery is already set");
        }
        this.currentToolQuery = currentToolQuery;
    }

    public RectQuery getBoundary(){
        if(boundary == null){
            throw new IllegalStateException("boundary is not set");
        }
        return boundary;
    }

    public void setBoundary(RectQuery boundary){
        if(this.boundary != null){
            throw new IllegalStateException("boundary is already set");
        }
        this.boundary = boundary;
    }

    public int getNumChunks(){
        if(numChunks < 0){
            throw new IllegalStateException("numChunks unset!");
        }
        return numChunks;
    }

    public void setNumChunks(int numChunks){
        this.numChunks = numChunks;
    }

    public int getLloydRelaxCount(){
        if(lloydRelaxCount < 0){
            throw new IllegalStateException("lloydRelaxCount unset!");
        }
        return lloydRelaxCount;
    }

    public void setLloydRelaxCount(int lloydRelaxCount){
        this.lloydRelaxCount = lloydRelaxCount;
    }

    public FloodFillAnnexQuery<PaintCentroidData> getAnnexQuery(){
        if(annexQuery == null){
            throw new IllegalStateException("annexQuery is not set");
        }
        return annexQuery;
    }

    public void setAnnexQuery(FloodFillAnnexQuery<PaintCentroidData> annexQuery){
        if(this.annexQuery != null){
            throw new IllegalStateException("annexQuery is already set");
        }
        this.annexQuery = annexQuery;
    }

    public PaintCanvas getCanvas(){
        if(canvas == null){
            throw new IllegalStateException("canvas is not set");
        }
        return canvas;
    }

    public void setCanvas(PaintCanvas canvas){
        if(this.canvas != null){
            throw new IllegalStateException("canvas is already set");
        }
        this.canvas = canvas;
    }

    public IntState getAlphaState(){
        if(alphaState == null){
            throw new IllegalStateException("alphaState is not set");
        }
        return alphaState;
    }

    public void setAlphaState(IntState alphaState){
        if(this.alphaState != null){
            throw new IllegalStateException("alphaState is already set");
        }
        this.alphaState = alphaState;
    }

    public PositionState getCertaintyState(){
        if(certaintyState == null){
            throw new IllegalStateException("certaintyState is not set");
        }
        return certaintyState;
    }

    public void setCertaintyState(PositionState certaintyState){
        if(this.certaintyState != null){
            throw new IllegalStateException("certaintyState is already set");
        }
        this.certaintyState = certaintyState;
    }

}
