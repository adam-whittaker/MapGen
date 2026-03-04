package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.builders.BrushColorDisplayBuilder;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.logic.tools.brush.BrushColor;
import com.mason.mapgen.paint.skeletons.BrushColorDisplaySkeleton;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;
import java.util.function.Supplier;

import static com.mason.mapgen.core.Utils.lerp;

public class BrushColorDisplay extends GridImageComponent{


    private final PaintCentroidData primaryColorCentroid;
    private final PaintCentroidData secondaryColorCentroid;
    private final PaintCentroidData averageColorCentroid;
    private Supplier<Double> brushCentrePeeker;
    private Supplier<Integer> brushAlphaPeeker;
    private final ChunkingGrid<PaintCentroidData> grid;


    private BrushColorDisplay(BrushColorDisplaySkeleton skeleton){
        super(skeleton.getGrid());
        primaryColorCentroid = skeleton.getPrimaryColorCentroid();
        secondaryColorCentroid = skeleton.getSecondaryColorCentroid();
        averageColorCentroid = skeleton.getAverageColorCentroid();
        grid = skeleton.getGrid();
    }

    public static BrushColorDisplay build(Size size){
        return new BrushColorDisplay(BrushColorDisplayBuilder.buildSkeleton(size));
    }


    public void setBrushPeekers(BrushColor brushColor){
        brushCentrePeeker = brushColor::getCentre;
        brushAlphaPeeker = brushColor::getAlpha;
    }


    public void displayPrimaryColor(Color color){
        displayColorOnCentroid(color, primaryColorCentroid);
        updateAverageColor();
    }

    public void displaySecondaryColor(Color color){
        displayColorOnCentroid(color, secondaryColorCentroid);
        updateAverageColor();
    }

    private void displayColorOnCentroid(Color color, PaintCentroidData centroid){
        centroid.setColor(color);
        updateImageInClip(grid, grid.constructBoundingRectangle(centroid.getCoord()));
    }

    private void updateAverageColor(){
        verifyBrushPeekersSet();
        Color averageColor = getWeightedColor(primaryColorCentroid.getColor(), secondaryColorCentroid.getColor());
        displayColorOnCentroid(averageColor, averageColorCentroid);
    }

    private void verifyBrushPeekersSet(){
        if(brushCentrePeeker == null || brushAlphaPeeker == null){
            throw new IllegalStateException("Brush peekers not set");
        }
    }

    private Color getWeightedColor(Color primary, Color secondary){
        double weight = brushCentrePeeker.get();
        int alpha = brushAlphaPeeker.get();
        int red = lerp(primary.getRed(), secondary.getRed(), weight);
        int green = lerp(primary.getGreen(), secondary.getGreen(), weight);
        int blue = lerp(primary.getBlue(), secondary.getBlue(), weight);
        return new Color(red, green, blue, alpha);
    }

}
