package com.mason.mapgen.procgen.algorithms.chunking;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;
import com.mason.mapgen.procgen.algorithms.misc.RandomCoords;

import java.util.Map;
import java.util.Set;

public class VoronoiChunker<T extends CentroidData<T>>{


    private final ChunkingGrid<T> grid;
    private final int numChunks;
    private final int lloydRelaxCount;
    private final CentroidDataInitializer<T> centroidDataInitializer;
    private final CentroidFloodFill<T> lloydRelaxFloodFill;
    private final CentroidFloodFill<T> chunkingFloodFill;


    public VoronoiChunker(Size size,
                          int numChunks,
                          int lloydRelaxCount,
                          CentroidDataInitializer<T> centroidDataInitializer,
                          FloodFillAnnexQuery<T> annexQuery){
        this.grid = new ChunkingGrid<>(size);
        this.numChunks = numChunks;
        this.lloydRelaxCount = lloydRelaxCount;
        this.centroidDataInitializer = centroidDataInitializer;
        this.chunkingFloodFill = new CentroidFloodFill<>(grid, annexQuery, true);
        this.lloydRelaxFloodFill = new CentroidFloodFill<>(grid, AnnexQueries::euclideanQuery, false);
    }


    public void createChunks(){
        placeCentroidsRandomly();
        lloydRelax();
        chunkingFloodFill.floodFill();
    }

    public ChunkingGrid<T> getGrid(){
        return grid;
    }

    private void placeCentroidsRandomly(){
        Set<Coord> coords = RandomCoords.generateRandomDistinctCoords(grid.size(), numChunks);
        for(Coord coord : coords){
            grid.createCentroid(centroidDataInitializer.initializeCentroid(coord));
        }
    }

    private void lloydRelax(){
        for(int n=0; n<lloydRelaxCount; n++){
            lloydRelaxFloodFill.floodFill();
            resetCentroidsToChunkCentre();
        }
    }

    private void resetCentroidsToChunkCentre(){
        CentroidCounterMap counterMap = new CentroidCounterMap(grid);
        counterMap.averageAndClearCentroids();
        moveCentroidsToChunkCentre(counterMap);
    }

    private void moveCentroidsToChunkCentre(CentroidCounterMap counterMap){
        for(Map.Entry<Short, long[]> centroidPair : counterMap){
            grid.moveCentroid(centroidPair.getKey(), counterMap.counterToCoord(centroidPair.getValue()));
        }
    }

}
