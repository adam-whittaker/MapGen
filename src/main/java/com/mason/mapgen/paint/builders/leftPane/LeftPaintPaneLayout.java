package com.mason.mapgen.paint.builders.leftPane;

import com.mason.libgui.components.panes.PaneLayout;
import com.mason.libgui.utils.structures.RectQuery;

public class LeftPaintPaneLayout extends PaneLayout{


    public LeftPaintPaneLayout(RectQuery bounds){
        super(bounds);
        construct();
    }

    private void construct(){
        divide("Root", 3, 1);
        nameAddress("[2,0]", "PALETTE_BOX");

        verticalDissect("[1,0]", 0.8);
        nameAddress("[1,0]-[0,0]", "COLOR_SELECTOR_BOX");
        nameAddress("[1,0]-[1,0]", "BRUSH_BRIGHTNESS_SLIDER_BOX");

        divide("[0,0]", 2, 2);
        nameAddress("[0,0]-[0,0]", "TOGGLE_BOX");
        nameAddress("[0,0]-[0,1]", "BRUSH_COLOR_DISPLAY_BOX");

        divide("TOGGLE_BOX", 1, 6);
        nameAddress("TOGGLE_BOX-[0,1]", "BRUSH_TOGGLE_BOX");
        nameAddress("TOGGLE_BOX-[0,2]", "COLOR_PICKER_TOGGLE_BOX");
        nameAddress("TOGGLE_BOX-[0,3]", "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE_BOX");

        divide("[0,0]-[1,0]", 2, 1);
        divide("[0,0]-[1,1]", 2, 1);
        nameAddress("[0,0]-[1,0]-[0,0]", "BRUSH_SIZE_SLIDER_BOX");
        nameAddress("[0,0]-[1,0]-[1,0]", "BRUSH_ALPHA_SLIDER_BOX");
        nameAddress("[0,0]-[1,1]-[0,0]", "BRUSH_CENTRE_SLIDER_BOX");
        nameAddress("[0,0]-[1,1]-[1,0]", "BRUSH_CERTAINTY_SLIDER_BOX");
    }


}
