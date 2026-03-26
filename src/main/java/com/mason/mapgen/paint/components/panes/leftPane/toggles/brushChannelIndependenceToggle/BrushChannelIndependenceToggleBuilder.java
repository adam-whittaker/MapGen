package com.mason.mapgen.paint.components.panes.leftPane.toggles.brushChannelIndependenceToggle;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.components.toggles.sourced.SourcedToggle;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.utils.structures.states.onOff.OnOffState;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;

public class BrushChannelIndependenceToggleBuilder{


    public static Toggle buildToggle(LeftPaintPaneSkeleton skeleton){
        String name = "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE";
        HitboxRect boundary = getBoundary(skeleton, name);
        ButtonDeco deco = buildDeco(name);
        OnOffState sourceState = getSourceState(skeleton);
        return new SourcedToggle(name, boundary, deco, sourceState);
    }

    private static HitboxRect getBoundary(LeftPaintPaneSkeleton skeleton, String name){
        PaneLayout layout = skeleton.getPaneLayout();
        Size size = skeleton.getToggleSize();
        Coord coord = layout.centre(name, size);
        return new BasicHitboxRect(coord, size);
    }

    private static ButtonDeco buildDeco(String name){
        return BasicButtonDeco.build("assets/paintIcons/channelIndependence.png");
    }

    private static OnOffState getSourceState(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        return settingsSkeleton.getChannelIndependenceState();
    }

}
