package com.mason.mapgen.paint.skeletons;

import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class BrushColorDisplaySkeleton{


    private ChunkingGrid<PaintCentroidData> grid;
    private PaintCentroidData primaryColorCentroid;
    private PaintCentroidData secondaryColorCentroid;


    public BrushColorDisplaySkeleton(){}


    public ChunkingGrid<PaintCentroidData> getGrid(){
        if(grid == null){
            throw new IllegalStateException("grid is not set");
        }
        return grid;
    }

    public void setGrid(ChunkingGrid<PaintCentroidData> grid){
        this.grid = grid;
    }

    public PaintCentroidData getPrimaryColorCentroid(){
        if(primaryColorCentroid == null){
            throw new IllegalStateException("primaryColorCentroid is not set");
        }
        return primaryColorCentroid;
    }

    public void setPrimaryColorCentroid(PaintCentroidData primaryColorCentroid){
        this.primaryColorCentroid = primaryColorCentroid;
    }

    public PaintCentroidData getSecondaryColorCentroid(){
        if(secondaryColorCentroid == null){
            throw new IllegalStateException("secondaryColorCentroid is not set");
        }
        return secondaryColorCentroid;
    }

    public void setSecondaryColorCentroid(PaintCentroidData secondaryColorCentroid){
        this.secondaryColorCentroid = secondaryColorCentroid;
    }

}
