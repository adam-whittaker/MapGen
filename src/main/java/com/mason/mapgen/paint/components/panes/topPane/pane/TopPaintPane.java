package com.mason.mapgen.paint.components.panes.topPane.pane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libstruct.interfaces.RectQuery;

public class TopPaintPane extends PaneWithDeco{


    private TopPaintPane(PaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static TopPaintPane buildEmptyPane(RectQuery boundary){
        PaneSkeleton skeleton = new PaneSkeleton();
        skeleton.setBoundary(boundary);
        return new TopPaintPane(skeleton);
    }

}
