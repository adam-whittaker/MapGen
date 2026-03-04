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
import com.mason.mapgen.paint.logic.PaintKeyProcessor;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.paint.skeletons.PaintGUIStateSkeleton;
import com.mason.mapgen.paint.skeletons.PaintManagerSkeleton;
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
        PaintGUIStateSkeleton skeleton = buildSkeletonWithInitialComponents(screenSize, sidePaneWidth, topPaneHeight, chunkingGrid);
        addSubPanesToSkeleton(skeleton,
                PaintedImagePane.build(skeleton),
                LeftPaintPane.build(skeleton, sidePaneWidth),
                RightPaintPane.build(screenSize, sidePaneWidth),
                TopPaintPane.build(screenSize, sidePaneWidth, topPaneHeight),
                BottomPaintPane.build(screenSize, sidePaneWidth, topPaneHeight));

        skeleton.getPaintedImagePane().addMouseInputListener(skeleton.getPaintManager());
        skeleton.getPaintedImagePane().addKeyListener(skeleton.getPaintManager());

        return skeleton;
    }

    private static PaintGUIStateSkeleton buildSkeletonWithInitialComponents(Size screenSize, int sidePaneWidth, int topPaneHeight, ChunkingGrid<PaintCentroidData> chunkingGrid){
        PaintGUIStateSkeleton skeleton = new PaintGUIStateSkeleton();
        skeleton.setGuiStateSkeleton(new GUIStateSkeleton());
        skeleton.setScreenSize(screenSize);
        skeleton.setPaintedImagePaneBoundary(constructPaneBoundary(screenSize, sidePaneWidth, topPaneHeight));
        skeleton.setMainCanvasChunkingGrid(chunkingGrid);

        constructAndAddPaletteToSkeleton(skeleton, sidePaneWidth);
        constructAndAddPaintManagerToSkeleton(skeleton);

        return skeleton;
    }

    private static HitboxRect constructPaneBoundary(Size screenSize, int sidePaneWidth, int topPaneHeight){
        int width = screenSize.width() - 2 * sidePaneWidth;
        int height = width;
        return new HitboxRect(new Coord(sidePaneWidth, topPaneHeight), new Size(width, height));
    }

    private static void constructAndAddPaletteToSkeleton(PaintGUIStateSkeleton skeleton, int sidePaneWidth){
        ChunkingGrid<PaintCentroidData> paletteGrid = constructPaletteGrid(skeleton.getScreenSize(), sidePaneWidth, 30);
        PaletteImageComponent paletteImageComponent = new PaletteImageComponent(paletteGrid);
        PaintCanvas palette = new PaintCanvas(paletteGrid, paletteImageComponent);
        skeleton.setPaletteImageComponent(paletteImageComponent);
        skeleton.setPalette(palette);
    }

    private static ChunkingGrid<PaintCentroidData> constructPaletteGrid(Size screenSize, int sidePaneWidth, int numChunks){
        Size paletteSize = new Size(sidePaneWidth-128, screenSize.height()/3 - 64);
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

    private static void constructAndAddPaintManagerToSkeleton(PaintGUIStateSkeleton skeleton){
        PaintKeyProcessor paintKeyProcessor = new PaintKeyProcessor();
        skeleton.setPaintKeyProcessor(paintKeyProcessor);
        skeleton.setPaintImageComponent(new GridImageComponent(skeleton.getMainCanvasChunkingGrid()));
        PaintManager paintManager = constructPaintManager(skeleton);
        skeleton.getPaletteImageComponent().setPaintManager(paintManager);
        skeleton.setPaintManager(paintManager);
    }

    private static PaintManager constructPaintManager(PaintGUIStateSkeleton skeleton){
        PaintManagerSkeleton paintManagerSkeleton = new PaintManagerSkeleton();
        PaintCanvas mainCanvas = new PaintCanvas(skeleton.getMainCanvasChunkingGrid(), skeleton.getPaintImageComponent());
        paintManagerSkeleton.setMainCanvas(mainCanvas);
        paintManagerSkeleton.setPalette(skeleton.getPalette());
        paintManagerSkeleton.setPaintKeyProcessor(skeleton.getPaintKeyProcessor());
        return PaintManager.build(paintManagerSkeleton);
    }

    private static void addSubPanesToSkeleton(PaintGUIStateSkeleton skeleton,
                                               PaintedImagePane paintedImagePane,
                                               LeftPaintPane leftPaintPane,
                                               RightPaintPane rightPaintPane,
                                               TopPaintPane topPaintPane,
                                               BottomPaintPane bottomPaintPane){
        GUIStateSkeleton guiStateSkeleton = skeleton.getGuiStateSkeleton();
        registerPaneWithGUIStateSkeleton(paintedImagePane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(leftPaintPane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(rightPaintPane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(topPaintPane, guiStateSkeleton);
        registerPaneWithGUIStateSkeleton(bottomPaintPane, guiStateSkeleton);

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
