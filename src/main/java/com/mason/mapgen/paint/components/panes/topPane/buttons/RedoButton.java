package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;

public class RedoButton extends AbstractButton{


    private final Runnable redoCallable;


    protected RedoButton(TopPaintPaneSkeleton skeleton, String name, HitboxRect boundary, ButtonDeco buttonDeco){
        super(name, boundary, buttonDeco);
        redoCallable = skeleton.getRedoCallable();
    }

    public static RedoButton build(TopPaintPaneSkeleton skeleton){
        String name = "REDO_BUTTON";
        HitboxRect boundary = ButtonBuilder.getBoundary(skeleton, name);
        ButtonDeco deco = ButtonBuilder.buildDeco(name);
        return new RedoButton(skeleton, name, boundary, deco);
    }


    @Override
    public void click(MouseInputEvent e){
        redoCallable.run();
    }

}
