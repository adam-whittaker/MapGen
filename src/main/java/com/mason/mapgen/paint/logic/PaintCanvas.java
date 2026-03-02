package com.mason.mapgen.paint.logic;

import com.mason.libgui.utils.structures.*;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;

public class PaintCanvas{


    private final ChunkingGrid<PaintCentroidData> grid;
    private final GridImageComponent image;


    public PaintCanvas(ChunkingGrid<PaintCentroidData> grid, GridImageComponent image){
        this.grid = grid;
        this.image = image;
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

}
