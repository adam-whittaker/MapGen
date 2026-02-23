package com.mason.mapgen.core;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.MapGenGUI;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.mapgen.paint.components.*;
import com.mason.mapgen.procgen.algorithms.chunking.AnnexQueries;
import com.mason.mapgen.procgen.algorithms.chunking.VoronoiChunker;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;


public class Launcher{


    public static void launch(){

        VoronoiChunker<PaintCentroidData> chunker = new VoronoiChunker<>(new Size(1200, 1200),
                2400, 0, PaintCentroidData::new, AnnexQueries::randomQuery);
        chunker.createChunks();
        ChunkingGrid<PaintCentroidData> chunkingGrid = chunker.getGrid();
        System.out.println("Chunks created");

        Size screenSize = new Size(1680, 840);
        MapGenGUI gui = new MapGenGUI(screenSize, "MapGen", "assets/AppIcon.png");
        GUIState paintState = PaintGUIState.build(screenSize, chunkingGrid);
        System.out.println("GUI created");

        gui.switchState(paintState);
        System.out.println("State switched");

        gui.start();
        System.out.println("GUI started");

    }

}
