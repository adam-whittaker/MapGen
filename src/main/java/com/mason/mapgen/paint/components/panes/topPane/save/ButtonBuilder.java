package com.mason.mapgen.paint.components.panes.topPane.save;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;

public class ButtonBuilder{


    static HitboxRect getBoundary(TopPaintPaneSkeleton skeleton, String name){
        PaneLayout layout = skeleton.getPaneLayout();
        Size size = skeleton.getButtonSize();
        Coord coord = layout.centre(name, size);
        return new BasicHitboxRect(coord, size);
    }

    static ButtonDeco buildDeco(String name){
        return BasicButtonDeco.build("assets/paintIcons/" + name + ".png");
    }

}
