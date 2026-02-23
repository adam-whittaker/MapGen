package com.mason.mapgen.procgen.algorithms.chunking.components;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.grids.lowMemory.CardinalIndexNeighbours;
import com.mason.mapgen.structures.grids.lowMemory.ShortGrid;

import java.util.Iterator;

import static com.mason.mapgen.procgen.algorithms.chunking.components.CentroidIDMap.CENTROID_UNSET;

public class ChunkingGrid<T extends CentroidData<T>>{


    private static final short DIST_UNSET = -1;

    private final ShortGrid centroidIDGrid;
    private final ShortGrid distToCentroidGrid;
    private final CentroidIDMap<T> centroidDataMap;
    private final Size size;


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

}
