package com.mason.mapgen.paint.components.panes;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.builders.BottomPaintPaneBuilder;
import com.mason.mapgen.paint.skeletons.BottomPaintPaneSkeleton;

public class BottomPaintPane extends PaneWithDeco{


    private BottomPaintPane(BottomPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static BottomPaintPane build(Size screenSize, int sidePaneWidth, int topPaneHeight){
        return new BottomPaintPane(BottomPaintPaneBuilder.buildSkeleton(screenSize, sidePaneWidth, topPaneHeight));
    }

}
