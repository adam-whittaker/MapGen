package com.mason.mapgen.paint.builders;

import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.skeletons.TopPaintPaneSkeleton;

public class TopPaintPaneBuilder{


    public static TopPaintPaneSkeleton buildSkeleton(Size screenSize, int sidePaneWidth, int topPaneHeight){
        TopPaintPaneSkeleton skeleton = new TopPaintPaneSkeleton();
        int topPaneWidth = screenSize.width() - 2 * sidePaneWidth;
        Size size = new Size(topPaneWidth, topPaneHeight);
        Coord topLeft = new Coord(sidePaneWidth, 0);
        skeleton.setBoundary(new HitboxRect(topLeft, size));
        return skeleton;
    }

}
