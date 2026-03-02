package com.mason.mapgen.paint.components;

import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.procgen.algorithms.chunking.components.BasicCentroidData;

import java.awt.*;

import static com.mason.mapgen.core.Utils.RANDOM;
import static java.lang.Math.round;

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

    public void setColor(Color newColor){
        this.color = newColor;
    }

    public void paintColor(Color newColor){
        float alpha = newColor.getAlpha()/255F;
        int red = round(this.color.getRed()*(1-alpha) + newColor.getRed()*alpha);
        int green = round(this.color.getGreen()*(1-alpha) + newColor.getGreen()*alpha);
        int blue = round(this.color.getBlue()*(1-alpha) + newColor.getBlue()*alpha);
        this.color = new Color(red, green, blue);
    }

    public boolean colorMatches(Color color){
        return color.equals(this.color);
    }

}
