package com.mason.mapgen.paint.components.panes.leftPane.toggles;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.toggles.PresetToggle;
import com.mason.libgui.components.toggles.ToggleEvent;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.function.Consumer;

public class BrushChannelIndependenceToggle extends PresetToggle{


    private final Consumer<Boolean> channelIndependenceSetter;


    protected BrushChannelIndependenceToggle(Size size, Consumer<Boolean> channelIndependenceSetter){
        super("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE",
                new HitboxRect(new Coord(0, 0), size),
                BasicButtonDeco.build("assets/paintIcons/channelIndependence.png"));
        this.channelIndependenceSetter = channelIndependenceSetter;
    }


    @Override
    public void toggleSelected(ToggleEvent event){
        channelIndependenceSetter.accept(true);
    }

    @Override
    public void toggleUnselected(ToggleEvent event){
        channelIndependenceSetter.accept(false);
    }

}
