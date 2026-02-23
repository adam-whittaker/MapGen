package com.mason.mapgen.paint.components;

import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PaintedImageComponent extends UIComponent{


    private final Size size;
    private final BufferedImage image;
    private final int[] pixels;


    public PaintedImageComponent(ChunkingGrid<PaintCentroidData> grid){
        super(new HitboxRect(new Coord(0, 0), grid.size()));
        this.size = grid.size();
        image = new BufferedImage(size.width(), size.height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        updateWholeImage(grid);
    }

    private static int[] getPixelMask(BufferedImage image){
        DataBufferInt dataBufferInt = ((DataBufferInt) image.getRaster().getDataBuffer());
        return dataBufferInt.getData();
    }

    public final void updateWholeImage(ChunkingGrid<PaintCentroidData> grid){
        PaintCentroidData data;
        for(int i = 0; i < pixels.length; i++){
            data = grid.getCentroidDataByIndex(i);
            pixels[i] = data.getColor().getRGB();
        }
    }


    @Override
    public void render(Graphics2D g){
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void tick(){}

    public Size getSize(){
        return size;
    }

}
