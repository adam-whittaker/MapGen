package com.mason.mapgen.paint.components;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.RectQuery;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GridImageComponent extends AbstractUIComponent{


    private final BufferedImage image;
    private final int[] pixels;


    public GridImageComponent(ChunkingGrid<PaintCentroidData> grid){
        super(new HitboxRect(new Coord(0, 0), grid.size()));
        image = new BufferedImage(getSize().width(), getSize().height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        updateWholeImage(grid);
    }

    private static int[] getPixelMask(BufferedImage image){
        DataBufferInt dataBufferInt = ((DataBufferInt) image.getRaster().getDataBuffer());
        return dataBufferInt.getData();
    }

    public final void updateWholeImage(ChunkingGrid<PaintCentroidData> grid){
        updateImageInClip(grid, new HitboxRect(new Coord(0, 0), grid.size()));
    }

    public final void updateImageInClip(ChunkingGrid<PaintCentroidData> grid, RectQuery clip){
        PaintCentroidData data;
        for(Integer i : grid.indicesInClip(clip)){
            data = grid.getCentroidDataByIndex(i);
            pixels[i] = data.getColor().getRGB();
        }
    }


    @Override
    public void render(Graphics2D g){
        g.drawImage(image, getCoord().x(), getCoord().y(), null);
    }

    @Override
    public void tick(){}

}
