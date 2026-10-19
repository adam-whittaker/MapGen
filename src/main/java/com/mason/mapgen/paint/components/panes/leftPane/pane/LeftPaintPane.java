package com.mason.mapgen.paint.components.panes.leftPane.pane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libstruct.interfaces.RectQuery;

public class LeftPaintPane extends PaneWithDeco{


    protected LeftPaintPane(PaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static LeftPaintPane buildEmptyPane(RectQuery boundary){
        PaneSkeleton skeleton = new PaneSkeleton();
        skeleton.setBoundary(boundary);
        return new LeftPaintPane(skeleton);
    }


}
