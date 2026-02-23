package com.mason.mapgen.world;

import com.mason.libgui.utils.structures.Coord;

public interface WorldPointSkeleton{


    Coord getCoord();
    TerrainData getTerrainData();
    Coord getCentroidCoord();

}
