package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;

public class UndoButton extends AbstractButton{


    private final Runnable undoCallable;


    protected UndoButton(HitboxRect boundary,
                         Runnable undoCallable){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/UNDO_BUTTON.png"));
        this.undoCallable = undoCallable;
    }


    @Override
    public void click(MouseInputEvent e){
        undoCallable.run();
    }

}
