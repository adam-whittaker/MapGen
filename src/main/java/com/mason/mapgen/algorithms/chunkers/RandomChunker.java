package com.mason.mapgen.algorithms.chunkers;


public class RandomChunker /*extends AbstractChunker*/{


    /*public RandomChunker(int numCentroids, int lloydRelaxCount, WorldMap map){
        super(numCentroids, lloydRelaxCount, map);
    }


    @Override
    protected boolean canTakeOver(Point centroid, Point point){
        int dist = point.squareDist(point.getCentroid());
        return R.nextDouble()*(point.squareDist(centroid) + dist) < dist || surrounded(centroid, point);
    }

    private boolean surrounded(Point centroid, Point point){
        int adjacents = 0;
        for(Point p : map.cardinals(point)){
            if(p.hasCentroid() && p.getCentroid().equals(centroid)) adjacents++;
        }
        return adjacents>=3;
    }*/

}
