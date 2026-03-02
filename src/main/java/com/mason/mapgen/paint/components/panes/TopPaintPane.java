package com.mason.mapgen.paint.components.panes;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.builders.TopPaintPaneBuilder;
import com.mason.mapgen.paint.skeletons.TopPaintPaneSkeleton;

public class TopPaintPane extends PaneWithDeco{


    private TopPaintPane(TopPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static TopPaintPane build(Size screenSize, int sidePaneWidth, int topPaneHeight){
        return new TopPaintPane(TopPaintPaneBuilder.buildSkeleton(screenSize, sidePaneWidth, topPaneHeight));
    }

}
