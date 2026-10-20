package com.mason.mapgen.paint.components.misc;

import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.lowMemory.ShortGrid;
import com.mason.libvoronoi.algorithms.components.CentroidIDMap;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.libvoronoi.algorithms.voronoi.ChunkingGridInitializer;

public class PaintGrid extends ChunkingGrid<PaintCentroidData>{


    public PaintGrid(Size size,
                     ShortGrid centroidIDGrid,
                     ShortGrid distToCentroidGrid,
                     CentroidIDMap<PaintCentroidData> centroidDataMap){
        super(size, centroidIDGrid, distToCentroidGrid, centroidDataMap);
    }

    private PaintGrid(Size size){
        super(size);
    }


    public static final ChunkingGridInitializer<PaintCentroidData, PaintGrid> PAINT_GRID_INITIALIZER = new ChunkingGridInitializer<>(){

        @Override
        public PaintCentroidData initializeCentroid(Coord coord){
            return new PaintCentroidData(coord);
        }

        @Override
        public PaintGrid initializeChunkingGrid(Size size){
            return new PaintGrid(size);
        }
    };

}
