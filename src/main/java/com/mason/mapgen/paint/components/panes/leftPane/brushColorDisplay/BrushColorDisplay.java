package com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay;

import com.mason.mapgen.paint.components.misc.GridImageComponent;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;

public class BrushColorDisplay extends GridImageComponent{


    private final ChunkingGrid<PaintCentroidData> grid;
    private final PaintCentroidData primaryColorCentroid;
    private final PaintCentroidData secondaryColorCentroid;
    private final PaintCentroidData averageColorCentroid;

    private final RGBQuery primaryColor;
    private final RGBQuery secondaryColor;
    private final RGBQuery averageColor;



    private BrushColorDisplay(BrushColorDisplaySkeleton skeleton){
        super(skeleton.getCoord(), skeleton.getGrid());
        primaryColorCentroid = skeleton.getPrimaryColorCentroid();
        secondaryColorCentroid = skeleton.getSecondaryColorCentroid();
        averageColorCentroid = skeleton.getAverageColorCentroid();
        primaryColor = skeleton.getPrimaryRGBQuery();
        secondaryColor = skeleton.getSecondaryRGBQuery();
        averageColor = skeleton.getAverageRGBQuery();
        grid = skeleton.getGrid();
        skeleton.setBrushDisplayUpdate(this::update);
    }

    public static BrushColorDisplay build(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        return new BrushColorDisplay(BrushColorDisplayBuilder.buildSkeleton(leftPaintPaneSkeleton));
    }


    private void update(){
        displayColorOnCentroid(primaryColor, primaryColorCentroid);
        displayColorOnCentroid(secondaryColor, secondaryColorCentroid);
        displayColorOnCentroid(averageColor, averageColorCentroid);
    }

    private void displayColorOnCentroid(RGBQuery colorState, PaintCentroidData centroid){
        centroid.setColor(sampleRGBColor(colorState));
        updateImageInClip(grid, grid.constructBoundingRectangle(centroid.getCoord()));
    }

    private Color sampleRGBColor(RGBQuery colorState){
        return new Color(colorState.getRed(), colorState.getGreen(), colorState.getBlue());
    }

}
