package com.mason.mapgen.paint.components.misc;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.mapgen.paint.components.panes.topPane.resources.ImageQuery;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mason.mapgen.core.Utils.getPixelMask;

public class GridImageComponent extends AbstractUIComponent{


    private BufferedImage image;
    private int[] pixels;


    public GridImageComponent(Coord topLeft, ChunkingGrid<PaintCentroidData> grid){
        super(new BasicHitboxRect(topLeft, grid.size()));
        loadInNewPaintGrid(grid);
    }

    public void loadInNewPaintGrid(ChunkingGrid<PaintCentroidData> newGrid){
        Size size = newGrid.size();
        image = new BufferedImage(size.width(), size.height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        updateWholeImage(newGrid);
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


    public ImageQuery getImageQueryForExporting(){
        return () -> image;
    }

}
