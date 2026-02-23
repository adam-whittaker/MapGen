package com.mason.mapgen.procgen.algorithms.chunking;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import static com.mason.mapgen.core.Utils.RANDOM;
import static java.lang.Math.abs;
import static java.lang.Math.max;

public final class AnnexQueries{


    private AnnexQueries(){}


    public static boolean manhattanQuery(ChunkingGrid<?> grid, CentroidData<?> centroidData, Integer targetIdx){
        return grid.distanceToCentroid(targetIdx) > manhattanDist(centroidData.getCoord(), grid.asCoord(targetIdx));
    }

    private static int manhattanDist(Coord a, Coord b){
        return abs(a.x() - b.x()) + abs(a.y() - b.y());
    }


    public static boolean euclideanQuery(ChunkingGrid<?> grid, CentroidData<?> centroidData, Integer targetIdx){
        Coord targetCoord = grid.asCoord(targetIdx);
        CentroidData<?> targetCentroidData = grid.getCentroidDataByIndex(targetIdx);
        int targetOwnCentroidSquareDist = squareDist(targetCentroidData.getCoord(), targetCoord);
        int targetAnnexingCentroidSquareDist = squareDist(centroidData.getCoord(), targetCoord);
        return targetOwnCentroidSquareDist > targetAnnexingCentroidSquareDist;
    }

    private static int squareDist(Coord a, Coord b){
        return (a.x()-b.x())*(a.x()-b.x()) + (a.y()-b.y())*(a.y()-b.y());
    }


    public static boolean minkowskiQuery(ChunkingGrid<?> grid, CentroidData<?> centroidData, Integer targetIdx){
        Coord targetCoord = grid.asCoord(targetIdx);
        CentroidData<?> targetCentroidData = grid.getCentroidDataByIndex(targetIdx);
        int targetOwnCentroidDist = minkowskiDist(targetCentroidData.getCoord(), targetCoord);
        int targetAnnexingCentroidDist = minkowskiDist(centroidData.getCoord(), targetCoord);
        return targetOwnCentroidDist > targetAnnexingCentroidDist;
    }

    private static int minkowskiDist(Coord a, Coord b){
        return max(abs(a.x()-b.x()), abs(a.y()-b.y()));
    }


    public static boolean randomQuery(ChunkingGrid<?> grid, CentroidData<?> centroidData, Integer targetIdx){
        Coord targetCoord = grid.asCoord(targetIdx);
        CentroidData<?> targetCentroidData = grid.getCentroidDataByIndex(targetIdx);
        int targetOwnCentroidDist = squareDist(targetCentroidData.getCoord(), targetCoord);
        int targetAnnexingCentroidDist = squareDist(centroidData.getCoord(), targetCoord);
        if(RANDOM.nextDouble() * (targetAnnexingCentroidDist + targetOwnCentroidDist) < targetOwnCentroidDist){
            return true;
        }
        int adjacents = numCardinalAdjacents(grid, centroidData, targetIdx);
        if(adjacents == 4){
            return RANDOM.nextDouble() < 0.9;
        }
        if(adjacents == 3){
            return RANDOM.nextDouble() < 0.65;
        }
        return false;
    }

    private static int numCardinalAdjacents(ChunkingGrid<?> grid, CentroidData<?> centroidData, Integer targetIdx){
        int adjacents = 0;
        for(Integer neighbourIdx : grid.cardinalNeighbours(targetIdx)){
            if(!grid.hasCentroid(neighbourIdx)){
                continue;
            }
            if(grid.getCentroidDataByIndex(neighbourIdx).coordMatches(centroidData)){
                adjacents++;
            }
        }
        return adjacents;
    }

}
