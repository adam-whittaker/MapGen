package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridQuery;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

public class SaveButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final SaveAsButton saveAsButton;
    private final PaintGridQuery gridQuery;


    protected SaveButton(TopPaintPaneSkeleton skeleton, String name, HitboxRect boundary, ButtonDeco buttonDeco){
        super(name, boundary, buttonDeco);
        saveLocation = skeleton.getSaveLocation();
        saveAsButton = skeleton.getSaveAsButton();
        gridQuery = skeleton.getGridQuery();
    }

    public static SaveButton build(TopPaintPaneSkeleton skeleton){
        String name = "SAVE_BUTTON";
        HitboxRect boundary = ButtonBuilder.getBoundary(skeleton, name);
        ButtonDeco deco = ButtonBuilder.buildDeco(name);
        return new SaveButton(skeleton, name, boundary, deco);
    }


    @Override
    public void click(MouseInputEvent e){
        if(saveLocation.isFileSet()){
            PaintGridPersistence.tryWriteToFile(saveLocation.getFile(), gridQuery.getGrid());
        }else{
            saveAsButton.click(e);
        }
    }

}
