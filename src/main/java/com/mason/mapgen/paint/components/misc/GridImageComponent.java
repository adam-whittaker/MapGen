package com.mason.mapgen.paint.components.misc;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mason.mapgen.core.Utils.getPixelMask;

public class GridImageComponent extends AbstractUIComponent{


    private final BufferedImage image;
    private final int[] pixels;


    public GridImageComponent(Coord topLeft, ChunkingGrid<PaintCentroidData> grid){
        super(new BasicHitboxRect(topLeft, grid.size()));
        image = new BufferedImage(getSize().width(), getSize().height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        updateWholeImage(grid);
    }


    public final void updateWholeImage(ChunkingGrid<PaintCentroidData> grid){
        updateImageInClip(grid, new BasicHitboxRect(new Coord(0, 0), grid.size()));
    }

    public final void updateImageInClip(ChunkingGrid<PaintCentroidData> grid, RectQuery clip){
        updateImageOverIndices(grid, grid.indicesInClip(clip));
    }

    public final void updateImageOverIndices(ChunkingGrid<PaintCentroidData> grid, Iterable<Integer> indices){
        PaintCentroidData data;
        for(Integer i : indices){
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
