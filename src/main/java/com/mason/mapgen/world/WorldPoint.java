package com.mason.mapgen.world;

import com.mason.libgui.utils.structures.Coord;

public class WorldPoint{


    private final Coord coord;
    private final Coord centroidCoord;
    private final TerrainData terrainData;


    public WorldPoint(WorldPointSkeleton skeleton){
        this.coord = skeleton.getCoord();
        this.terrainData = skeleton.getTerrainData();
        this.centroidCoord = skeleton.getCentroidCoord();
    }


    public Coord getCoord(){
        return coord;
    }

    public TerrainData getTerrainData(){
        return terrainData;
    }

    public Coord getCentroidCoord(){
        return centroidCoord;
    }

}
