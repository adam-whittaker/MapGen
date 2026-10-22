package com.mason.mapgen.paint.components.panes.rightPane.referenceButtons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;

public class ClearReferenceButton extends AbstractButton{


    private final Runnable clearReference;


    protected ClearReferenceButton(HitboxRect boundary,
                                   Runnable clearReference){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/CLEAR_REFERENCE_BUTTON.png"));
        this.clearReference = clearReference;
    }


    @Override
    public void click(MouseInputEvent e){
        clearReference.run();
    }

}
