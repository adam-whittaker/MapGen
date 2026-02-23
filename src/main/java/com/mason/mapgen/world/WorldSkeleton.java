package com.mason.mapgen.world;

import com.mason.mapgen.structures.grids.Grid;

public interface WorldSkeleton{

    Grid<WorldPoint> getWorldMapGrid();

}
