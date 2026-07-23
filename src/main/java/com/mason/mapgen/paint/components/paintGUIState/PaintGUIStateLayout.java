package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;

public class PaintGUIStateLayout extends PaneLayout{


    private final double[] horizontalDissection;
    private final double[] verticalDissection;


    public PaintGUIStateLayout(PaintGUIStateLayoutParameters params){
        super(HitboxRect.build(new Coord(0, 0), params.getScreenSize()));
        horizontalDissection = constructHorizontalDissection(params);
        verticalDissection = constructVerticalDissection(params);
        construct();
    }

    private static double[] constructHorizontalDissection(PaintGUIStateLayoutParameters params){
        double d1 = params.getLeftPaneWidthRatio();
        double d2 = 1 - params.getRightPaneWidthRatio() - params.getLeftPaneWidthRatio();
        return new double[]{d1, d2};
    }

    private static double[] constructVerticalDissection(PaintGUIStateLayoutParameters params){
        double d1 = params.getTopPaneHeightRatio();
        double d2 = 1 - params.getBottomPaneHeightRatio() - params.getTopPaneHeightRatio();
        return new double[]{d1, d2};
    }

    private void construct(){
        horizontalDissect("ROOT", horizontalDissection);
        nameAddress("[0,0]", "LEFT_PAINT_PANE");
        nameAddress("[0,2]", "RIGHT_PAINT_PANE");

        verticalDissect("[0,1]", verticalDissection);
        nameAddress("[0,1]-[0,0]", "TOP_PAINT_PANE");
        nameAddress("[0,1]-[1,0]", "IMAGE_PAINT_PANE");
        nameAddress("[0,1]-[2,0]", "BOTTOM_PAINT_PANE");
    }

}
