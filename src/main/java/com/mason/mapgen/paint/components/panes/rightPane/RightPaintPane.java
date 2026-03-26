package com.mason.mapgen.paint.components.panes.rightPane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class RightPaintPane extends PaneWithDeco{


    private RightPaintPane(RightPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static RightPaintPane build(PaintGUIStateSkeleton paintGUIStateSkeleton){
        return new RightPaintPane(RightPaintPaneBuilder.buildSkeleton(paintGUIStateSkeleton));
    }

}
