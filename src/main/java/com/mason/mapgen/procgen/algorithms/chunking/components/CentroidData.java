package com.mason.mapgen.procgen.algorithms.chunking.components;

import com.mason.libgui.utils.structures.Coord;

public interface CentroidData<T extends CentroidData<T>>{

    Coord getCoord();

    void setCoord(Coord coord);

    void addNeighbour(T neighbour);

    Iterable<T> neighbours();

    default boolean coordMatches(CentroidData<?> other){
        return other.getCoord().equals(getCoord());
    }

}
