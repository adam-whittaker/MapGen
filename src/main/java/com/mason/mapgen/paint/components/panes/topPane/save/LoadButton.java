package com.mason.mapgen.paint.components.panes.topPane.save;

import com.mason.libgui.components.buttons.AbstractButton;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneSkeleton;
import com.mason.mapgen.paint.components.panes.topPane.resources.PaintGridReceiver;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.logic.fileIO.PaintGridPersistence;
import com.mason.mapgen.structures.enums.DialogOption;

import java.io.File;

public class LoadButton extends AbstractButton{


    private final SaveLocation saveLocation;
    private final SaveButton saveButton;
    private final PaintGridReceiver gridReceiver;


    protected LoadButton(TopPaintPaneSkeleton skeleton, String name, HitboxRect boundary, ButtonDeco buttonDeco){
        super(name, boundary, buttonDeco);
        saveLocation = skeleton.getSaveLocation();
        saveButton = skeleton.getSaveButton();
        gridReceiver = skeleton.getGridReceiver();
    }

    public static LoadButton build(TopPaintPaneSkeleton skeleton){
        String name = "LOAD_BUTTON";
        HitboxRect boundary = ButtonBuilder.getBoundary(skeleton, name);
        ButtonDeco deco = ButtonBuilder.buildDeco(name);
        return new LoadButton(skeleton, name, boundary, deco);
    }


    @Override
    public void click(MouseInputEvent e){
        if(!checkSaveFirst(e)){
            return;
        }
        File file = PaintGridPersistence.chooseLoadFile();
        if(file != null){
            saveLocation.setFile(file);
            gridReceiver.receiveGrid(PaintGridPersistence.tryReadFromFile(file));
        }
    }

    private boolean checkSaveFirst(MouseInputEvent e){
        DialogOption option = PaintGridPersistence.shouldSaveFirst();
        if(option.equals(DialogOption.YES)){
            saveButton.click(e);
            return true;
        }else return option.equals(DialogOption.NO);
    }

}
