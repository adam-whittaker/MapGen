package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;

public class RedoButton extends AbstractButton{


    private final Runnable redoCallable;


    protected RedoButton(String name,
                         HitboxRect boundary,
                         ButtonDeco buttonDeco,
                         Runnable redoCallable){
        super(name, boundary, buttonDeco);
        this.redoCallable = redoCallable;
    }


    @Override
    public void click(MouseInputEvent e){
        redoCallable.run();
    }

}
