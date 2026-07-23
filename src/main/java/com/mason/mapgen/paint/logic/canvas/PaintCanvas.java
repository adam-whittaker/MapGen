package com.mason.mapgen.paint.logic.canvas;

import com.mason.libstruct.geo.*;
import com.mason.libstruct.interfaces.Boundable;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.GridImageComponent;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.mapgen.paint.components.panes.topPane.resources.ImageQuery;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridQuery;

import java.awt.*;

public class PaintCanvas implements Boundable{


    private ChunkingGrid<PaintCentroidData> grid;
    private final GridImageComponent image;


    public PaintCanvas(Coord topLeft, ChunkingGrid<PaintCentroidData> grid){
        this.grid = grid;
        this.image = new GridImageComponent(topLeft, grid);
    }


    public Short getCentroidIDFromCoord(Coord coord){
        return grid.centroidID(grid.asIndex(coord));
    }

    public Color getChunkColor(Coord coord){
        PaintCentroidData data = grid.getCentroidDataByIndex(grid.asIndex(coord));
        return data.getColor();
    }

    public void changeChunkColor(Color newColor, Short centroidID){
        PaintCentroidData centroidData = grid.getCentroidDataByID(centroidID);
        centroidData.paintColor(newColor);
        updateImageLocallyAroundCentroid(centroidData.getCoord());
    }

    private void updateImageLocallyAroundCentroid(Coord centroidCoord){
        image.updateImageInClip(grid, grid.constructBoundingRectangle(centroidCoord));
    }

    public Iterable<Short> centroidNeighbourhood(Short centroidID, int searchDepth){
        return grid.centroidNeighbourhood(centroidID, searchDepth);
    }

    @Override
    public boolean withinBounds(Coord c){
        return image.withinBounds(c);
    }

    public Coord getCoord(){
        return image.getCoord();
    }

    public Size getSize(){
        return image.getSize();
    }

    protected GridImageComponent getImageComponent(){
        return image;
    }

    public void loadInNewPaintGrid(ChunkingGrid<PaintCentroidData> newGrid){
        this.grid = newGrid;
        image.loadInNewPaintGrid(newGrid);
    }

    public PaintGridQuery getGridQueryForSaving(){
        return () -> grid;
    }

    public ImageQuery getImageQueryForExporting(){
        return image.getImageQueryForExporting();
    }

}
