package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridQuery;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

public class SaveButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final SaveAsButton saveAsButton;
    private final PaintGridQuery paintGridQuery;


    protected SaveButton(String name,
                         HitboxRect boundary,
                         ButtonDeco buttonDeco,
                         SaveLocation saveLocation,
                         SaveAsButton saveAsButton,
                         PaintGridQuery paintGridQuery){
        super(name, boundary, buttonDeco);
        this.saveLocation = saveLocation;
        this.saveAsButton = saveAsButton;
        this.paintGridQuery = paintGridQuery;
    }


    @Override
    public void click(MouseInputEvent e){
        if(saveLocation.isFileSet()){
            PaintGridPersistence.tryWriteToFile(saveLocation.getFile(), paintGridQuery.getGrid());
        }else{
            saveAsButton.click(e);
        }
    }

}
