package com.mason.mapgen.procgen.algorithms.chunking.voronoi;

import com.mason.mapgen.procgen.algorithms.chunking.CentroidFloodFill;
import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;
import com.mason.mapgen.procgen.algorithms.misc.RandomCoords;

public class VoronoiChunkerSkeleton<T extends CentroidData<T>>{


    private ChunkingGrid<T> grid;
    private int numChunks;
    private int lloydRelaxCount;
    private RandomCoordGenerator randomCoordGenerator;
    private CentroidDataInitializer<T> centroidDataInitializer;
    private CentroidFloodFill<T> lloydRelaxFloodFill;
    private CentroidFloodFill<T> chunkingFloodFill;


    public VoronoiChunkerSkeleton(){
        randomCoordGenerator = RandomCoords::generateRandomDistinctCoords;
    }


    public void setGrid(ChunkingGrid<T> grid){
        this.grid = grid;
    }

    public ChunkingGrid<T> getGrid(){
        if(grid == null){
            throw new IllegalStateException("grid is not set");
        }
        return grid;
    }

    public int getNumChunks(){
        return numChunks;
    }

    public void setNumChunks(int numChunks){
        this.numChunks = numChunks;
    }

    public int getLloydRelaxCount(){
        return lloydRelaxCount;
    }

    public void setLloydRelaxCount(int lloydRelaxCount){
        this.lloydRelaxCount = lloydRelaxCount;
    }

    public CentroidDataInitializer<T> getCentroidDataInitializer(){
        if(centroidDataInitializer == null){
            throw new IllegalStateException("centroidDataInitializer is not set");
        }
        return centroidDataInitializer;
    }

    public void setCentroidDataInitializer(CentroidDataInitializer<T> centroidDataInitializer){
        this.centroidDataInitializer = centroidDataInitializer;
    }

    public CentroidFloodFill<T> getLloydRelaxFloodFill(){
        if(lloydRelaxFloodFill == null){
            throw new IllegalStateException("lloydRelaxFloodFill is not set");
        }
        return lloydRelaxFloodFill;
    }

    public void setLloydRelaxFloodFill(CentroidFloodFill<T> lloydRelaxFloodFill){
        this.lloydRelaxFloodFill = lloydRelaxFloodFill;
    }

    public CentroidFloodFill<T> getChunkingFloodFill(){
        if(chunkingFloodFill == null){
            throw new IllegalStateException("chunkingFloodFill is not set");
        }
        return chunkingFloodFill;
    }

    public void setChunkingFloodFill(CentroidFloodFill<T> chunkingFloodFill){
        this.chunkingFloodFill = chunkingFloodFill;
    }

    public void preventCentroidGraph(){
        if(chunkingFloodFill == null){
            throw new IllegalStateException("ChunkingFloodFill must be set first");
        }
        chunkingFloodFill.shouldConnectChunkGraph(false);
    }

    public RandomCoordGenerator getRandomCoordGenerator(){
        if(randomCoordGenerator == null){
            throw new IllegalStateException("randomCoordGenerator is not set");
        }
        return randomCoordGenerator;
    }

    public void setRandomCoordGenerator(RandomCoordGenerator randomCoordGenerator){
        this.randomCoordGenerator = randomCoordGenerator;
    }

}
