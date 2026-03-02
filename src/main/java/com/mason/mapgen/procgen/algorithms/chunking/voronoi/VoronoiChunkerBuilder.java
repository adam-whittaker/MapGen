package com.mason.mapgen.procgen.algorithms.chunking.voronoi;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.algorithms.chunking.*;
import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class VoronoiChunkerBuilder{

    public static <E extends CentroidData<E>> VoronoiChunkerSkeleton<E> buildSkeleton(Size size,
                                                                                      int numChunks,
                                                                                      int lloydRelaxCount,
                                                                                      CentroidDataInitializer<E> centroidDataInitializer,
                                                                                      FloodFillAnnexQuery<E> annexQuery){
        VoronoiChunkerSkeleton<E> skeleton = new VoronoiChunkerSkeleton<>();
        skeleton.setGrid(new ChunkingGrid<>(size));
        skeleton.setNumChunks(numChunks);
        skeleton.setLloydRelaxCount(lloydRelaxCount);
        skeleton.setCentroidDataInitializer(centroidDataInitializer);
        constructLloydRelaxFloodFill(skeleton);
        constructChunkingFloodFill(skeleton, annexQuery);
        return skeleton;
    }

    private static <E extends CentroidData<E>> void constructLloydRelaxFloodFill(VoronoiChunkerSkeleton<E> skeleton){
        CentroidFloodFill<E> lloydFloodFill = new CentroidFloodFill<>(skeleton.getGrid(), AnnexQueries::euclideanQuery, false);
        skeleton.setLloydRelaxFloodFill(lloydFloodFill);
    }

    private static <E extends CentroidData<E>> void constructChunkingFloodFill(VoronoiChunkerSkeleton<E> skeleton, FloodFillAnnexQuery<E> annexQuery){
        CentroidFloodFill<E> chunkingFloodFill = new CentroidFloodFill<>(skeleton.getGrid(), annexQuery, true);
        skeleton.setChunkingFloodFill(chunkingFloodFill);
    }

}
