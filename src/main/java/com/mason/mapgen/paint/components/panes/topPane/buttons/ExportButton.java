package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Supplier;

public class ExportButton extends AbstractButton{


    private final Supplier<BufferedImage> imageQuery;


    protected ExportButton(String name,
                           HitboxRect boundary,
                           ButtonDeco buttonDeco,
                           Supplier<BufferedImage>  imageQuery){
        super(name, boundary, buttonDeco);
        this.imageQuery = imageQuery;
    }


    @Override
    public void click(MouseInputEvent e){
        File file = PaintGridPersistence.chooseExportFile();
        if(file != null){
            PaintGridPersistence.trySaveAsPNG(imageQuery.get(), file);
        }
    }

}
