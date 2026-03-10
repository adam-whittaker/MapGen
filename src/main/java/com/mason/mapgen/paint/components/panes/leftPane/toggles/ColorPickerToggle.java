package com.mason.mapgen.paint.components.panes.leftPane.toggles;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.toggles.ExclusiveToggleGroup;
import com.mason.libgui.components.toggles.PresetToggle;
import com.mason.libgui.components.toggles.ToggleEvent;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

public class ColorPickerToggle extends PresetToggle{


    private final Runnable setToolToColorPicker;


    protected ColorPickerToggle(Size size, Runnable setToolToColorPicker, ExclusiveToggleGroup toggleGroup){
        super("COLOR_PICKER_TOGGLE",
                new HitboxRect(new Coord(0, 0), size),
                BasicButtonDeco.build("assets/paintIcons/colorPicker.png"));
        this.setToolToColorPicker = setToolToColorPicker;
        toggleGroup.addToggle(this);
    }


    @Override
    public void toggleSelected(ToggleEvent event){
        setToolToColorPicker.run();
    }

    @Override
    public void toggleUnselected(ToggleEvent event){}

}
