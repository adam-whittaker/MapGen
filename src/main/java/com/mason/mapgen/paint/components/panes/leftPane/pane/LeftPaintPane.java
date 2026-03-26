package com.mason.mapgen.paint.components.panes.leftPane.pane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class LeftPaintPane extends PaneWithDeco{


    private LeftPaintPane(LeftPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static LeftPaintPane build(PaintGUIStateSkeleton paintGUIStateSkeleton){
        return new LeftPaintPane(LeftPaintPaneBuilder.buildSkeleton(paintGUIStateSkeleton));
    }


}
