package com.mason.mapgen.paint.components.misc;

import com.mason.libstruct.geo.Coord;
import com.mason.libvoronoi.algorithms.components.CentroidData;
import com.mason.mapgen.core.random.RandomSource;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

public class PaintCentroidData extends CentroidData{


    private final LayeredColor color;


    public PaintCentroidData(Coord coord){
        super(coord);
        color = new LayeredColor(randomUnsetColor());
    }

    protected PaintCentroidData(Coord coord, Set<Short> neighbours, LayeredColor color){
        super(coord, neighbours);
        this.color = color;
    }

    private Color randomUnsetColor(){
        int brightness = 75 + RandomSource.nextInt(100);
        return new Color(brightness, brightness, brightness);
    }


    public Color getColor(int idx){
        return color.getColor(idx);
    }

    public void setColor(int idx, Color newColor){
        color.setColor(idx, newColor);
    }

    public void paintColor(int idx, Color foreground){
        color.paintColor(idx, foreground);
    }


    public void writeToDataStream(DataOutputStream out) throws IOException{
        color.writeToDataStream(out);
        super.writeToDataStream(out);
    }

    public static PaintCentroidData readFromDataStream(DataInputStream in) throws IOException{
        LayeredColor color = LayeredColor.readFromDataStream(in);
        CentroidData rawData = CentroidData.readFromDataStream(in);
        return new PaintCentroidData(rawData.getCoord(), (Set<Short>)rawData.neighbours(), color);
    }

}
