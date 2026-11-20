package com.mason.mapgen.paint.logic.fileIO;

import com.mason.libstruct.geo.Size;
import com.mason.libstruct.lowMemory.ShortGrid;
import com.mason.libvoronoi.algorithms.components.CentroidIDMap;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.libvoronoi.io.ChunkingGridPersistence;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public record PaintGridSaveData(PaintGrid grid, LayerManager layers){


    public void writeToDataStream(DataOutputStream out) throws IOException{
        grid.writeToDataStream(out);
        layers.writeToDataStream(out);
    }

    public static PaintGridSaveData readFromDataStream(DataInputStream in) throws IOException{
        PaintGrid grid = ChunkingGrid.readFromDataStream(in, PAINT_GRID_FACTORY);
        LayerManager layers = LayerManager.readFromDataStream(in, grid);
        return new PaintGridSaveData(grid, layers);
    }


    private static final ChunkingGridPersistence.ChunkingGridFactory<PaintCentroidData, PaintGrid> PAINT_GRID_FACTORY = new ChunkingGridPersistence.ChunkingGridFactory<>(){

        @Override
        public PaintGrid constructGrid(Size size, ShortGrid centroidIDGrid, ShortGrid distToCentroidGrid, CentroidIDMap<PaintCentroidData> centroidDataMap){
            return new PaintGrid(size, centroidIDGrid, distToCentroidGrid, centroidDataMap);
        }

        @Override
        public PaintCentroidData readFromDataStream(DataInputStream in) throws IOException{
            return PaintCentroidData.readFromDataStream(in);
        }
    };

}
