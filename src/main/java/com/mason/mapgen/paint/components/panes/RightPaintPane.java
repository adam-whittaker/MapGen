package com.mason.mapgen.paint.components.panes;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.builders.RightPaintPaneBuilder;
import com.mason.mapgen.paint.skeletons.RightPaintPaneSkeleton;

public class RightPaintPane extends PaneWithDeco{


    private RightPaintPane(RightPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static RightPaintPane build(Size screenSize, int sidePaneWidth){
        return new RightPaintPane(RightPaintPaneBuilder.buildSkeleton(screenSize, sidePaneWidth));
    }

}
