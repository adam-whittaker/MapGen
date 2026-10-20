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
        reduceBoxSizeWithMargin("Root", 0.02, 0.05);
        horizontalDissect("Root", new double[]{0.3});
        horizontalDissect("[0,0]", new double[]{0.166, 0.166, 0.166, 0.166, 0.166});

        nameAddress("[0,0]-[0,0]", "SAVE_BUTTON");
        nameAddress("[0,0]-[0,1]", "SAVE_AS_BUTTON");
        nameAddress("[0,0]-[0,2]", "LOAD_BUTTON");
        nameAddress("[0,0]-[0,3]", "EXPORT_BUTTON");
        nameAddress("[0,0]-[0,4]", "UNDO_BUTTON");
        nameAddress("[0,0]-[0,5]", "REDO_BUTTON");

    }

}
