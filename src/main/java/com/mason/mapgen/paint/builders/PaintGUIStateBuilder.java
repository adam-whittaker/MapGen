package com.mason.mapgen.paint.builders;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.paint.components.panes.leftPane.PaletteImageComponent;
import com.mason.mapgen.paint.components.panes.*;
import com.mason.mapgen.paint.components.panes.leftPane.LeftPaintPane;
import com.mason.mapgen.paint.logic.PaintCanvas;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.skeletons.PaintGUIStateSkeleton;
import com.mason.mapgen.procgen.algorithms.chunking.AnnexQueries;
import com.mason.mapgen.procgen.algorithms.chunking.voronoi.VoronoiChunker;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;
import com.mason.mapgen.procgen.algorithms.chunking.voronoi.VoronoiChunkerBuilder;
import com.mason.mapgen.procgen.algorithms.chunking.voronoi.VoronoiChunkerSkeleton;

public class PaintGUIStateBuilder{


    private static final int MIN_TOP_PANE_HEIGHT = 48;


    public static PaintGUIStateSkeleton buildSkeletonWithEvenHorizontalPanes(Size screenSize, int sidePaneWidth, ChunkingGrid<PaintCentroidData> chunkingGrid){
        return buildSkeleton(screenSize,
                sidePaneWidth,
                calculateEvenTopPaneHeight(screenSize, sidePaneWidth),
                chunkingGrid);
    }

    private static int calculateEvenTopPaneHeight(Size screenSize, int sidePaneWidth){
        int imageDimension = screenSize.width() - 2 * sidePaneWidth;
        int topPaneHeight = (screenSize.height() - imageDimension)/2;
        if(topPaneHeight < MIN_TOP_PANE_HEIGHT){
            throw new IllegalArgumentException("sidePaneWidth too small for given screenSize.");
        }
        return topPaneHeight;
    }

    public static PaintGUIStateSkeleton buildSkeleton(Size screenSize, int sidePaneWidth, int topPaneHeight, ChunkingGrid<PaintCentroidData> chunkingGrid){
        GridImageComponent imageComponent = new GridImageComponent(chunkingGrid);
        HitboxRect paneBoundary = constructPaneBoundary(screenSize, sidePaneWidth, topPaneHeight);
        ChunkingGrid<PaintCentroidData> paletteGrid = constructPaletteGrid(screenSize, sidePaneWidth, 30);
        PaletteImageComponent paletteComponent = new PaletteImageComponent(paletteGrid);
        PaintCanvas palette = new PaintCanvas(paletteGrid, paletteComponent);
        PaintManager paintManager = constructPaintManager(chunkingGrid, imageComponent, palette);
        paletteComponent.setPaintManager(paintManager);
        PaintGUIStateSkeleton skeleton = buildSkeletonFromSubPanes(
                PaintedImagePane.build(paneBoundary, imageComponent),
                LeftPaintPane.build(screenSize, sidePaneWidth, paletteComponent, paintManager),
                RightPaintPane.build(screenSize, sidePaneWidth),
                TopPaintPane.build(screenSize, sidePaneWidth, topPaneHeight),
                BottomPaintPane.build(screenSize, sidePaneWidth, topPaneHeight));
        skeleton.setPaintManager(paintManager);
        skeleton.getPaintedImagePane().addMouseInputListener(skeleton.getPaintManager());
        return skeleton;
    }

    private static HitboxRect constructPaneBoundary(Size screenSize, int sidePaneWidth, int topPaneHeight){
        int width = screenSize.width() - 2 * sidePaneWidth;
        int height = width;
        return new HitboxRect(new Coord(sidePaneWidth, topPaneHeight), new Size(width, height));
    }

    private static ChunkingGrid<PaintCentroidData> constructPaletteGrid(Size screenSize, int sidePaneWidth, int numChunks){
        Size paletteSize = new Size(sidePaneWidth-128, screenSize.height()/3);
        VoronoiChunker<PaintCentroidData> paletteChunker = constructPaletteChunker(paletteSize, numChunks);
        paletteChunker.createChunks();
        ChunkingGrid<PaintCentroidData> grid = paletteChunker.getGrid();
        //slow
        grid.updateMaxDistToCentroid();
        return grid;
    }

    private static VoronoiChunker<PaintCentroidData> constructPaletteChunker(Size paletteSize, int numChunks){
        VoronoiChunkerSkeleton<PaintCentroidData> paletteChunkerSkeleton =
                VoronoiChunkerBuilder.buildSkeleton(paletteSize, numChunks, 1, PaintCentroidData::new, AnnexQueries::manhattanQuery);
        paletteChunkerSkeleton.preventCentroidGraph();
        return VoronoiChunker.buildFromSkeleton(paletteChunkerSkeleton);
    }

    private static PaintManager constructPaintManager(ChunkingGrid<PaintCentroidData> chunkingGrid, GridImageComponent imageComponent, PaintCanvas palette){
        PaintCanvas canvas = new PaintCanvas(chunkingGrid, imageComponent);
        return new PaintManager(canvas, palette);
    }

    private static PaintGUIStateSkeleton buildSkeletonFromSubPanes(PaintedImagePane paintedImagePane,
                                                                   LeftPaintPane leftPaintPane,
                                                                   RightPaintPane rightPaintPane,
                                                                   TopPaintPane topPaintPane,
                                                                   BottomPaintPane bottomPaintPane){
        GUIStateSkeleton guiStateSkeleton = new GUIStateSkeleton();
        registerPaneWithGUIStateSkeleton(paintedImagePane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(leftPaintPane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(rightPaintPane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(topPaintPane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(bottomPaintPane, guiStateSkeleton);

        PaintGUIStateSkeleton skeleton = new PaintGUIStateSkeleton();
        skeleton.setGuiStateSkeleton(guiStateSkeleton);
        skeleton.setPaintedImagePane(paintedImagePane);
        skeleton.setLeftPaintPane(leftPaintPane);
        skeleton.setRightPaintPane(rightPaintPane);
        skeleton.setTopPaintPane(topPaintPane);
        skeleton.setBottomPaintPane(bottomPaintPane);
        return skeleton;
    }

    private static void registerPaneWithGUIStateSkeleton(Pane pane, GUIStateSkeleton guiStateSkeleton){
        guiStateSkeleton.addComponent(pane);
        pane.setInputSource(guiStateSkeleton);
    }

}
