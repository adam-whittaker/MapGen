package com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController;


import com.mason.libstruct.setters.ConsumerSetter;
import com.mason.libstruct.setters.QuerySetter;
import com.mason.libstruct.setters.UpdateSetter;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CanvasViewSlot{


    private final QuerySetter.QuerySlot<PaintGridSaveData> paintGridQuerySlot = new QuerySetter.QuerySlot<>();
    private final QuerySetter.QuerySlot<BufferedImage> paintImageQuerySlot = new QuerySetter.QuerySlot<>();
    private final ConsumerSetter.ConsumerSlot<PaintGridSaveData> paintGridLoaderSlot = new ConsumerSetter.ConsumerSlot<>();
    private final QuerySetter.QuerySlot<Integer> layerCountQuerySlot = new QuerySetter.QuerySlot<>();
    private final QuerySetter.QuerySlot<Integer> currentLayerQuerySlot = new QuerySetter.QuerySlot<>();
    private final QuerySetter.QuerySlot<LayerManager> layerManagerQuerySlot = new QuerySetter.QuerySlot<>();
    private final UpdateSetter.UpdateSlot currentLayerResetterSlot = new UpdateSetter.UpdateSlot();
    private final UpdateSetter.UpdateSlot createLayerUpdateSlot = new UpdateSetter.UpdateSlot();


    CanvasViewSlot(){}


    public void setSaveDataQuery(Supplier<PaintGridSaveData> query){
        paintGridQuerySlot.setQuery(query);
    }

    public void setPaintImageQuery(Supplier<BufferedImage> query){
        paintImageQuerySlot.setQuery(query);
    }

    public void setSaveDataLoader(Consumer<PaintGridSaveData> loader){
        paintGridLoaderSlot.setConsumer(loader);
    }

    public void setLayerCountQuery(Supplier<Integer> query){
        layerCountQuerySlot.setQuery(query);
    }

    public void setCurrentLayerQuery(Supplier<Integer> query){
        currentLayerQuerySlot.setQuery(query);
    }

    public void setCurrentLayerResetter(Runnable update){
        currentLayerResetterSlot.setUpdate(update);
    }

    public void setLayerManagerQuery(Supplier<LayerManager> query){
        layerManagerQuerySlot.setQuery(query);
    }

    public void setCreateLayerUpdate(Runnable update){
        createLayerUpdateSlot.setUpdate(update);
    }


    public PaintGridSaveData getSaveData(){
        return paintGridQuerySlot.get();
    }

    public BufferedImage getPaintImage(){
        return paintImageQuerySlot.get();
    }

    public void loadSaveData(PaintGridSaveData saveData){
        paintGridLoaderSlot.accept(saveData);
    }

    public int getLayerCount(){
        return layerCountQuerySlot.get();
    }

    public int getCurrentLayer(){
        return currentLayerQuerySlot.get();
    }

    public LayerManager getLayerManager(){
        return layerManagerQuerySlot.get();
    }

    public void resetCurrentLayerNum(){
        currentLayerResetterSlot.run();
    }

    public void createLayer(){
        createLayerUpdateSlot.run();
    }

}
