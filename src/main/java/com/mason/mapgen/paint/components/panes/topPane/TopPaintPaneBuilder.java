package com.mason.mapgen.paint.components.panes.topPane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;

public class TopPaintPaneBuilder{


    public static TopPaintPaneSkeleton buildSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        TopPaintPaneSkeleton skeleton = new TopPaintPaneSkeleton();
        PaneLayout layout = paintGUIStateSkeleton.getLayout();
        RectQuery boundary = layout.getBounds("TOP_PAINT_PANE");
        skeleton.setBoundary(boundary);
        return skeleton;
    }

}
