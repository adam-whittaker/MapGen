package com.mason.mapgen.paint.components.panes.rightPane.pane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;

public class RightPaintPaneLayout extends PaneLayout{


    public RightPaintPaneLayout(Size size){
        super(HitboxRect.build(new Coord(0,0), size));
        construct();
    }

    private void construct(){
        verticalDissect("Root", 0.2, 0.5);
        horizontalDissect("[0,0]", 0.7);
        reduceBoxSizeWithMargin("[0,0]-[0,1]", 0.2, 0.29);
        divide("[0,0]-[0,1]", 2, 2);

        nameAddress("[0,0]-[0,0]", "REFERENCE_ALPHA_SLIDER");
        reduceBoxSizeWithMargin("REFERENCE_ALPHA_SLIDER", 0.1, 0.4);

        horizontalDissect("[1,0]", 0.85);
        nameAddress("[1,0]-[0,0]", "PAINT_LAYERS_PANE");
        reduceBoxSizeWithMargin("PAINT_LAYERS_PANE", 0.1, 0.2);

        verticalDissect("[1,0]-[0,1]", 0.2, 0.1);
        horizontalDissect("[1,0]-[0,1]-[1,0]", 0.6);
        nameAddress("[1,0]-[0,1]-[1,0]-[0,0]", "CREATE_LAYER_BUTTON");

        nameAddress("[0,0]-[0,1]-[0,0]", "RECENTRE_REFERENCE_BUTTON");
        nameAddress("[0,0]-[0,1]-[0,1]", "CLEAR_REFERENCE_BUTTON");
        nameAddress("[0,0]-[0,1]-[1,0]", "LOAD_REFERENCE_BUTTON");
        nameAddress("[0,0]-[0,1]-[1,1]", "GRAB_REFERENCE_ONLY_TOGGLE");
    }

}
