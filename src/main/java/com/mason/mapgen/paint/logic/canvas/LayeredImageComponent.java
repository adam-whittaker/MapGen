package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.DynamicHitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.CanvasViewSlot;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.Layer;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.history.LayerAction;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LayeredImageComponent extends AbstractUIComponent{


    private LayerManager layers;


    public LayeredImageComponent(
            PaintGrid grid,
            CanvasViewSlot canvasViewSlot
    ){
        super(DynamicHitboxRect.buildWithStaticCoord(
                new Coord(0, 0),
                canvasViewSlot::getGridSize
        ));
        layers = new LayerManager(grid);
        canvasViewSlot.setLayerCountQuery(() -> getLayerManager().numLayers());
        canvasViewSlot.setLayerManagerQuery(this::getLayerManager);
        canvasViewSlot.setPaintImageQuery(this::createCompositeImage);
        canvasViewSlot.setCreateLayerUpdate(() -> getLayerManager().createLayer());
    }

    //So we don't reference the mutable object directly.
    private LayerManager getLayerManager(){
        return layers;
    }

    private BufferedImage createCompositeImage(){
        BufferedImage img = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        try{
            render(g);
        }finally{
            g.dispose();
        }
        return img;
    }


    @Override
    public void render(Graphics2D g){
        for(Layer layer : layers){
            layer.render(g);
        }
    }

    public void updateImageInChunkOnLayer(PaintGrid grid, Short centroidID, int layerID){
        Layer layer = layers.getLayer(layerID);
        layer.updateImageInChunk(grid, centroidID);
    }

    private void applyAction(LayerAction action){
        action.apply(layers);
    }

    private void undoAction(LayerAction action){
        action.undo(layers);
    }

    public void loadInNewSaveData(PaintGridSaveData saveData){
        layers = saveData.layers();
    }

}
