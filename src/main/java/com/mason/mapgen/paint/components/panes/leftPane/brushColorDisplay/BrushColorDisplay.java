package com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay;

import com.mason.mapgen.paint.components.misc.GridImageComponent;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;

public class BrushColorDisplay extends GridImageComponent{


    private final PaintGrid grid;
    private final PaintCentroidData primaryColorCentroid;
    private final PaintCentroidData secondaryColorCentroid;
    private final PaintCentroidData averageColorCentroid;

    private final RGBQuery primaryColor;
    private final RGBQuery secondaryColor;
    private final RGBQuery averageColor;


    BrushColorDisplay(BrushColorDisplaySkeleton skeleton){
        super(skeleton.getCoord(), skeleton.getGrid(), 0);
        primaryColorCentroid = skeleton.getPrimaryColorCentroid();
        secondaryColorCentroid = skeleton.getSecondaryColorCentroid();
        averageColorCentroid = skeleton.getAverageColorCentroid();
        primaryColor = skeleton.getPrimaryRGBQuery();
        secondaryColor = skeleton.getSecondaryRGBQuery();
        averageColor = skeleton.getAverageRGBQuery();
        grid = skeleton.getGrid();
        skeleton.setBrushDisplayUpdate(this::updateAllColors);
        skeleton.setAverageBrushColorDisplayUpdate(this::updateAverageColor);
    }


    private void updateAllColors(){
        displayColorOnCentroid(primaryColor, primaryColorCentroid);
        displayColorOnCentroid(secondaryColor, secondaryColorCentroid);
        displayColorOnCentroid(averageColor, averageColorCentroid);
    }

    private void displayColorOnCentroid(RGBQuery colorState, PaintCentroidData centroid){
        centroid.setColor(0, colorState.sampleRGBColor());
        updateImageOverIndices(grid, centroid.pointIndices());
    }


    private void updateAverageColor(){
        displayColorOnCentroid(averageColor, averageColorCentroid);
    }

}
