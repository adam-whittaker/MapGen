package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.setters.QuerySetter;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.CanvasViewSlot;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;

import java.util.function.Consumer;

public class PersistentLayeredPaintCanvas extends AbstractPaintCanvas{


    private final LayeredImageComponent layers;


    public PersistentLayeredPaintCanvas(
            PaintGrid grid,
            CanvasViewSlot canvasViewSlot){
        super(grid, canvasViewSlot::getCurrentLayer);
        QuerySetter.QuerySlot<LayerManager> layerManagerQuery = new QuerySetter.QuerySlot<>();
        this.layers = new LayeredImageComponent(grid, canvasViewSlot);
        Consumer<PaintGridSaveData> saveDataLoader = buildSaveDataLoader(canvasViewSlot);
        canvasViewSlot.setSaveDataLoader(saveDataLoader);
        canvasViewSlot.setSaveDataQuery(() -> new PaintGridSaveData(getGrid(), layerManagerQuery.get()));
    }

    private Consumer<PaintGridSaveData> buildSaveDataLoader(CanvasViewSlot canvasViewSlot){
        return (saveData) -> {
            canvasViewSlot.resetCurrentLayerNum();
            setGrid(saveData.grid());
            layers.loadInNewSaveData(saveData);
        };
    }


    @Override
    protected void updateImageInChunk(PaintGrid grid, Short centroidID){
        layers.updateImageInChunk(grid, centroidID);
    }

    @Override
    public boolean withinBounds(Coord c){
        return layers.withinBounds(c);
    }

    @Override
    public Coord getCoord(){
        return layers.getCoord();
    }

    @Override
    public Size getSize(){
        return layers.getSize();
    }

    @Override
    public UIComponent getImageComponent(){
        return layers;
    }

}
