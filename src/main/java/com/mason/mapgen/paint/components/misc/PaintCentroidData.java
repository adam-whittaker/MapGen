package com.mason.mapgen.paint.components.misc;

import com.mason.libstruct.geo.Coord;
import com.mason.libvoronoi.algorithms.components.CentroidData;
import com.mason.mapgen.core.random.RandomSource;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import static java.lang.Math.round;

public class PaintCentroidData extends CentroidData{


    private Color color;


    public PaintCentroidData(Coord coord){
        super(coord);
        color = randomUnsetColor();
    }

    protected PaintCentroidData(Coord coord, Set<Short> neighbours, Color color){
        super(coord, neighbours);
        this.color = color;
    }

    private Color randomUnsetColor(){
        int brightness = 75 + RandomSource.nextInt(100);
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


    public void writeToDataStream(DataOutputStream out) throws IOException{
        out.writeInt(color.getRGB());
        super.writeToDataStream(out);
    }

    public static PaintCentroidData readFromDataStream(DataInputStream in) throws IOException{
        int rgb = in.readInt();
        Color color = new Color(rgb);
        CentroidData rawData = CentroidData.readFromDataStream(in);
        return new PaintCentroidData(rawData.getCoord(), (Set<Short>)rawData.neighbours(), color);
    }

}
