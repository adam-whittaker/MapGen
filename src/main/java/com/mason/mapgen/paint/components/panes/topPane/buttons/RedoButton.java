package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;

public class RedoButton extends AbstractButton{


    private final Runnable redoCallable;


    protected RedoButton(HitboxRect boundary,
                         Runnable redoCallable){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/REDO_BUTTON.png"));
        this.redoCallable = redoCallable;
    }


    @Override
    public void click(MouseInputEvent e){
        redoCallable.run();
    }

}
