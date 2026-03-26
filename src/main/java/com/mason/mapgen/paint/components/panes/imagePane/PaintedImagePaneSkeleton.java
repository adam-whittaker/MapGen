package com.mason.mapgen.paint.components.panes.imagePane;

import com.mason.libgui.components.panes.construction.PanZoomPaneSkeleton;

public class PaintedImagePaneSkeleton{


    private PanZoomPaneSkeleton panZoomPaneSkeleton;


    public PaintedImagePaneSkeleton(){}


    public PanZoomPaneSkeleton getPanZoomPaneSkeleton(){
        return panZoomPaneSkeleton;
    }

    public void setPanZoomPaneSkeleton(PanZoomPaneSkeleton panZoomPaneSkeleton){
        this.panZoomPaneSkeleton = panZoomPaneSkeleton;
    }

}
