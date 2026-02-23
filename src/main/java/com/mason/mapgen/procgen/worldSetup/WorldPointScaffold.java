package com.mason.mapgen.procgen.worldSetup;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.world.*;

public class WorldPointScaffold implements WorldPointSkeleton{


    private Coord coord;
    private double elevation;
    private double moisture;
    private Biome biome;


    public WorldPointScaffold(Coord coord){
        this.coord = coord;
        elevation = 0;
        moisture = 0;
        biome = Biome.UNSET;
    }


    @Override
    public Coord getCoord(){
        return coord;
    }

    @Override
    public TerrainData getTerrainData(){
        return new TerrainData(elevation, moisture, biome);
    }

    @Override
    public Coord getCentroidCoord(){
        throw new UnsupportedOperationException("Unfinished");
    }

}
