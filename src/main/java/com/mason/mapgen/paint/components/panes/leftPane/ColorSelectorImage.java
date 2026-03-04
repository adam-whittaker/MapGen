package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mason.mapgen.core.Utils.getPixelMask;

public class ColorSelectorImage{


    private final BufferedImage image;
    private final int[] pixels;
    private final Size size;


    ColorSelectorImage(Size size){
        this.size = size;
        image = new BufferedImage(size.width(), size.height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        updateImage(0.5f);
    }

    void updateImage(float brightness){
        int index = 0;
        for(int y=0; y<size.height(); y++){
            for(int x=0; x<size.width(); x++){
                pixels[index] = getRGBFromHS((float)x/size.width(), (float)y/size.height(), brightness);
                index++;
            }
        }
    }

    private int getRGBFromHS(float x, float y, float brightness){
        return Color.HSBtoRGB(x, 1-y, brightness);
    }


    void render(Graphics2D g, Coord topLeft){
        g.drawImage(image, topLeft.x(), topLeft.y(), null);
    }


    float[] getHueAndSaturationAtPosition(int x, int y){
        int rgb = pixels[x + y*size.width()];
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return Color.RGBtoHSB(r, g, b, null);
    }

}
