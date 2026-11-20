package com.mason.mapgen.paint.logic.canvas;

import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.PaintGrid;

import java.awt.*;

public abstract class AbstractPaintCanvas implements PaintCanvas{


    private PaintGrid grid;
    private final IntQuery layerQuery;


    protected AbstractPaintCanvas(PaintGrid grid, IntQuery layerQuery){
        this.grid = grid;
        this.layerQuery = layerQuery;
    }


    protected void setGrid(PaintGrid grid){
        this.grid = grid;
    }

    protected PaintGrid getGrid(){
        return grid;
    }


    @Override
    public Short getCentroidIDFromCoord(Coord coord){
        return grid.centroidID(grid.asIndex(coord));
    }

    @Override
    public Color getChunkColor(Coord coord){
        PaintCentroidData data = grid.getCentroidDataByIndex(grid.asIndex(coord));
        return data.getColor(layerQuery.getState());
    }

    @Override
    public Color getChunkColorFromCentroidID(Short centroidID){
        PaintCentroidData data = grid.getCentroidDataByID(centroidID);
        return data.getColor(layerQuery.getState());
    }

    @Override
    public void changeChunkColor(Color newColor, Short centroidID){
        PaintCentroidData centroidData = grid.getCentroidDataByID(centroidID);
        centroidData.paintColor(layerQuery.getState(), newColor);
        updateImageInChunk(grid, centroidID);
    }

    protected abstract void updateImageInChunk(PaintGrid grid, Short centroidID);

    @Override
    public Iterable<Short> centroidNeighbourhood(Short centroidID, int searchDepth){
        return grid.centroidNeighbourhood(centroidID, searchDepth);
    }

}
