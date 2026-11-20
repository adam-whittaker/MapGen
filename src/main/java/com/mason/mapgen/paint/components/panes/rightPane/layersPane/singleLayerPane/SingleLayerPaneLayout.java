package com.mason.mapgen.paint.components.panes.rightPane.layersPane.singleLayerPane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;

public class SingleLayerPaneLayout extends PaneLayout{


    public SingleLayerPaneLayout(Size size){
        super(Rect.buildRect(new Coord(0, 0), size));
        construct();
    }

    private void construct(){
        horizontalDissect("Root", 0.23, 0.6);

        nameAddress("[0,0]", "LAYER_THUMBNAIL");
        nameAddress("[0,1]", "LAYER_ALPHA_SLIDER");
        nameAddress("[0,2]", "LAYER_EXPORT_BUTTON");

        //reduceBoxSizeWithMargin("LAYER_ALPHA_SLIDER", 0.1, 0);
    }

}
