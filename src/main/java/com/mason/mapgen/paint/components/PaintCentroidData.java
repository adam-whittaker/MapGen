package com.mason.mapgen.paint.components;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.procgen.algorithms.chunking.components.BasicCentroidData;

import java.awt.*;

import static com.mason.mapgen.core.Utils.RANDOM;

public class PaintCentroidData extends BasicCentroidData<PaintCentroidData>{


    private Color color;


    public PaintCentroidData(Coord coord){
        super(coord);
        color = randomUnsetColor();
    }

    private Color randomUnsetColor(){
        int brightness = 75 + RANDOM.nextInt(100);
        return new Color(brightness, brightness, brightness);
    }


    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

}
