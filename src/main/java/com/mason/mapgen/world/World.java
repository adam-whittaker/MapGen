package com.mason.mapgen.world;

public class World{


    private final WorldMap map;


    public World(WorldSkeleton skeleton){
        map = new WorldMap(skeleton);
    }

}
