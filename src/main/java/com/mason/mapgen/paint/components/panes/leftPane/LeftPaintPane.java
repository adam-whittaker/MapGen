package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.components.panes.PaneWithDeco;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.components.deco.BasicPaneDeco;
import com.mason.mapgen.paint.builders.LeftPaintPaneBuilder;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.paint.skeletons.LeftPaintPaneSkeleton;

public class LeftPaintPane extends PaneWithDeco{


    private LeftPaintPane(LeftPaintPaneSkeleton skeleton){
        super(skeleton, new BasicPaneDeco());
    }

    public static LeftPaintPane build(Size screenSize, int sidePaneWidth, PaletteImageComponent paletteImageComponent, PaintManager paintManager){
        return new LeftPaintPane(LeftPaintPaneBuilder.buildSkeleton(screenSize, sidePaneWidth, paletteImageComponent, paintManager));
    }


}
