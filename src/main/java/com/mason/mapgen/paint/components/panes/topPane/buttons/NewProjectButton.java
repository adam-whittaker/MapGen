package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;


public class NewProjectButton extends TrySaveFirstButton{


    private final Runnable newProjectCreator;


    protected NewProjectButton(
             HitboxRect boundary,
             SaveButton saveButton,
             Runnable newProjectCreator
    ){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/NEW_PROJECT_BUTTON.png"), saveButton);
        this.newProjectCreator = newProjectCreator;
    }


    @Override
    public void click(MouseInputEvent e){
        if(!trySaveFirst(e)){
            return;
        }
        newProjectCreator.run();
    }

}
