package com.mason.mapgen.paint.skeletons;

import com.mason.mapgen.paint.logic.PaintCanvas;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;

public class PaintManagerSkeleton{


    private PaintCanvas mainCanvas;
    private PaintCanvas palette;
    private PaintKeyProcessor paintKeyProcessor;


    public PaintManagerSkeleton(){}


    public PaintCanvas getMainCanvas(){
        if(mainCanvas == null){
            throw new IllegalStateException("mainCanvas is not set");
        }
        return mainCanvas;
    }

    public void setMainCanvas(PaintCanvas mainCanvas){
        this.mainCanvas = mainCanvas;
    }

    public PaintCanvas getPalette(){
        if(palette == null){
            throw new IllegalStateException("palette is not set");
        }
        return palette;
    }

    public void setPalette(PaintCanvas palette){
        this.palette = palette;
    }

    public PaintKeyProcessor getPaintKeyProcessor(){
        if(paintKeyProcessor == null){
            throw new IllegalStateException("paintKeyProcessor is not set");
        }
        return paintKeyProcessor;
    }

    public void setPaintKeyProcessor(PaintKeyProcessor paintKeyProcessor){
        this.paintKeyProcessor = paintKeyProcessor;
    }

}
