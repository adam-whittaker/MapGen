package com.mason.mapgen.core;

import com.mason.libstruct.geo.Size;
import com.mason.libvoronoi.algorithms.FloodFillAnnexQuery;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.gui.MapGenGUI;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIState;
import com.mason.libvoronoi.algorithms.AnnexQueries;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunker;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;


public class Launcher{


    public static void launch(){

        FloodFillAnnexQuery<PaintCentroidData> randomQuery = AnnexQueries.buildDefaultRandomQuery(RandomSource.asRandom());
        VoronoiChunker<PaintCentroidData> chunker = VoronoiChunker.build(
                RandomSource.asRandom(),
                new Size(1200, 1200),
                8600,
                0,
                PaintCentroidData::new,
                randomQuery);

        chunker.createChunks();
        ChunkingGrid<PaintCentroidData> chunkingGrid = chunker.getGrid();
        chunkingGrid.updateMaxDistToCentroid();
        System.out.println("Chunks created");

        Size screenSize = /*new Size(1680, 840)*/ new Size(1760, 960);
        GUIState paintState = PaintGUIState.buildWithEvenHorizontalPanes(screenSize, 460, chunkingGrid);
        MapGenGUI gui = new MapGenGUI(screenSize, "MapGen", "assets/AppIcon.png");
        gui.start();
        System.out.println("GUI started");

        gui.switchState(paintState);
        System.out.println("State switched");

    }

}
