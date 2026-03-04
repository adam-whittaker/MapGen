package com.mason.mapgen.paint.skeletons;

import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.util.function.Supplier;

public class BrushColorDisplaySkeleton{


    private ChunkingGrid<PaintCentroidData> grid;
    private PaintCentroidData primaryColorCentroid;
    private PaintCentroidData secondaryColorCentroid;
    private PaintCentroidData averageColorCentroid;


    public BrushColorDisplaySkeleton(){}


    public ChunkingGrid<PaintCentroidData> getGrid(){
        if(grid == null){
            throw new IllegalStateException("grid is not set");
        }
        return grid;
    }

    public void setGrid(ChunkingGrid<PaintCentroidData> grid){
        if(this.grid != null){
            throw new IllegalStateException("grid is already set");
        }
        this.grid = grid;
    }

    public PaintCentroidData getPrimaryColorCentroid(){
        if(primaryColorCentroid == null){
            throw new IllegalStateException("primaryColorCentroid is not set");
        }
        return primaryColorCentroid;
    }

    public void setPrimaryColorCentroid(PaintCentroidData primaryColorCentroid){
        if(this.primaryColorCentroid != null){
            throw new IllegalStateException("primaryColorCentroid is already set");
        }
        this.primaryColorCentroid = primaryColorCentroid;
    }

    public PaintCentroidData getSecondaryColorCentroid(){
        if(secondaryColorCentroid == null){
            throw new IllegalStateException("secondaryColorCentroid is not set");
        }
        return secondaryColorCentroid;
    }

    public void setSecondaryColorCentroid(PaintCentroidData secondaryColorCentroid){
        if(this.secondaryColorCentroid != null){
            throw new IllegalStateException("secondaryColorCentroid is already set");
        }
        this.secondaryColorCentroid = secondaryColorCentroid;
    }

    public PaintCentroidData getAverageColorCentroid(){
        if(averageColorCentroid == null){
            throw new IllegalStateException("averageColorCentroid is not set");
        }
        return averageColorCentroid;
    }

    public void setAverageColorCentroid(PaintCentroidData averageColorCentroid){
        if(this.averageColorCentroid != null){
            throw new IllegalStateException("averageColorCentroid is already set");
        }
        this.averageColorCentroid = averageColorCentroid;
    }

}
