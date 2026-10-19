package com.mason.mapgen.paint.buildResources;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunker;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.ChunkingGridParams;

import java.util.Set;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.CHUNKING_GRID_PARAMS;

public class MainChunkingGridBuildComponent extends ComponentBuildResource<ChunkingGrid<PaintCentroidData>>{


    public final static MainChunkingGridBuildComponent INSTANCE = new MainChunkingGridBuildComponent();


    private MainChunkingGridBuildComponent(){
        super();
    }


    @Override
    public ChunkingGrid<PaintCentroidData> build(){
        ChunkingGridParams params = CHUNKING_GRID_PARAMS.get();
        VoronoiChunker<PaintCentroidData> chunker = VoronoiChunker.build(
                RandomSource.asRandom(),
                params.size(),
                params.numChunks(),
                params.lloydRelaxCount(),
                PaintCentroidData::new,
                params.query());
        chunker.createChunks();
        return chunker.getGrid();
    }

    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(CHUNKING_GRID_PARAMS);
    }


    @Override
    public String toString(){
        return "MAIN_CHUNKING_GRID";
    }

}
