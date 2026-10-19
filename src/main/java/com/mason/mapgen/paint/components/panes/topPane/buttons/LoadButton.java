package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridReceiver;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;
import com.mason.mapgen.structures.enums.DialogOption;

import java.io.File;

public class LoadButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final SaveButton saveButton;
    private final PaintGridReceiver paintGridReceiver;


    protected LoadButton(String name,
                         HitboxRect boundary,
                         ButtonDeco buttonDeco,
                         SaveLocation saveLocation,
                         SaveButton saveButton,
                         PaintGridReceiver paintGridReceiver){
        super(name, boundary, buttonDeco);
        this.saveLocation = saveLocation;
        this.saveButton = saveButton;
        this.paintGridReceiver = paintGridReceiver;
    }


    @Override
    public void click(MouseInputEvent e){
        if(!checkSaveFirst(e)){
            return;
        }
        File file = PaintGridPersistence.chooseLoadFile();
        if(file != null){
            saveLocation.setFile(file);
            paintGridReceiver.receiveGrid(PaintGridPersistence.tryReadFromFile(file));
        }
    }

    private boolean checkSaveFirst(MouseInputEvent e){
        DialogOption option = PaintGridPersistence.shouldSaveFirst();
        if(option.equals(DialogOption.YES)){
            saveButton.click(e);
            return true;
        }else return option.equals(DialogOption.NO);
    }

}
