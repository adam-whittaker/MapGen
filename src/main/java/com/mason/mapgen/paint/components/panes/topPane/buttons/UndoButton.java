package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;

public class UndoButton extends AbstractButton{


    private final Runnable undoCallable;

    protected UndoButton(TopPaintPaneSkeleton skeleton, String name, HitboxRect boundary, ButtonDeco buttonDeco){
        super(name, boundary, buttonDeco);
        undoCallable = skeleton.getUndoCallable();
    }

    public static UndoButton build(TopPaintPaneSkeleton skeleton){
        String name = "UNDO_BUTTON";
        HitboxRect boundary = ButtonBuilder.getBoundary(skeleton, name);
        ButtonDeco deco = ButtonBuilder.buildDeco(name);
        return new UndoButton(skeleton, name, boundary, deco);
    }


    @Override
    public void click(MouseInputEvent e){
        undoCallable.run();
    }

}
