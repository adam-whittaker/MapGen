package com.mason.mapgen.procgen.worldSetup;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.grids.Grid;
import com.mason.mapgen.structures.grids.MutableGrid;
import com.mason.mapgen.world.WorldPoint;
import com.mason.mapgen.world.WorldSkeleton;

import java.util.Map;

public class WorldGenScaffold implements WorldSkeleton{


    private final MutableGrid<WorldPointScaffold> map;
    private Map<Coord, WorldPointScaffold> scaffoldCentroidMap;


    public WorldGenScaffold(Size size){
        map = buildInitialMap(size);
    }

    private MutableGrid<WorldPointScaffold> buildInitialMap(Size size){
        WorldPointScaffold[][] pointGrid = new WorldPointScaffold[size.height()][size.width()];
        return MutableGrid.buildGrid(pointGrid, WorldPointScaffold::new);
    }


    @Override
    public Grid<WorldPoint> getWorldMapGrid(){
        Size size = map.getSize();
        WorldPoint[][] worldMap = new WorldPoint[size.height()][size.width()];
        for(int y=0; y<size.height(); y++){
            for(int x=0; x<size.width(); x++){
                worldMap[y][x] = new WorldPoint(map.getValue(new Coord(x, y)));
            }
        }
        return new Grid<>(worldMap);
    }


    public void setCentroidMap(Map<Coord, WorldPointScaffold> centroidMap){
        scaffoldCentroidMap = centroidMap;
    }

    public Grid<WorldPointScaffold> getScaffoldMap(){
        return map;
    }

    public WorldPointScaffold getPoint(Coord coord){
        return map.getValue(coord);
    }

    public Size getSize(){
        return map.getSize();
    }

}
