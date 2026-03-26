package com.mason.mapgen.paint.components.panes.bottomPane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class BottomPaintPaneBuilder{


    public static BottomPaintPaneSkeleton buildSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        BottomPaintPaneSkeleton skeleton = new BottomPaintPaneSkeleton();
        PaneLayout layout = paintGUIStateSkeleton.getLayout();
        RectQuery boundary = layout.getBounds("BOTTOM_PAINT_PANE");
        skeleton.setBoundary(boundary);
        return skeleton;
    }

}
