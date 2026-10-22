package com.mason.mapgen.paint.components.panes.rightPane.referenceButtons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;

public class RecentreReferenceButton extends AbstractButton{


    private final Runnable recentre;


    protected RecentreReferenceButton(HitboxRect boundary,
                                      Runnable recentre){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/RECENTRE_REFERENCE_BUTTON.png"));
        this.recentre = recentre;
    }


    @Override
    public void click(MouseInputEvent e){
        recentre.run();
    }

}
