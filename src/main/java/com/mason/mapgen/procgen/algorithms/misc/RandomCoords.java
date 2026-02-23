package com.mason.mapgen.procgen.algorithms.misc;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.HashSet;
import java.util.Set;

import static com.mason.mapgen.core.Utils.RANDOM;

public class RandomCoords{


    public static Set<Coord> generateRandomDistinctCoords(Size bounds, int num){
        Set<Coord> coords = new HashSet<>();
        while(coords.size() < num){
            coords.add(generateRandomCoord(bounds));
        }
        return coords;
    }

    public static Coord generateRandomCoord(Size bounds){
        int x = RANDOM.nextInt(bounds.width());
        int y = RANDOM.nextInt(bounds.height());
        return new Coord(x, y);
    }

}
