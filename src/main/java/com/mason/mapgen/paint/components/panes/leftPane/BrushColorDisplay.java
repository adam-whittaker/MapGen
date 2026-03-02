package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.builders.BrushColorDisplayBuilder;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.skeletons.BrushColorDisplaySkeleton;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;

public class BrushColorDisplay extends GridImageComponent{


    private final PaintCentroidData primaryColorCentroid;
    private final PaintCentroidData secondaryColorCentroid;
    private final ChunkingGrid<PaintCentroidData> grid;


    private BrushColorDisplay(BrushColorDisplaySkeleton skeleton){
        super(skeleton.getGrid());
        primaryColorCentroid = skeleton.getPrimaryColorCentroid();
        secondaryColorCentroid = skeleton.getSecondaryColorCentroid();
        grid = skeleton.getGrid();
    }

    public static BrushColorDisplay build(Size size){
        return new BrushColorDisplay(BrushColorDisplayBuilder.buildSkeleton(size));
    }


    public void displayPrimaryColor(Color color){
        displayColorOnCentroid(color, primaryColorCentroid);
    }

    public void displaySecondaryColor(Color color){
        displayColorOnCentroid(color, secondaryColorCentroid);
    }

    private void displayColorOnCentroid(Color color, PaintCentroidData centroid){
        centroid.setColor(color);
        updateImageInClip(grid, grid.constructBoundingRectangle(centroid.getCoord()));
    }

}
