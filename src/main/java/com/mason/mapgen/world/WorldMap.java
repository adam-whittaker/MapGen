package com.mason.mapgen.world;

import com.mason.mapgen.structures.grids.Grid;

public class WorldMap{


    private final Grid<WorldPoint> map;


    public WorldMap(WorldSkeleton skeleton){
        map = skeleton.getWorldMapGrid();
    }

}
