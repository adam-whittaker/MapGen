package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.states.intState.IntQuery;
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
    private final IntQuery currentLayerQuery;


    public LayeredImageComponent(
            PaintGrid grid,
            CanvasViewSlot canvasViewSlot
    ){
        super(new BasicHitboxRect(new Coord(0, 0), grid.size()));
        layers = new LayerManager(grid);
        this.currentLayerQuery = canvasViewSlot::getCurrentLayer;
        canvasViewSlot.setLayerCountQuery(layers::numLayers);
        canvasViewSlot.setLayerManagerQuery(() -> layers);
        canvasViewSlot.setPaintImageQuery(this::createCompositeImage);
        canvasViewSlot.setCreateLayerUpdate(layers::createLayer);
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

    public void updateImageInChunk(PaintGrid grid, Short centroidID){
        Layer layer = layers.getLayer(currentLayerQuery.getState());
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
