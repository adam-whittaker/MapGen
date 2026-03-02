package com.mason.mapgen.paint.skeletons;

import com.mason.libgui.components.panes.PanZoomPaneSkeleton;
import com.mason.mapgen.paint.components.GridImageComponent;

public class PaintedImagePaneSkeleton{


    private PanZoomPaneSkeleton panZoomPaneSkeleton;
    private GridImageComponent paintedImageComponent;


    public PaintedImagePaneSkeleton(){}


    public PanZoomPaneSkeleton getPanZoomPaneSkeleton(){
        return panZoomPaneSkeleton;
    }

    public void setPanZoomPaneSkeleton(PanZoomPaneSkeleton panZoomPaneSkeleton){
        this.panZoomPaneSkeleton = panZoomPaneSkeleton;
    }

    public GridImageComponent getPaintedImageComponent(){
        return paintedImageComponent;
    }

    public void setPaintedImageComponent(GridImageComponent paintedImageComponent){
        this.paintedImageComponent = paintedImageComponent;
    }

}
