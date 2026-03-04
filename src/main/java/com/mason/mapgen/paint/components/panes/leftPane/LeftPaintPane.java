package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.builders.LeftPaintPaneBuilder;
import com.mason.mapgen.paint.skeletons.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.skeletons.PaintGUIStateSkeleton;

public class LeftPaintPane extends PaneWithDeco{


    private LeftPaintPane(LeftPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static LeftPaintPane build(PaintGUIStateSkeleton paintGUIStateSkeleton, int sidePaneWidth){
        return new LeftPaintPane(LeftPaintPaneBuilder.buildSkeleton(paintGUIStateSkeleton, sidePaneWidth));
    }


}
