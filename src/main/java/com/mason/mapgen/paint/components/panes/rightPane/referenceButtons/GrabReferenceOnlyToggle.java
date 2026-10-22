package com.mason.mapgen.paint.components.panes.rightPane.referenceButtons;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.toggles.AbstractToggle;
import com.mason.libgui.core.component.hitbox.HitboxRect;

public class GrabReferenceOnlyToggle extends AbstractToggle{


    private boolean state = false;


    public GrabReferenceOnlyToggle(HitboxRect hitbox){
        super(hitbox, BasicButtonDeco.build("assets/paintIcons/GRAB_REFERENCE_ONLY_TOGGLE.png"));
    }

    @Override
    public void turnOn(){
        state = true;
    }

    @Override
    public void turnOff(){
        state = false;
    }

    @Override
    public boolean isOn(){
        return state;
    }

}
