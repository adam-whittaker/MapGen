package com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunker;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunkerBuilder;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunkerSkeleton;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneBuildComponent;
import com.mason.mapgen.paint.logic.canvas.PaintCanvas;

import java.util.Set;

import static com.mason.mapgen.paint.components.misc.PaintGrid.PAINT_GRID_INITIALIZER;
import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.CHUNKING_GRID_PARAMS;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.PAINT_TOOL_QUERY;

public class PaletteCanvasControllerBuildComponent extends ComponentBuildResource<PaletteCanvasController>{


    public static final PaletteCanvasControllerBuildComponent INSTANCE = new PaletteCanvasControllerBuildComponent();


    private PaletteCanvasControllerBuildComponent(){}


    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PAINT_TOOL_QUERY,
                LeftPaintPaneBuildComponent.LAYOUT,
                CHUNKING_GRID_PARAMS
        );
    }

    @Override
    protected PaletteCanvasController build(){
        return new PaletteCanvasController(constructCanvas(), PAINT_TOOL_QUERY.get());
    }

    private PaintCanvas constructCanvas(){
        PaneLayout paneLayout = LeftPaintPaneBuildComponent.LAYOUT.get();
        RectQuery boundary = paneLayout.getBounds("PALETTE");
        VoronoiChunker<PaintCentroidData, PaintGrid> paletteChunker = constructPaletteChunker(boundary.getSize());
        paletteChunker.createChunks();
        return new PaintCanvas(boundary.getCoord(), paletteChunker.getGrid());
    }

    private VoronoiChunker<PaintCentroidData, PaintGrid> constructPaletteChunker(Size boundarySize){
        PaintGUIStateBuildCollection.ChunkingGridParams params = CHUNKING_GRID_PARAMS.get();
        VoronoiChunkerSkeleton<PaintCentroidData, PaintGrid> paletteChunkerSkeleton = VoronoiChunkerBuilder.buildSkeleton(
                RandomSource.asRandom(),
                boundarySize,
                calcNumChunks(params, boundarySize),
                params.lloydRelaxCount(),
                PAINT_GRID_INITIALIZER,
                params.query()
        );
        return VoronoiChunker.buildFromSkeleton(paletteChunkerSkeleton);
    }

    private int calcNumChunks(PaintGUIStateBuildCollection.ChunkingGridParams params, Size boundarySize){
        Size mainSize = params.size();
        double sizeFactor = (double) boundarySize.width() * boundarySize.height() / (mainSize.width() * mainSize.height());
        return (int) Math.ceil(params.numChunks() * sizeFactor);
    }


    @Override
    public String toString(){
        return "PALETTE_CANVAS_CONTROLLER";
    }

}
