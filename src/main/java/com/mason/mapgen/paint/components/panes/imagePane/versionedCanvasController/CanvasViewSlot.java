package com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController;


import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.skeletons.ConsumerSetter;
import com.mason.mapgen.paint.skeletons.QuerySetter;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CanvasViewSlot{


    private final QuerySetter.QuerySlot<PaintGrid> paintGridQuerySlot = new QuerySetter.QuerySlot<>();
    private final QuerySetter.QuerySlot<BufferedImage> paintImageQuerySlot = new QuerySetter.QuerySlot<>();
    private final ConsumerSetter.ConsumerSlot<PaintGrid> paintGridLoaderSlot = new ConsumerSetter.ConsumerSlot<>();


    CanvasViewSlot(){}


    public void setPaintGridQuery(Supplier<PaintGrid> query){
        paintGridQuerySlot.setQuery(query);
    }

    public void setPaintImageQuery(Supplier<BufferedImage> query){
        paintImageQuerySlot.setQuery(query);
    }

    public void setPaintGridLoader(Consumer<PaintGrid> loader){
        paintGridLoaderSlot.setConsumer(loader);
    }


    public PaintGrid getPaintGrid(){
        return paintGridQuerySlot.get();
    }

    public BufferedImage getPaintImage(){
        return paintImageQuerySlot.get();
    }

    public void loadPaintGrid(PaintGrid grid){
        paintGridLoaderSlot.accept(grid);
    }

}
