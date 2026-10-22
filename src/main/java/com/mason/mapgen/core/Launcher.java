package com.mason.mapgen.core;

import com.mason.libgui.construction.BuildPipeline;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.gui.MapGenGUI;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.libvoronoi.algorithms.AnnexQueries;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection;


public class Launcher{


    public static void launch(){

        Size screenSize = new Size(1760, 960);
        Size toggleSize = new Size(36, 36);
        PaintGUIStateBuildCollection.GUIParams guiParams = new PaintGUIStateBuildCollection.GUIParams(screenSize, 460, toggleSize);
        PaintGUIStateBuildCollection.ChunkingGridParams chunkingGridParams = new PaintGUIStateBuildCollection.ChunkingGridParams(
                new Size(1200, 1200),
                8600,
                0,
                AnnexQueries.buildDefaultRandomQuery(RandomSource.asRandom())
        );

        PaintGUIStateBuildCollection buildCollection = new PaintGUIStateBuildCollection(guiParams, chunkingGridParams);
        BuildPipeline pipeline = BuildPipeline.constructPipelineWithDefaultUpdater(buildCollection);
        pipeline.build();
        GUIState paintState = buildCollection.getPaintGUIState();
        System.out.println("PaintGUIState Constructed");

        MapGenGUI gui = new MapGenGUI(screenSize, "MapGen", "assets/AppIcon.png");
        gui.start();
        System.out.println("GUI started");

        gui.switchState(paintState);
        System.out.println("State switched");

    }

}
