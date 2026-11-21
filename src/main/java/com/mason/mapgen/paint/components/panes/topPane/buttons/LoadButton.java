package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;
import com.mason.mapgen.structures.enums.DialogOption;

import java.io.File;
import java.util.function.Consumer;

public class LoadButton extends TrySaveFirstButton{


    private final SaveLocation saveLocation;
    private final Consumer<PaintGridSaveData> saveDataLoader;


    protected LoadButton(HitboxRect boundary,
                         SaveLocation saveLocation,
                         SaveButton saveButton,
                         Consumer<PaintGridSaveData> saveDataLoader){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/LOAD_BUTTON.png"), saveButton);
        this.saveLocation = saveLocation;
        this.saveDataLoader = saveDataLoader;
    }


    @Override
    public void click(MouseInputEvent e){
        if(!trySaveFirst(e)){
            return;
        }
        File file = PaintGridPersistence.chooseLoadFile();
        if(file != null){
            saveLocation.setFile(file);
            saveDataLoader.accept(PaintGridPersistence.tryReadFromFile(file));
        }
    }

}
