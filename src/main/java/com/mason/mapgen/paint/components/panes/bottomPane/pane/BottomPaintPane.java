package com.mason.mapgen.paint.components.panes.bottomPane.pane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libstruct.interfaces.RectQuery;

public class BottomPaintPane extends PaneWithDeco{


    private BottomPaintPane(PaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static BottomPaintPane buildEmptyPane(RectQuery boundary){
        PaneSkeleton skeleton = new PaneSkeleton();
        skeleton.setBoundary(boundary);
        return new BottomPaintPane(skeleton);
    }

}
