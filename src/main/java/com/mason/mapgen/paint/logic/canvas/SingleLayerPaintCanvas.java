package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.misc.GridImageComponent;
import com.mason.mapgen.paint.components.misc.PaintGrid;

public class SingleLayerPaintCanvas extends AbstractPaintCanvas{


    private final GridImageComponent image;


    public SingleLayerPaintCanvas(Coord topLeft, PaintGrid grid){
        super(grid, () -> 0);
        this.image = GridImageComponent.buildForSingleLayeredCanvas(topLeft, grid);
    }


    @Override
    protected void updateImageInChunkOnLayer(PaintGrid grid, Short centroidID, int layerID){
        image.updateImageInChunk(grid, centroidID);
    }

    @Override
    public boolean withinBounds(Coord c){
        return image.withinBounds(c);
    }

    @Override
    public Coord getCoord(){
        return image.getCoord();
    }

    @Override
    public Size getSize(){
        return image.getSize();
    }

    @Override
    public UIComponent getImageComponent(){
        return image;
    }

}
