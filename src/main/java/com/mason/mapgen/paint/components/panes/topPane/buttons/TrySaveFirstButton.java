package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;
import com.mason.mapgen.structures.enums.DialogOption;

public abstract class TrySaveFirstButton extends AbstractButton{


    private final SaveButton saveButton;


    protected TrySaveFirstButton(HitboxRect hitbox, ButtonDeco buttonDeco, SaveButton saveButton){
        super(hitbox, buttonDeco);
        this.saveButton = saveButton;
    }


    protected boolean trySaveFirst(MouseInputEvent e){
        DialogOption option = PaintGridPersistence.shouldSaveFirst();
        if(option.equals(DialogOption.YES)){
            saveButton.click(e);
            return false;
        }else return option.equals(DialogOption.NO);
    }

}
