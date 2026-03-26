package com.mason.mapgen.procgen.algorithms.misc;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.core.random.RandomSource;

import java.util.HashSet;
import java.util.Set;

public class RandomCoords{


    public static Set<Coord> generateRandomDistinctCoords(Size bounds, int num){
        Set<Coord> coords = new HashSet<>();
        while(coords.size() < num){
            coords.add(generateRandomCoord(bounds));
        }
        return coords;
    }

    public static Coord generateRandomCoord(Size bounds){
        int x = RandomSource.nextInt(bounds.width());
        int y = RandomSource.nextInt(bounds.height());
        return new Coord(x, y);
    }

    public static Coord generateRandomCoordWithinClip(RectQuery clip){
        int x = clip.x() + RandomSource.nextInt(clip.width());
        int y = clip.y() + RandomSource.nextInt(clip.height());
        return new Coord(x, y);
    }

}
