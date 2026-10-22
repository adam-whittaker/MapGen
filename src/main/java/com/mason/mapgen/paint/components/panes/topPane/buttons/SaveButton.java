package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

import java.util.function.Supplier;

public class SaveButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final SaveAsButton saveAsButton;
    private final Supplier<PaintGrid> paintGridQuery;


    protected SaveButton(HitboxRect boundary,
                         SaveLocation saveLocation,
                         SaveAsButton saveAsButton,
                         Supplier<PaintGrid> paintGridQuery){
        super(boundary, BasicButtonDeco.build("assets/paintIcons/SAVE_BUTTON.png"));
        this.saveLocation = saveLocation;
        this.saveAsButton = saveAsButton;
        this.paintGridQuery = paintGridQuery;
    }


    @Override
    public void click(MouseInputEvent e){
        if(saveLocation.isFileSet()){
            PaintGridPersistence.tryWriteToFile(saveLocation.getFile(), paintGridQuery.get());
        }else{
            saveAsButton.click(e);
        }
    }

}
