package com.mason.mapgen.core;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;

public final class DebugUtils{


    private DebugUtils(){}


    public static int colorGradient(Integer pointIdx, ChunkingGrid<?> grid){
        return colorGradientFromSize(pointIdx, grid.size());
    }

    public static int colorGradientFromSize(Integer pointIdx, Size size){
        Coord c = new Coord(pointIdx % size.width(), pointIdx / size.height());
        if(nearEdge(c, size)){
            return Color.WHITE.getRGB();
        }
        double x = (double)c.x()/size.width();
        double y = (double)c.y()/size.height();
        return colorGradientByProportion(x, y);
    }

    private static boolean nearEdge(Coord c, Size size){
        if(c.x() < 12 || c.y() < 12){
            return true;
        }
        return c.x() > size.width() - 12 || c.y() > size.height() - 12;
    }

    private static int colorGradientByProportion(double x, double y){
        return new Color((int)(250*x), 125, (int)(250*y)).getRGB();
    }

}
