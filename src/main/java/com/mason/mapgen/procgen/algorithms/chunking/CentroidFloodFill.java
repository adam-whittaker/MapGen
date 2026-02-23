package com.mason.mapgen.procgen.algorithms.chunking;

import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class CentroidFloodFill<T extends CentroidData<T>>{


    private final ChunkingGrid<T> grid;
    private final FloodFillAnnexQuery<T> annexQuery;
    private final boolean connectChunkGraph;


    public CentroidFloodFill(ChunkingGrid<T> grid, FloodFillAnnexQuery<T> annexQuery, boolean connectChunkGraph){
        this.grid = grid;
        this.annexQuery = annexQuery;
        this.connectChunkGraph = connectChunkGraph;
    }


    void floodFill(){
        CentroidFloodFillFrontier frontier = new CentroidFloodFillFrontier(grid);
        Integer currentIdx;
        while(!frontier.isEmpty()){
            currentIdx = frontier.poll();
            annexEligibleNeighbors(currentIdx, frontier);
        }
    }

    private void annexEligibleNeighbors(Integer currentIdx, CentroidFloodFillFrontier frontier){
        short centroidID = grid.centroidID(currentIdx);
        CentroidData<T> centroidData = grid.getCentroidDataByIndex(currentIdx);
        short newDistFromCentroid = (short)(grid.distanceToCentroid(currentIdx) + 1);
        for(Integer neighbourIdx : frontier.cardinalNeighbours(currentIdx)){
            if(shouldAnnex(centroidData, neighbourIdx)){
                frontier.register(centroidID, neighbourIdx, newDistFromCentroid);
            }
        }
    }

    private boolean shouldAnnex(CentroidData<T> centroidData, Integer targetIdx){
        if(grid.isCentroid(targetIdx)){
            return false;
        }
        if(!grid.hasCentroid(targetIdx)){
            return true;
        }
        if(centroidAlreadyAnnexedPoint(centroidData, targetIdx)){
            return false;
        }

        boolean canAnnex = annexQuery.canAnnex(grid, centroidData, targetIdx);
        if(connectChunkGraph && !canAnnex){
            connectChunk(centroidData, targetIdx);
        }
        return canAnnex;
    }

    private boolean centroidAlreadyAnnexedPoint(CentroidData<?> centroidData, Integer targetIdx){
        CentroidData<?> targetCentroidData = grid.getCentroidDataByIndex(targetIdx);
        return centroidData.coordMatches(targetCentroidData);
    }

    private void connectChunk(CentroidData<T> centroidData, Integer targetIdx){
        centroidData.addNeighbour(grid.getCentroidDataByIndex(targetIdx));
    }

}
