package com.mason.mapgen.paint.components;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.mapgen.paint.builders.PaintGUIStateBuilder;
import com.mason.mapgen.paint.components.panes.*;
import com.mason.mapgen.paint.components.panes.leftPane.LeftPaintPane;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.paint.skeletons.PaintGUIStateSkeleton;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class PaintGUIState extends GUIState{


    private final PaintedImagePane paintedImagePane;
    private final LeftPaintPane leftPaintPane;
    private final RightPaintPane rightPaintPane;
    private final TopPaintPane topPaintPane;
    private final BottomPaintPane bottomPaintPane;

    private final PaintManager paintManager;


    private PaintGUIState(PaintGUIStateSkeleton skeleton){
        super(skeleton.getGuiStateSkeleton());
        this.paintedImagePane = skeleton.getPaintedImagePane();
        this.leftPaintPane = skeleton.getLeftPaintPane();
        this.rightPaintPane = skeleton.getRightPaintPane();
        this.topPaintPane = skeleton.getTopPaintPane();
        this.bottomPaintPane = skeleton.getBottomPaintPane();
        this.paintManager = skeleton.getPaintManager();
    }

    public static PaintGUIState buildWithEvenHorizontalPanes(Size screenSize, int sidePaneWidth, ChunkingGrid<PaintCentroidData> chunkingGrid){
        return new PaintGUIState(PaintGUIStateBuilder.buildSkeletonWithEvenHorizontalPanes(screenSize, sidePaneWidth, chunkingGrid));
    }

    public static PaintGUIState build(Size screenSize, int sidePaneWidth, int topPaneHeight, ChunkingGrid<PaintCentroidData> chunkingGrid){
        return new PaintGUIState(PaintGUIStateBuilder.buildSkeleton(screenSize, sidePaneWidth, topPaneHeight, chunkingGrid));
    }


    @Override
    public void setUp(GUIState previousState){

    }

    @Override
    public void tearDown(){

    }

}
