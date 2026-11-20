package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;
import com.mason.mapgen.paint.logic.fileIO.PaintGridSaveData;

import java.io.File;
import java.util.function.Supplier;

public class SaveAsButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final Supplier<PaintGridSaveData> saveDataQuery;


    protected SaveAsButton(HitboxRect boundary,
                           SaveLocation saveLocation,
                           Supplier<PaintGridSaveData> saveDataQuery){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/SAVE_AS_BUTTON.png"));
        this.saveLocation = saveLocation;
        this.saveDataQuery = saveDataQuery;
    }


    @Override
    public void click(MouseInputEvent e){
        File file = PaintGridPersistence.chooseSaveFile();
        if(file != null){
            saveLocation.setFile(file);
            PaintGridPersistence.tryWriteToFile(saveLocation.getFile(), saveDataQuery.get());
        }
    }

}
