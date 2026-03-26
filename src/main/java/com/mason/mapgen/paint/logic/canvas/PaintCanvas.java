package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.utils.structures.*;
import com.mason.libgui.utils.structures.interfaces.Boundable;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.GridImageComponent;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;

public class PaintCanvas implements Boundable{


    private final ChunkingGrid<PaintCentroidData> grid;
    private final GridImageComponent image;


    public PaintCanvas(Coord topLeft, ChunkingGrid<PaintCentroidData> grid){
        this.grid = grid;
        this.image = new GridImageComponent(topLeft, grid);
    }


    public PaintCentroidData getCentroidData(Coord coord){
        return grid.getCentroidDataByIndex(grid.asIndex(coord));
    }

    public Color getChunkColor(Coord coord){
        return getCentroidData(coord).getColor();
    }

    public void changeChunkColor(Color newColor, Coord coord){
        Integer pointIdx = grid.asIndex(coord);
        PaintCentroidData centroidData = grid.getCentroidDataByIndex(pointIdx);
        centroidData.paintColor(newColor);
        updateImageLocallyAroundCentroid(centroidData.getCoord());
    }

    private void updateImageLocallyAroundCentroid(Coord centroidCoord){
        image.updateImageInClip(grid, grid.constructBoundingRectangle(centroidCoord));
    }

    public Iterable<PaintCentroidData> centroidNeighbourhood(PaintCentroidData data, int searchDepth){
        return grid.centroidNeighbourhood(data, searchDepth);
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

}
