package com.mason.mapgen.paint.components.panes.topPane.resources;

import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;

public interface PaintGridReceiver{

    void receiveGrid(ChunkingGrid<PaintCentroidData> grid);

}
