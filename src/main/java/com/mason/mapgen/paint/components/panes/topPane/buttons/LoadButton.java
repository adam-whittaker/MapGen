package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;
import com.mason.mapgen.structures.enums.DialogOption;

import java.io.File;
import java.util.function.Consumer;

public class LoadButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final SaveButton saveButton;
    private final Consumer<PaintGrid> paintGridLoader;


    protected LoadButton(String name,
                         HitboxRect boundary,
                         ButtonDeco buttonDeco,
                         SaveLocation saveLocation,
                         SaveButton saveButton,
                         Consumer<PaintGrid> paintGridLoader){
        super(name, boundary, buttonDeco);
        this.saveLocation = saveLocation;
        this.saveButton = saveButton;
        this.paintGridLoader = paintGridLoader;
    }


    @Override
    public void click(MouseInputEvent e){
        if(!checkSaveFirst(e)){
            return;
        }
        File file = PaintGridPersistence.chooseLoadFile();
        if(file != null){
            saveLocation.setFile(file);
            paintGridLoader.accept(PaintGridPersistence.tryReadFromFile(file));
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
