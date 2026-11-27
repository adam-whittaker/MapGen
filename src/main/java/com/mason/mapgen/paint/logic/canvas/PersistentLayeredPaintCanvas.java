package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.CanvasViewSlot;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;

import java.util.function.Consumer;

public class PersistentLayeredPaintCanvas extends AbstractPaintCanvas{


    private final LayeredImageComponent layers;


    public PersistentLayeredPaintCanvas(
            PaintGrid grid,
            CanvasViewSlot canvasViewSlot){
        super(grid, canvasViewSlot::getCurrentLayer);
        this.layers = new LayeredImageComponent(grid, canvasViewSlot);
        Consumer<PaintGridSaveData> saveDataLoader = buildSaveDataLoader(canvasViewSlot);
        canvasViewSlot.setSaveDataLoader(saveDataLoader);
        canvasViewSlot.setSaveDataQuery(() -> new PaintGridSaveData(getGrid(), canvasViewSlot.getLayerManager()));
        canvasViewSlot.setGridSizeQuery(() -> getGrid().size());
    }

    private Consumer<PaintGridSaveData> buildSaveDataLoader(CanvasViewSlot canvasViewSlot){
        return (saveData) -> {
            canvasViewSlot.resetCurrentLayerNum();
            canvasViewSlot.clearHistory();
            setGrid(saveData.grid());
            layers.loadInNewSaveData(saveData);
        };
    }


    @Override
    protected void updateImageInChunkOnLayer(PaintGrid grid, Short centroidID, int layerID){
        layers.updateImageInChunkOnLayer(grid, centroidID, layerID);
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
