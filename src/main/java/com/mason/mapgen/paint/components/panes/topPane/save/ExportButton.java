package com.mason.mapgen.paint.components.panes.topPane.save;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;
import com.mason.mapgen.paint.components.panes.topPane.resources.ImageQuery;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

import java.io.File;

public class ExportButton extends AbstractButton{


    private final ImageQuery imageQuery;


    protected ExportButton(TopPaintPaneSkeleton skeleton, String name, HitboxRect boundary, ButtonDeco buttonDeco){
        super(name, boundary, buttonDeco);
        imageQuery = skeleton.getImageQuery();
    }

    public static ExportButton build(TopPaintPaneSkeleton skeleton){
        String name = "EXPORT_BUTTON";
        HitboxRect boundary = ButtonBuilder.getBoundary(skeleton, name);
        ButtonDeco deco = ButtonBuilder.buildDeco(name);
        return new ExportButton(skeleton, name, boundary, deco);
    }


    @Override
    public void click(MouseInputEvent e){
        File file = PaintGridPersistence.chooseExportFile();
        if(file != null){
            PaintGridPersistence.trySaveAsPNG(imageQuery.getImage(), file);
        }
    }

}
