package com.mason.mapgen.paint.builders;

import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.skeletons.RightPaintPaneSkeleton;

public class RightPaintPaneBuilder{


    public static RightPaintPaneSkeleton buildSkeleton(Size screenSize, int sidePaneWidth){
        Size size = new Size(sidePaneWidth, screenSize.height());
        RightPaintPaneSkeleton skeleton = new RightPaintPaneSkeleton();
        Coord topLeft = new Coord(screenSize.width() - sidePaneWidth, 0);
        skeleton.setBoundary(new HitboxRect(topLeft, size));
        return skeleton;
    }


}
