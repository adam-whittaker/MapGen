package com.mason.mapgen.procgen.algorithms.chunking;

import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public interface FloodFillAnnexQuery<T extends CentroidData<T>>{


    boolean canAnnex(ChunkingGrid<T> grid, CentroidData<T> centroid, Integer targetIdx);

}
