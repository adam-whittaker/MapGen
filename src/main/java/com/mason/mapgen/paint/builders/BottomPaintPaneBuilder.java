package com.mason.mapgen.paint.builders;

import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.skeletons.BottomPaintPaneSkeleton;

public class BottomPaintPaneBuilder{


    public static BottomPaintPaneSkeleton buildSkeleton(Size screenSize, int sidePaneWidth, int topPaneHeight){
        BottomPaintPaneSkeleton skeleton = new BottomPaintPaneSkeleton();
        int bottomPaneWidth = screenSize.width() - 2 * sidePaneWidth;
        int bottomPaneHeight = screenSize.height() - topPaneHeight - bottomPaneWidth;
        Size size = new Size(bottomPaneWidth, bottomPaneHeight);
        Coord topLeft = new Coord(sidePaneWidth, topPaneHeight + bottomPaneWidth);
        skeleton.setBoundary(new HitboxRect(topLeft, size));
        return skeleton;
    }

}
