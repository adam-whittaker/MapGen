package com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.history;

import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.Layer;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;

public class RenameLayer implements LayerAction{


    private final int layerID;
    private final String newName;
    private String oldName = null;


    public RenameLayer(int layerID, String newName){
        this.layerID = layerID;
        this.newName = newName;
    }


    @Override
    public void apply(LayerManager layers){
        Layer layer = layers.getLayer(layerID);
        oldName = layer.getName();
        layer.setName(newName);
    }

    @Override
    public void undo(LayerManager layers){
        if(oldName == null){
            throw new IllegalStateException("Cannot undo action which has not happened");
        }
        Layer layer = layers.getLayer(layerID);
        layer.setName(oldName);
    }

}
