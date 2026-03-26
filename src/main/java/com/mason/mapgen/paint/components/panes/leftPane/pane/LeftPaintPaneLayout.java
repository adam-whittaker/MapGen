package com.mason.mapgen.paint.components.panes.leftPane.pane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.utils.structures.interfaces.RectQuery;

public class LeftPaintPaneLayout extends PaneLayout{


    private final int sliderHeight;


    public LeftPaintPaneLayout(RectQuery bounds, int sliderHeight){
        super(bounds);
        this.sliderHeight = sliderHeight;
        construct();
    }

    private void construct(){
        divide("Root", 3, 1);
        nameAddress("[2,0]", "PALETTE");
        reduceBoxSizeWithMargin("PALETTE", 0.08, 0.1);

        verticalDissect("[1,0]", new double[]{0.8});
        nameAddress("[1,0]-[0,0]", "COLOR_SELECTOR");
        nameAddress("[1,0]-[1,0]", "BRUSH_BRIGHTNESS_SLIDER");
        reduceBoxSizeWithMargin("COLOR_SELECTOR", 0.08, 0.05);
        setAbsoluteBoxHeight("BRUSH_BRIGHTNESS_SLIDER", sliderHeight);
        reduceBoxSizeWithMargin("BRUSH_BRIGHTNESS_SLIDER", 0.1, 0);

        divide("[0,0]", 2, 2);
        verticalDissect("[0,0]-[0,0]", new double[]{0.2});
        nameAddress("[0,0]-[0,0]-[1,0]", "TOGGLE_BOX");
        nameAddress("[0,0]-[0,1]", "BRUSH_COLOR_DISPLAY");
        reduceBoxSizeWithMargin("TOGGLE_BOX", 0.0, 0.15);

        divide("TOGGLE_BOX", 2, 6);
        nameAddress("TOGGLE_BOX-[0,1]", "BRUSH_0_TOGGLE");
        nameAddress("TOGGLE_BOX-[0,2]", "COLOR_PICKER_TOGGLE");
        nameAddress("TOGGLE_BOX-[0,3]", "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE");
        nameAddress("TOGGLE_BOX-[1,1]", "BRUSH_1_TOGGLE");
        nameAddress("TOGGLE_BOX-[1,2]", "BRUSH_2_TOGGLE");
        nameAddress("TOGGLE_BOX-[1,3]", "BRUSH_3_TOGGLE");

        divide("[0,0]-[1,0]", 2, 1);
        divide("[0,0]-[1,1]", 2, 1);
        nameAddress("[0,0]-[1,0]-[0,0]", "BRUSH_SIZE_SLIDER");
        nameAddress("[0,0]-[1,0]-[1,0]", "BRUSH_ALPHA_SLIDER");
        nameAddress("[0,0]-[1,1]-[0,0]", "BRUSH_CENTRE_SLIDER");
        nameAddress("[0,0]-[1,1]-[1,0]", "BRUSH_CERTAINTY_SLIDER");
        setAbsoluteBoxHeight("BRUSH_SIZE_SLIDER", sliderHeight);
        setAbsoluteBoxHeight("BRUSH_ALPHA_SLIDER", sliderHeight);
        setAbsoluteBoxHeight("BRUSH_CENTRE_SLIDER", sliderHeight);
        setAbsoluteBoxHeight("BRUSH_CERTAINTY_SLIDER", sliderHeight);
        reduceBoxSizeWithMargin("BRUSH_SIZE_SLIDER", 0.1, 0);
        reduceBoxSizeWithMargin("BRUSH_ALPHA_SLIDER", 0.1, 0);
        reduceBoxSizeWithMargin("BRUSH_CENTRE_SLIDER", 0.1, 0);
        reduceBoxSizeWithMargin("BRUSH_CERTAINTY_SLIDER", 0.1, 0);
    }


}
