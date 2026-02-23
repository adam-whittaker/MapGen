package com.mason.mapgen.procgen.algorithms.chunking;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.procgen.algorithms.chunking.components.CentroidData;

public interface CentroidDataInitializer<T extends CentroidData<T>>{

    T initializeCentroid(Coord coord);

}
