package com.mason.mapgen.procgen.algorithms.chunking.components;

import com.mason.libgui.utils.structures.Coord;

import java.util.HashMap;
import java.util.Map;

public class CentroidIDMap<T extends CentroidData<T>>{


    public static final short CENTROID_UNSET = -1;

    private final Map<Short, T> map;
    private short nextID = 0;


    public CentroidIDMap(){
        map = new HashMap<>();
    }


    public boolean isCentroid(short id){
        return map.containsKey(id);
    }

    private void putCentroid(short id, T data){
        map.put(id, data);
    }

    public T getCentroidData(short id){
        return map.get(id);
    }

    public Coord getCentroidCoord(short id){
        T data = getCentroidData(id);
        return data.getCoord();
    }

    public void moveCentroid(short id, Coord newCoord){
        T data = map.get(id);
        data.setCoord(newCoord);
    }

    public short createCentroidAndReturnID(T data){
        short id = generateID();
        putCentroid(id, data);
        return id;
    }

    private short generateID(){
        short id = nextID;
        nextID++;
        return id;
    }

    public Iterable<Short> getCentroidIDs(){
        return map.keySet();
    }

}
