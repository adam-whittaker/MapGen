package com.mason.mapgen.procgen.algorithms.chunking.components;

import com.mason.libgui.utils.structures.Coord;

import java.util.*;

public class BasicCentroidData<T extends CentroidData<T>> implements CentroidData<T>{


    private Coord coord;
    private final Set<T> neighbours;


    public BasicCentroidData(Coord coord){
        this.coord = coord;
        this.neighbours = new HashSet<>();
    }


    @Override
    public Coord getCoord(){
        return coord;
    }

    @Override
    public void setCoord(Coord coord){
        this.coord = coord;
    }

    @Override
    public void addNeighbour(T neighbour){
        neighbours.add(neighbour);
    }

    @Override
    public Iterable<T> neighbours(){
        return neighbours;
    }

    protected void removeNeighbour(T neighbour){
        neighbours.remove(neighbour);
    }

}
