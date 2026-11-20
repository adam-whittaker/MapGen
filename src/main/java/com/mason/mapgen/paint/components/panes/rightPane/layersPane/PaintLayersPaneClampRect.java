package com.mason.mapgen.paint.components.panes.rightPane.layersPane;

import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.intState.IntQuery;

import static java.lang.Math.max;

public class PaintLayersPaneClampRect implements RectQuery{


    private final Coord coord;
    private final int width;
    private final int layerHeight;
    private final int minLayerNum;
    private final IntQuery layerCountQuery;


    public PaintLayersPaneClampRect(RectQuery paneBoundary, IntQuery layerCountQuery, int minLayerNum){
        this.width = paneBoundary.width();
        this.coord = new Coord(0, 0);
        this.layerHeight = paneBoundary.height() / minLayerNum;
        this.minLayerNum = minLayerNum;
        this.layerCountQuery = layerCountQuery;
    }


    @Override
    public Size getSize(){
        return new Size(width, layerHeight * max(minLayerNum, layerCountQuery.getState()));
    }

    @Override
    public Coord getCoord(){
        return coord;
    }

}
