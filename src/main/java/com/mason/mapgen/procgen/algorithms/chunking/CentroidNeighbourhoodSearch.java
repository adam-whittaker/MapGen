package com.mason.mapgen.procgen.algorithms.chunking;


import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.util.*;

import static com.mason.mapgen.procgen.algorithms.chunking.AnnexQueries.euclideanDist;

public class CentroidNeighbourhoodSearch<T extends CentroidData<T>>{


    private final ChunkingGrid<T> grid;


    public CentroidNeighbourhoodSearch(ChunkingGrid<T> grid){
        this.grid = grid;
    }


    public Iterable<T> centroidNeighbourhood(T data, int maxDist){
        Coord startCoord = data.getCoord();
        Set<T> found = new HashSet<>();
        Deque<T> frontier = new ArrayDeque<>(8);
        frontier.add(data);
        found.add(data);
        T current;
        while(!frontier.isEmpty()){
            current = frontier.pop();
            for(T neighbour : current.neighbours()){
                if(!found.contains(neighbour) && euclideanDist(startCoord, neighbour.getCoord()) < maxDist){
                    found.add(neighbour);
                    frontier.add(neighbour);
                }
            }
        }
        return found;
    }

}
