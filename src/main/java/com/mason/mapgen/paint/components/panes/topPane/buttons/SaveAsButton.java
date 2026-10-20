package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

import java.io.File;
import java.util.function.Supplier;

public class SaveAsButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final Supplier<PaintGrid> paintGridQuery;


    protected SaveAsButton(String name,
                           HitboxRect boundary,
                           ButtonDeco buttonDeco,
                           SaveLocation saveLocation,
                           Supplier<PaintGrid> paintGridQuery){
        super(name, boundary, buttonDeco);
        this.saveLocation = saveLocation;
        this.paintGridQuery = paintGridQuery;
    }


    @Override
    public void click(MouseInputEvent e){
        File file = PaintGridPersistence.chooseSaveFile();
        if(file != null){
            saveLocation.setFile(file);
            PaintGridPersistence.tryWriteToFile(saveLocation.getFile(), paintGridQuery.get());
        }
    }

}
