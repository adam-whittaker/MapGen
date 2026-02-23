package com.mason.mapgen.gui.components;

import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.core.DebugUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class StubComponent extends UIComponent implements BoundedMouseInputListener{


    private final Size size;
    private final BufferedImage image;
    private final int[] pixels;


    public StubComponent(Coord topLeft, Size size){
        super(new HitboxRect(topLeft, size));
        this.size = size;
        image = new BufferedImage(size.width(), size.height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        generateImage();
    }

    private static int[] getPixelMask(BufferedImage image){
        DataBufferInt dataBufferInt = ((DataBufferInt) image.getRaster().getDataBuffer());
        return dataBufferInt.getData();
    }

    private void generateImage(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = DebugUtils.colorGradientFromSize(i, size);
        }
    }


    @Override
    public void render(Graphics2D g){
        g.drawImage(image, getCoord().x(), getCoord().y(), null);
    }

    @Override
    public void tick(){

    }

    @Override
    public void onMouseClicked(MouseInputEvent e){
        System.out.println("Mouse click on stub: " + e.getCoord());
    }

    public Size getSize(){
        return size;
    }

}
