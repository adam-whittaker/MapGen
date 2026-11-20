package com.mason.mapgen.paint.components.misc;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class LayeredColor{


    private final Map<Integer, Color> colors;
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);


    protected LayeredColor(Color bgc){
        colors = new HashMap<>();
        colors.put(0, bgc);
    }

    private LayeredColor(Map<Integer, Color> colors){
        this.colors = colors;
    }


    protected void setColor(int id, Color color){
        colors.put(id, color);
    }

    protected Color getColor(int id){
        if(!colors.containsKey(id)){
            colors.put(id, TRANSPARENT);
            return TRANSPARENT;
        }
        return colors.get(id);
    }

    protected void paintColor(int id, Color fg){
        Color bg = getColor(id);
        Color newColor = composite(bg, fg);
        setColor(id, newColor);
    }

    private static Color composite(Color bg, Color fg){
        double foregroundAlpha = fg.getAlpha() / 255D;
        double alpha = bg.getAlpha() / 255D;
        double newAlpha = foregroundAlpha + alpha * (1 - foregroundAlpha);

        if(newAlpha == 0){
            return TRANSPARENT;
        }

        int r = (int) ((fg.getRed() * foregroundAlpha
                + bg.getRed() * alpha * (1 - foregroundAlpha)) / newAlpha);

        int g = (int) ((fg.getGreen() * foregroundAlpha
                + bg.getGreen() * alpha * (1 - foregroundAlpha)) / newAlpha);

        int b = (int) ((fg.getBlue() * foregroundAlpha
                + bg.getBlue() * alpha * (1 - foregroundAlpha)) / newAlpha);

        return new Color(r, g, b, (int) (newAlpha * 255));
    }


    public void writeToDataStream(DataOutputStream out) throws IOException{
        out.writeInt(colors.size());
        for(Map.Entry<Integer, Color> entry : colors.entrySet()){
            out.writeInt(entry.getKey());
            out.writeInt(entry.getValue().getRGB());
        }
    }

    public static LayeredColor readFromDataStream(DataInputStream in) throws IOException{
        int size = in.readInt();
        Map<Integer, Color> colors = new HashMap<>();
        for(int n=0; n<size; n++){
            int id = in.readInt();
            Color color = new Color(in.readInt(), true);
            colors.put(id, color);
        }
        return new LayeredColor(colors);
    }

}
