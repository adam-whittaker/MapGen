package com.mason.mapgen.paint.components.panes.rightPane.referenceButtons;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.rightPane.resources.ReferenceImagePersistence;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class LoadReferenceButton extends AbstractButton{


    private final Consumer<BufferedImage> referenceLoader;


    protected LoadReferenceButton(HitboxRect hitbox, Consumer<BufferedImage> referenceLoader){
        super(hitbox, BasicButtonDeco.build("assets/paintIcons/LOAD_REFERENCE_BUTTON.png"));
        this.referenceLoader = referenceLoader;
    }


    @Override
    public void click(MouseInputEvent e){
        File file = ReferenceImagePersistence.chooseLoadFile();
        if(file != null){
            try{
                referenceLoader.accept(ImageIO.read(file));
            }catch(IOException ex){
                ReferenceImagePersistence.imageReadFailedDialogue();
            }
        }
    }

}
