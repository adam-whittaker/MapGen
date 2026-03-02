package com.mason.mapgen.procgen.algorithms.chunking.components;

import com.mason.libgui.utils.structures.*;
import com.mason.mapgen.procgen.algorithms.chunking.CentroidNeighbourhoodSearch;
import com.mason.mapgen.structures.grids.lowMemory.CardinalIndexNeighbours;
import com.mason.mapgen.structures.grids.lowMemory.ShortGrid;

import java.util.Iterator;

import static com.mason.mapgen.procgen.algorithms.chunking.components.CentroidIDMap.CENTROID_UNSET;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class ChunkingGrid<T extends CentroidData<T>>{


    private static final short DIST_UNSET = -1;

    private final ShortGrid centroidIDGrid;
    private final ShortGrid distToCentroidGrid;
    private final CentroidIDMap<T> centroidDataMap;
    private final Size size;
    private short maxDistToCentroid = -1;


    public ChunkingGrid(Size size){
        this.size = size;
        centroidIDGrid = new ShortGrid(size, CENTROID_UNSET);
        distToCentroidGrid = new ShortGrid(size, DIST_UNSET);
        centroidDataMap = new CentroidIDMap<>();
    }


    public Size size(){
        return size;
    }

    public Iterable<Short> getAllCentroidIDs(){
        return centroidDataMap.getCentroidIDs();
    }

    public short centroidID(Integer pointIdx){
        return centroidIDGrid.getByIndex(pointIdx);
    }

    public short distanceToCentroid(Integer pointIdx){
        return distToCentroidGrid.getByIndex(pointIdx);
    }

    public T getCentroidDataByIndex(Integer pointIdx){
        if(!hasCentroid(pointIdx)){
            throw new IllegalStateException("Point does not have centroid!");
        }
        short centroidID = centroidIDGrid.getByIndex(pointIdx);
        return centroidDataMap.getCentroidData(centroidID);
    }

    public T getCentroidDataByID(short centroidID){
        return centroidDataMap.getCentroidData(centroidID);
    }

    public boolean hasCentroid(Integer pointIdx){
        return centroidIDGrid.getByIndex(pointIdx) != CENTROID_UNSET;
    }

    public boolean isCentroid(Integer pointIdx){
        if(!hasCentroid(pointIdx)){
            return false;
        }
        short centroidID = centroidIDGrid.getByIndex(pointIdx);
        return pointIndexMatchesCentroidCoord(pointIdx, centroidID);
    }

    private boolean pointIndexMatchesCentroidCoord(Integer pointIdx, short centroidID){
        Coord centroidCoord = centroidDataMap.getCentroidCoord(centroidID);
        int centroidIndex = centroidIDGrid.asIndex(centroidCoord);
        return centroidIndex == pointIdx;
    }

    public void setCentroid(Coord pointCoord, short centroidID, short distToCentroid){
        int pointIndex = centroidIDGrid.asIndex(pointCoord);
        setCentroidByIndex(pointIndex, centroidID, distToCentroid);
    }

    public void setCentroidByIndex(int pointIdx, short centroidID, short distToCentroid){
        centroidIDGrid.setByIndex(pointIdx, centroidID);
        distToCentroidGrid.setByIndex(pointIdx, distToCentroid);
    }

    public void unsetCentroidByIndex(int pointIdx){
        setCentroidByIndex(pointIdx, CENTROID_UNSET, DIST_UNSET);
    }

    public Coord getCentroidCoord(short centroidID){
        return centroidDataMap.getCentroidCoord(centroidID);
    }

    public void createCentroid(T data){
        short centroidID = centroidDataMap.createCentroidAndReturnID(data);
        Coord centroidCoord = data.getCoord();
        centroidIDGrid.set(centroidCoord.x(), centroidCoord.y(), centroidID);
    }

    public void moveCentroid(short centroidId, Coord newCoord){
        centroidDataMap.moveCentroid(centroidId, newCoord);
    }

    public Coord asCoord(Integer targetIdx){
        return new Coord(targetIdx % size.width(), targetIdx / size.width());
    }

    public Integer asIndex(Coord coord){
        return coord.y()*size.width() + coord.x();
    }

    public Iterable<Integer> cardinalNeighbours(Integer pointIdx){
        return new CardinalIndexNeighbours(pointIdx, size);
    }

    public Iterable<Integer> pointIndices(){
        return () -> new Iterator<>(){

            final int maxIndex = size.width() * size.height() - 1;
            int current = -1;

            @Override
            public boolean hasNext(){
                return current < maxIndex;
            }

            @Override
            public Integer next(){
                current++;
                return current;
            }
        };
    }

    public Iterable<Integer> indicesInClip(RectQuery clip){
        return indicesInSafeClip(constructSafeClip(clip));
    }

    private RectQuery constructSafeClip(RectQuery clip){
        int x = max(clip.x(), 0);
        int y = max(clip.y(), 0);
        int width = min(clip.width(), size.width()-x-1);
        int height = min(clip.height(), size.height()-y-1);
        return new Rect(x, y, width, height);
    }

    private Iterable<Integer> indicesInSafeClip(RectQuery clip){
        return () -> new Iterator<>(){

            final int maxIndex = asIndex(new Coord(clip.x()+clip.width()-1, clip.y()+clip.height()-1));
            final int boundX = clip.x() + clip.width();
            int current = asIndex(clip.getCoord())-1;

            @Override
            public boolean hasNext(){
                return current < maxIndex;
            }

            @Override
            public Integer next(){
                current++;
                if(boundX <= current % size.width()){
                    current += size.width() - clip.width();
                }
                return current;
            }
        };
    }

    public void updateMaxDistToCentroid(){
        maxDistToCentroid = distToCentroidGrid.max();
    }

    public RectQuery constructBoundingRectangle(Coord centroidCoord){
        int x = max(centroidCoord.x() - maxDistToCentroid, 0);
        int y = max(centroidCoord.y() - maxDistToCentroid, 0);
        int width = min(2*maxDistToCentroid+1, size.width()-x-1);
        int height = min(2*maxDistToCentroid+1, size.height()-y-1);
        return new Rect(x, y, width, height);
    }

    public Iterable<T> centroidNeighbourhood(T data, int searchDepth){
        return new CentroidNeighbourhoodSearch<>(this).centroidNeighbourhood(data, searchDepth);
    }

}
