package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunker;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.ChunkingGridParams;

import java.util.Set;

import static com.mason.mapgen.paint.components.misc.PaintGrid.PAINT_GRID_INITIALIZER;
import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.CHUNKING_GRID_PARAMS;

public class MainPaintGridBuildComponent extends ComponentBuildResource<PaintGrid>{


    public final static MainPaintGridBuildComponent INSTANCE = new MainPaintGridBuildComponent();


    private MainPaintGridBuildComponent(){
        super();
    }


    @Override
    public PaintGrid build(){
        ChunkingGridParams params = CHUNKING_GRID_PARAMS.get();
        VoronoiChunker<PaintCentroidData, PaintGrid> chunker = VoronoiChunker.build(
                RandomSource.asRandom(),
                params.size(),
                params.numChunks(),
                params.lloydRelaxCount(),
                PAINT_GRID_INITIALIZER,
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
