package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
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


    protected SaveAsButton(HitboxRect boundary,
                           SaveLocation saveLocation,
                           Supplier<PaintGrid> paintGridQuery){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/SAVE_AS_BUTTON.png"));
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
