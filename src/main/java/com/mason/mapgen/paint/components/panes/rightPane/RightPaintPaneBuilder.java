package com.mason.mapgen.paint.components.panes.rightPane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class RightPaintPaneBuilder{


    public static RightPaintPaneSkeleton buildSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        RightPaintPaneSkeleton skeleton = new RightPaintPaneSkeleton();
        PaneLayout layout = paintGUIStateSkeleton.getLayout();
        RectQuery boundary = layout.getBounds("RIGHT_PAINT_PANE");
        skeleton.setBoundary(boundary);
        return skeleton;
    }


}
