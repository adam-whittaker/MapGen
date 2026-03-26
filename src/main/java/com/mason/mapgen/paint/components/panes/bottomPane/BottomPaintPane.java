package com.mason.mapgen.paint.components.panes.bottomPane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class BottomPaintPane extends PaneWithDeco{


    private BottomPaintPane(BottomPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static BottomPaintPane build(PaintGUIStateSkeleton paintGUIStateSkeleton){
        return new BottomPaintPane(BottomPaintPaneBuilder.buildSkeleton(paintGUIStateSkeleton));
    }

}
