package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.panes.bottomPane.BottomPaintPane;
import com.mason.mapgen.paint.components.panes.imagePane.PaintedImagePane;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPane;
import com.mason.mapgen.paint.components.panes.rightPane.RightPaintPane;
import com.mason.mapgen.paint.components.panes.topPane.TopPaintPane;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class PaintGUIState extends GUIState{


    private final PaintedImagePane paintedImagePane;
    private final LeftPaintPane leftPaintPane;
    private final RightPaintPane rightPaintPane;
    private final TopPaintPane topPaintPane;
    private final BottomPaintPane bottomPaintPane;


    private PaintGUIState(PaintGUIStateSkeleton skeleton){
        super(skeleton);
        this.paintedImagePane = skeleton.getPaintedImagePane();
        this.leftPaintPane = skeleton.getLeftPaintPane();
        this.rightPaintPane = skeleton.getRightPaintPane();
        this.topPaintPane = skeleton.getTopPaintPane();
        this.bottomPaintPane = skeleton.getBottomPaintPane();
    }

    public static PaintGUIState buildWithEvenHorizontalPanes(Size screenSize, int sidePaneWidth, ChunkingGrid<PaintCentroidData> chunkingGrid){
        return new PaintGUIState(PaintGUIStateBuilder.buildSkeletonWithEvenHorizontalPanes(screenSize, sidePaneWidth, chunkingGrid));
    }

    public static PaintGUIState build(PaintGUIStateLayoutParameters params, ChunkingGrid<PaintCentroidData> chunkingGrid){
        return new PaintGUIState(PaintGUIStateBuilder.buildSkeleton(params, chunkingGrid));
    }


    @Override
    public void setUp(GUIState previousState){

    }

    @Override
    public void tearDown(){

    }

}
