package com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libvoronoi.algorithms.AnnexQueries;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunker;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunkerBuilder;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunkerSkeleton;

public class PaletteCanvasControllerBuilder{


    public static PaletteCanvasControllerSkeleton buildSkeleton(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        PaletteCanvasControllerSkeleton skeleton = packFieldsIntoPaletteCanvasControllerSkeleton(leftPaintPaneSkeleton);
        skeleton.setCanvas(constructCanvas(skeleton));
        return skeleton;
    }

    private static PaletteCanvasControllerSkeleton packFieldsIntoPaletteCanvasControllerSkeleton(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        PaintControlSettingsSkeleton settingsSkeleton = leftPaintPaneSkeleton.getPaintControlSettingsSkeleton();
        PaneLayout paneLayout = leftPaintPaneSkeleton.getPaneLayout();

        PaletteCanvasControllerSkeleton paletteSkeleton = new PaletteCanvasControllerSkeleton();
        paletteSkeleton.setNumChunks(30);
        paletteSkeleton.setLloydRelaxCount(1);
        paletteSkeleton.setBoundary(paneLayout.getBounds("PALETTE"));
        paletteSkeleton.setAnnexQuery(AnnexQueries::manhattanQuery);
        paletteSkeleton.setCurrentToolQuery(settingsSkeleton.getPaintToolQuery());
        paletteSkeleton.setAlphaState(settingsSkeleton.getAlphaState());
        paletteSkeleton.setCertaintyState(settingsSkeleton.getCertaintyPositionState());
        return paletteSkeleton;
    }

    private static PaintCanvas constructCanvas(PaletteCanvasControllerSkeleton skeleton){
        VoronoiChunker<PaintCentroidData> paletteChunker = constructPaletteChunker(skeleton);
        paletteChunker.createChunks();
        ChunkingGrid<PaintCentroidData> grid = paletteChunker.getGrid();

        RectQuery boundary = skeleton.getBoundary();
        return new PaintCanvas(boundary.getCoord(), grid);
    }

    private static VoronoiChunker<PaintCentroidData> constructPaletteChunker(PaletteCanvasControllerSkeleton skeleton){
        RectQuery boundary = skeleton.getBoundary();
        VoronoiChunkerSkeleton<PaintCentroidData> paletteChunkerSkeleton = VoronoiChunkerBuilder.buildSkeleton(
                RandomSource.asRandom(),
                boundary.getSize(),
                skeleton.getNumChunks(),
                skeleton.getLloydRelaxCount(),
                PaintCentroidData::new,
                skeleton.getAnnexQuery());
        paletteChunkerSkeleton.preventCentroidGraph();
        return VoronoiChunker.buildFromSkeleton(paletteChunkerSkeleton);
    }

}
