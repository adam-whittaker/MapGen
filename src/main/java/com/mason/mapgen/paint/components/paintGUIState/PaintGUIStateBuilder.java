package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.panes.bottomPane.BottomPaintPane;
import com.mason.mapgen.paint.components.panes.imagePane.PaintedImagePane;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPane;
import com.mason.mapgen.paint.components.panes.rightPane.RightPaintPane;
import com.mason.mapgen.paint.components.panes.topPane.TopPaintPane;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import static java.lang.Math.min;

public class PaintGUIStateBuilder{


    private static final int MIN_VERTICAL_PANE_HEIGHT = 48;
    private static final int MIN_HORIZONTAL_PANE_WIDTH = 396;


    public static PaintGUIStateSkeleton buildSkeletonWithEvenHorizontalPanes(Size screenSize, int sidePaneWidth, ChunkingGrid<PaintCentroidData> chunkingGrid){
        PaintGUIStateLayoutParameters evenParams = constructEvenLayoutParameters(screenSize, sidePaneWidth);
        return buildSkeleton(evenParams, chunkingGrid);
    }

    private static PaintGUIStateLayoutParameters constructEvenLayoutParameters(Size screenSize, int sidePaneWidth){
        double imageDimension = screenSize.width() - 2 * sidePaneWidth;
        double verticalPaneHeight = (screenSize.height() - imageDimension)/2;
        double horizontalRatio = (double)sidePaneWidth/screenSize.width();
        double verticalRatio = verticalPaneHeight/screenSize.height();

        PaintGUIStateLayoutParameters params = new PaintGUIStateLayoutParameters();
        params.setScreenSize(screenSize);
        params.setLeftPaneWidthRatio(horizontalRatio);
        params.setRightPaneWidthRatio(horizontalRatio);
        params.setTopPaneHeightRatio(verticalRatio);
        params.setBottomPaneHeightRatio(verticalRatio);
        return params;
    }


    public static PaintGUIStateSkeleton buildSkeleton(PaintGUIStateLayoutParameters params, ChunkingGrid<PaintCentroidData> chunkingGrid){
        verifyParamsLargeEnough(params);
        PaintGUIStateSkeleton skeleton = buildSkeletonWithInitialComponents(params, chunkingGrid);
        addSubPanesToSkeleton(skeleton,
                PaintedImagePane.build(skeleton),
                LeftPaintPane.build(skeleton),
                RightPaintPane.build(skeleton),
                TopPaintPane.build(skeleton),
                BottomPaintPane.build(skeleton));
        return skeleton;
    }

    private static void verifyParamsLargeEnough(PaintGUIStateLayoutParameters params){
        Size size = params.getScreenSize();
        double verticalPaneRatio = min(params.getBottomPaneHeightRatio(), params.getTopPaneHeightRatio());
        double horizontalPaneRatio = min(params.getLeftPaneWidthRatio(), params.getRightPaneWidthRatio());
        if(verticalPaneRatio * size.height() < MIN_VERTICAL_PANE_HEIGHT){
            throw new IllegalArgumentException("vertical pane too small.");
        }
        if(horizontalPaneRatio * size.width() < MIN_HORIZONTAL_PANE_WIDTH){
            throw new IllegalArgumentException("horizontal pane too small.");
        }
    }

    private static PaintGUIStateSkeleton buildSkeletonWithInitialComponents(PaintGUIStateLayoutParameters params, ChunkingGrid<PaintCentroidData> chunkingGrid){
        PaintGUIStateSkeleton skeleton = new PaintGUIStateSkeleton();
        skeleton.setScreenSize(params.getScreenSize());
        skeleton.setLayout(new PaintGUIStateLayout(params));
        skeleton.setMainCanvasChunkingGrid(chunkingGrid);
        setUpPaintKeyProcessor(skeleton);
        return skeleton;
    }

    private static void setUpPaintKeyProcessor(PaintGUIStateSkeleton skeleton){
        PaintKeyProcessor paintKeyProcessor = new PaintKeyProcessor();
        skeleton.setPaintKeyProcessor(paintKeyProcessor);
        skeleton.addKeyListener(paintKeyProcessor);
    }

    private static void addSubPanesToSkeleton(PaintGUIStateSkeleton skeleton,
                                               PaintedImagePane paintedImagePane,
                                               LeftPaintPane leftPaintPane,
                                               RightPaintPane rightPaintPane,
                                               TopPaintPane topPaintPane,
                                               BottomPaintPane bottomPaintPane){
        registerPaneWithGUIStateSkeleton(paintedImagePane, skeleton);
        registerPaneWithGUIStateSkeleton(leftPaintPane, skeleton);
        registerPaneWithGUIStateSkeleton(rightPaintPane, skeleton);
        registerPaneWithGUIStateSkeleton(topPaintPane, skeleton);
        registerPaneWithGUIStateSkeleton(bottomPaintPane, skeleton);

        skeleton.setPaintedImagePane(paintedImagePane);
        skeleton.setLeftPaintPane(leftPaintPane);
        skeleton.setRightPaintPane(rightPaintPane);
        skeleton.setTopPaintPane(topPaintPane);
        skeleton.setBottomPaintPane(bottomPaintPane);
    }

    private static void registerPaneWithGUIStateSkeleton(Pane pane, GUIStateSkeleton guiStateSkeleton){
        guiStateSkeleton.addComponent(pane);
        pane.setInputSource(guiStateSkeleton);
    }

}
