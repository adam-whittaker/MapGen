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
    public Color getChunkColor(Coord coord){
        return getChunkColorFromLayer(coord, layerQuery.getState());
    }

    @Override
    public Color getChunkColorFromLayer(Coord coord, int layerID){
        PaintCentroidData data = grid.getCentroidDataByIndex(grid.asIndex(coord));
        return data.getColor(layerID);
    }

    @Override
    public Color getChunkColorFromCentroidID(Short centroidID){
        return getChunkColorFromCentroidAndLayerID(centroidID, layerQuery.getState());
    }

    public Color getChunkColorFromCentroidAndLayerID(Short centroidID, int layerID){
        PaintCentroidData data = grid.getCentroidDataByID(centroidID);
        return data.getColor(layerID);
    }


    public void paintChunkColor(Color paintColor, Short centroidID){
        PaintCentroidData centroidData = grid.getCentroidDataByID(centroidID);
        centroidData.paintColor(layerQuery.getState(), paintColor);
        updateImageInChunkOnLayer(grid, centroidID, layerQuery.getState());
    }

    @Override
    public void setChunkColor(Color newColor, Short centroidID){
        setChunkColorOnLayer(newColor, centroidID, layerQuery.getState());
    }

    @Override
    public void setChunkColorOnLayer(Color newColor, Short centroidID, int layerID){
        PaintCentroidData centroidData = grid.getCentroidDataByID(centroidID);
        centroidData.setColor(layerID, newColor);
        updateImageInChunkOnLayer(grid, centroidID, layerID);
    }

    @Override
    public Short getCentroidIDFromCoord(Coord coord){
        return grid.centroidID(grid.asIndex(coord));
    }

    protected abstract void updateImageInChunkOnLayer(PaintGrid grid, Short centroidID, int layerID);

    @Override
    public Iterable<Short> centroidNeighbourhood(Short centroidID, int searchDepth){
        return grid.centroidNeighbourhood(centroidID, searchDepth);
    }

}
