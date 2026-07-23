package com.mason.mapgen.paint.components.panes.topPane.save;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridQuery;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

import java.io.File;

public class SaveAsButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final PaintGridQuery gridQuery;


    protected SaveAsButton(TopPaintPaneSkeleton skeleton, String name, HitboxRect boundary, ButtonDeco buttonDeco){
        super(name, boundary, buttonDeco);
        saveLocation = skeleton.getSaveLocation();
        gridQuery = skeleton.getGridQuery();
    }

    public static SaveAsButton build(TopPaintPaneSkeleton skeleton){
        String name = "SAVE_AS_BUTTON";
        HitboxRect boundary = ButtonBuilder.getBoundary(skeleton, name);
        ButtonDeco deco = ButtonBuilder.buildDeco(name);
        return new SaveAsButton(skeleton, name, boundary, deco);
    }


    @Override
    public void click(MouseInputEvent e){
        File file = PaintGridPersistence.chooseSaveFile();
        if(file != null){
            saveLocation.setFile(file);
            PaintGridPersistence.tryWriteToFile(saveLocation.getFile(), gridQuery.getGrid());
        }
    }

}
