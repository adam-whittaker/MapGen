package com.mason.mapgen.world;

import com.mason.libstruct.geo.Coord;

public interface WorldPointSkeleton{


    Coord getCoord();
    TerrainData getTerrainData();
    Coord getCentroidCoord();

}
