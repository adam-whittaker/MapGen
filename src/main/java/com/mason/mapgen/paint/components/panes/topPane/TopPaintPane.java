package com.mason.mapgen.paint.components.panes.topPane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class TopPaintPane extends PaneWithDeco{


    private TopPaintPane(TopPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static TopPaintPane build(PaintGUIStateSkeleton paintGUIStateSkeleton){
        return new TopPaintPane(TopPaintPaneBuilder.buildSkeleton(paintGUIStateSkeleton));
    }

}
