package com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.history;

import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;

public interface LayerAction{


    void apply(LayerManager layers);

    void undo(LayerManager layers);

}
