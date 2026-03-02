package com.mason.mapgen.procgen.algorithms.chunking.voronoi;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.Set;

public interface RandomCoordGenerator{


    Set<Coord> generateRandomDistinctCoords(Size bounds, int numCoords);

}
