package com.mason.mapgen.paint.components.panes.topPane.pane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;

import java.util.Arrays;

public class TopPaintPaneLayout extends PaneLayout{


    public TopPaintPaneLayout(Size size){
        super(HitboxRect.build(new Coord(0,0), size));
        construct();
    }

    private void construct(){
        double[] dissectionRatios = createEqualDissectionRatios(7);
        reduceBoxSizeWithMargin("Root", 0.02, 0.05);
        horizontalDissect("Root", 0.35);
        horizontalDissect("[0,0]", dissectionRatios);

        nameAddress("[0,0]-[0,0]", "NEW_PROJECT_BUTTON");
        nameAddress("[0,0]-[0,1]", "SAVE_BUTTON");
        nameAddress("[0,0]-[0,2]", "SAVE_AS_BUTTON");
        nameAddress("[0,0]-[0,3]", "LOAD_BUTTON");
        nameAddress("[0,0]-[0,4]", "EXPORT_BUTTON");
        nameAddress("[0,0]-[0,5]", "UNDO_BUTTON");
        nameAddress("[0,0]-[0,6]", "REDO_BUTTON");
    }

    private double[] createEqualDissectionRatios(int buttonNum){
        double[] ratios = new double[buttonNum-1];
        Arrays.fill(ratios, 1D / buttonNum);
        return ratios;
    }


}
