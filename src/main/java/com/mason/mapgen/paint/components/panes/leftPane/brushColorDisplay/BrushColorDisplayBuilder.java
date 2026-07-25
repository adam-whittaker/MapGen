package com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libstruct.geo.*;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libvoronoi.algorithms.AnnexQueries;
import com.mason.libvoronoi.algorithms.FloodFillAnnexQuery;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunker;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunkerBuilder;
import com.mason.libvoronoi.algorithms.voronoi.VoronoiChunkerSkeleton;
import com.mason.libvoronoi.misc.RandomCoords;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;

import java.awt.*;
import java.util.*;

public class BrushColorDisplayBuilder{


    public static BrushColorDisplaySkeleton buildSkeleton(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        BrushColorDisplaySkeleton skeleton = buildSkeletonWithInitialFields(leftPaintPaneSkeleton);
        CoordSlot coordSlot = new CoordSlot();
        ChunkingGrid<PaintCentroidData> grid = buildGrid(skeleton.getSize(), coordSlot);
        makeOuterCentroidsTransparent(grid, coordSlot);
        skeleton.setGrid(grid);
        addColorCentroidsToSkeleton(skeleton, grid, coordSlot);
        return skeleton;
    }

    private static BrushColorDisplaySkeleton buildSkeletonWithInitialFields(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        PaintControlSettingsSkeleton controlSettingsSkeleton = leftPaintPaneSkeleton.getPaintControlSettingsSkeleton();
        BrushColorDisplaySkeleton skeleton = new BrushColorDisplaySkeleton();
        skeleton.setPrimaryRGBQuery(controlSettingsSkeleton.getPrimaryColorState());
        skeleton.setSecondaryRGBQuery(controlSettingsSkeleton.getSecondaryColorState());
        skeleton.setAverageRGBQuery(controlSettingsSkeleton.getAverageRGBQuery());
        skeleton.setBrushColorDisplayUpdaterSlot(leftPaintPaneSkeleton.getBrushColorDisplayUpdateSlot());
        skeleton.setAverageBrushColorDisplayUpdaterSlot(leftPaintPaneSkeleton.getAverageBrushColorDisplayUpdateSlot());
        skeleton.setBoundary(constructBrushSettingsBoundary(leftPaintPaneSkeleton));
        return skeleton;
    }

    private static RectQuery constructBrushSettingsBoundary(LeftPaintPaneSkeleton leftPaintPaneSkeleton){
        PaneLayout layout = leftPaintPaneSkeleton.getPaneLayout();
        return layout.getBounds("BRUSH_COLOR_DISPLAY");
    }

    private static ChunkingGrid<PaintCentroidData> buildGrid(Size size, CoordSlot coordSlot){
        VoronoiChunker<PaintCentroidData> chunker = buildChunker(size, coordSlot);
        chunker.createChunks();
        ChunkingGrid<PaintCentroidData> grid = chunker.getGrid();
        return grid;
    }

    private static VoronoiChunker<PaintCentroidData> buildChunker(Size size, CoordSlot coordSlot){
        FloodFillAnnexQuery<PaintCentroidData> randomQuery = AnnexQueries.buildDefaultRandomQuery(RandomSource.asRandom());
        VoronoiChunkerSkeleton<PaintCentroidData> voronoiChunkerSkeleton =
                VoronoiChunkerBuilder.buildSkeleton(RandomSource.asRandom(), size, -1, 0, PaintCentroidData::new, randomQuery);
        voronoiChunkerSkeleton.preventCentroidGraph();
        voronoiChunkerSkeleton.setRandomCoordGenerator((boundingSize, numCoords) -> generateCoords(boundingSize, coordSlot));
        return VoronoiChunker.buildFromSkeleton(voronoiChunkerSkeleton);
    }

    private static Set<Coord> generateCoords(Size size, CoordSlot coordSlot){
        int w = size.width()/6;
        int h = size.height()/6;
        addRandomInnerCoordsToCoordSlot(w, h, coordSlot);
        return buildCentroidSet(w, h, coordSlot);
    }

    private static void addRandomInnerCoordsToCoordSlot(int w, int h, CoordSlot coordSlot){
        RandomCoords randomCoords = new RandomCoords(RandomSource.asRandom());
        Coord primary = randomCoords.generateRandomCoordWithinClip(new Rect(w, h, w, 4*h));
        Coord average = randomCoords.generateRandomCoordWithinClip(new Rect(2*w, h, 2*w, 4*h));
        Coord secondary = randomCoords.generateRandomCoordWithinClip(new Rect(4*w, h, 2*w, 4*h));
        coordSlot.setPrimary(primary);
        coordSlot.setAverage(average);
        coordSlot.setSecondary(secondary);
    }

    private static Set<Coord> buildCentroidSet(int w, int h, CoordSlot filledCoordSlot){
        Set<Coord> coords = new HashSet<>();
        coords.add(filledCoordSlot.getPrimary());
        coords.add(filledCoordSlot.getSecondary());
        coords.add(filledCoordSlot.getAverage());
        addHorizontalBarsOfRandomCentroidsToSet(w, h, coords);
        addVerticalBarsOfRandomCentroidsToSet(w, h, coords);
        return coords;
    }

    private static void addHorizontalBarsOfRandomCentroidsToSet(int w, int h, Set<Coord> coords){
        RandomCoords randomCoords = new RandomCoords(RandomSource.asRandom());
        for(int i = 0; i<6; i++){
            coords.add(randomCoords.generateRandomCoordWithinClip(new Rect(i*w, 0, w, h)));
            coords.add(randomCoords.generateRandomCoordWithinClip(new Rect(i*w, 5*h, w, h)));
        }
    }

    private static void addVerticalBarsOfRandomCentroidsToSet(int w, int h, Set<Coord> coords){
        RandomCoords randomCoords = new RandomCoords(RandomSource.asRandom());
        for(int i = 1; i<=4; i++){
            coords.add(randomCoords.generateRandomCoordWithinClip(new Rect(0, i*h, w, h)));
            coords.add(randomCoords.generateRandomCoordWithinClip(new Rect(5*w, i*h, w, h)));
        }
    }

    private static void addColorCentroidsToSkeleton(BrushColorDisplaySkeleton skeleton, ChunkingGrid<PaintCentroidData> grid, CoordSlot coordSlot){
        PaintCentroidData primary = grid.getCentroidDataByIndex(grid.asIndex(coordSlot.getPrimary()));
        PaintCentroidData secondary = grid.getCentroidDataByIndex(grid.asIndex(coordSlot.getSecondary()));
        PaintCentroidData average = grid.getCentroidDataByIndex(grid.asIndex(coordSlot.getAverage()));
        skeleton.setPrimaryColorCentroid(primary);
        skeleton.setSecondaryColorCentroid(secondary);
        skeleton.setAverageColorCentroid(average);
    }

    private static void makeOuterCentroidsTransparent(ChunkingGrid<PaintCentroidData> grid, CoordSlot innerCentroids){
        for(Short centroidID : grid.getAllCentroidIDs()){
            if(!innerCentroids.contains(grid.getCentroidCoord(centroidID))){
                setCentroidTransparent(grid.getCentroidDataByID(centroidID));
            }
        }
    }

    private static void setCentroidTransparent(PaintCentroidData centroid){
        if(centroid == null){
            throw new IllegalStateException("Not enough centroids");
        }
        centroid.setColor(new Color(0, 0, 0, 0));
    }


    private static class CoordSlot{

        private Coord primary;
        private Coord secondary;
        private Coord average;


        public Coord getPrimary(){
            if(primary == null){
                throw new IllegalStateException("primary is not set");
            }
            return primary;
        }

        public void setPrimary(Coord primary){
            if(this.primary != null){
                throw new IllegalStateException("primary is already set");
            }
            this.primary = primary;
        }

        public Coord getSecondary(){
            if(secondary == null){
                throw new IllegalStateException("secondary is not set");
            }
            return secondary;
        }

        public void setSecondary(Coord secondary){
            if(this.secondary != null){
                throw new IllegalStateException("secondary is already set");
            }
            this.secondary = secondary;
        }

        public Coord getAverage(){
            if(average == null){
                throw new IllegalStateException("average is not set");
            }
            return average;
        }

        public void setAverage(Coord average){
            if(this.average != null){
                throw new IllegalStateException("average is already set");
            }
            this.average = average;
        }

        boolean contains(Coord coord){
            return primary.equals(coord) || average.equals(coord) || secondary.equals(coord);
        }

    }

}
