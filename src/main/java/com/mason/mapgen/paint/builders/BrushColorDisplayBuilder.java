package com.mason.mapgen.paint.builders;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Rect;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.skeletons.BrushColorDisplaySkeleton;
import com.mason.mapgen.procgen.algorithms.chunking.AnnexQueries;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;
import com.mason.mapgen.procgen.algorithms.chunking.voronoi.VoronoiChunker;
import com.mason.mapgen.procgen.algorithms.chunking.voronoi.VoronoiChunkerBuilder;
import com.mason.mapgen.procgen.algorithms.chunking.voronoi.VoronoiChunkerSkeleton;

import java.awt.*;
import java.util.*;

import static com.mason.mapgen.procgen.algorithms.misc.RandomCoords.generateRandomCoordWithinClip;

public class BrushColorDisplayBuilder{


    public static BrushColorDisplaySkeleton buildSkeleton(Size size){
        BrushColorDisplaySkeleton skeleton = new BrushColorDisplaySkeleton();
        CoordSlot coordSlot = new CoordSlot();
        ChunkingGrid<PaintCentroidData> grid = buildGrid(size, coordSlot);
        makeOuterCentroidsTransparent(grid, coordSlot);
        skeleton.setGrid(grid);
        addColorCentroidsToSkeleton(skeleton, grid, coordSlot);
        return skeleton;
    }

    private static ChunkingGrid<PaintCentroidData> buildGrid(Size size, CoordSlot coordSlot){
        VoronoiChunkerSkeleton<PaintCentroidData> voronoiChunkerSkeleton =
                VoronoiChunkerBuilder.buildSkeleton(size, -1, 0, PaintCentroidData::new, AnnexQueries::randomQuery);
        voronoiChunkerSkeleton.preventCentroidGraph();
        voronoiChunkerSkeleton.setRandomCoordGenerator((boundingSize, numCoords) -> generateCoords(boundingSize, coordSlot));
        VoronoiChunker<PaintCentroidData> chunker = VoronoiChunker.buildFromSkeleton(voronoiChunkerSkeleton);
        chunker.createChunks();
        ChunkingGrid<PaintCentroidData> grid = chunker.getGrid();
        grid.updateMaxDistToCentroid();
        return grid;
    }

    private static Set<Coord> generateCoords(Size size, CoordSlot coordSlot){
        int w = size.width()/6;
        int h = size.height()/6;
        addRandomInnerCoordsToCoordSlot(w, h, coordSlot);
        return buildCentroidSet(w, h, coordSlot);
    }

    private static void addRandomInnerCoordsToCoordSlot(int w, int h, CoordSlot coordSlot){
        Coord primary = generateRandomCoordWithinClip(new Rect(w, h, w, 4*h));
        Coord average = generateRandomCoordWithinClip(new Rect(2*w, h, 2*w, 4*h));
        Coord secondary = generateRandomCoordWithinClip(new Rect(4*w, h, 2*w, 4*h));
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
        for(int i = 0; i<6; i++){
            coords.add(generateRandomCoordWithinClip(new Rect(i*w, 0, w, h)));
            coords.add(generateRandomCoordWithinClip(new Rect(i*w, 5*h, w, h)));
        }
    }

    private static void addVerticalBarsOfRandomCentroidsToSet(int w, int h, Set<Coord> coords){
        for(int i = 1; i<=4; i++){
            coords.add(generateRandomCoordWithinClip(new Rect(0, i*h, w, h)));
            coords.add(generateRandomCoordWithinClip(new Rect(5*w, i*h, w, h)));
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
