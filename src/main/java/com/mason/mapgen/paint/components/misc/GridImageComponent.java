package com.mason.mapgen.paint.components.misc;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libvoronoi.algorithms.components.CentroidData;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mason.mapgen.core.Utils.getPixelMask;

public class GridImageComponent extends AbstractUIComponent{


    private final int layerID;
    private BufferedImage image;
    private int[] pixels;


    protected GridImageComponent(Coord topLeft, PaintGrid grid, int layerID){
        super(new BasicHitboxRect(topLeft, grid.size()));
        this.layerID = layerID;
        loadInNewPaintGrid(grid);
    }

    public static GridImageComponent buildForSingleLayeredCanvas(Coord topLeft, PaintGrid grid){
        return new GridImageComponent(topLeft, grid, 0);
    }


    public void loadInNewPaintGrid(PaintGrid newGrid){
        //Unfinished: Make UIComponent size change based on new grid. Ripple to pane.
        Size size = newGrid.size();
        image = new BufferedImage(size.width(), size.height(), BufferedImage.TYPE_INT_ARGB);
        pixels = getPixelMask(image);
        updateWholeImage(newGrid);
    }

    protected BufferedImage getImage(){
        return image;
    }

    protected int getLayerID(){
        return layerID;
    }


    public final void updateWholeImage(PaintGrid grid){
        updateImageOverIndices(grid, grid.pointIndices());
    }

    public final void updateImageInChunk(PaintGrid grid, short centroidID){
        CentroidData data = grid.getCentroidDataByID(centroidID);
        updateImageOverIndices(grid, data.pointIndices());
    }

    protected final void updateImageOverIndices(PaintGrid grid, Iterable<Integer> indices){
        PaintCentroidData data;
        for(Integer i : indices){
            data = grid.getCentroidDataByIndex(i);
            pixels[i] = data.getColor(layerID).getRGB();
        }
    }


    @Override
    public void render(Graphics2D g){
        g.drawImage(image, x(), y(), null);
    }

}
