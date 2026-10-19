package com.mason.mapgen.paint.components.panes.rightPane.pane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libstruct.interfaces.RectQuery;

public class RightPaintPane extends PaneWithDeco{


    private RightPaintPane(PaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static RightPaintPane buildEmptyPane(RectQuery boundary){
        PaneSkeleton skeleton = new PaneSkeleton();
        skeleton.setBoundary(boundary);
        return new RightPaintPane(skeleton);
    }

}
