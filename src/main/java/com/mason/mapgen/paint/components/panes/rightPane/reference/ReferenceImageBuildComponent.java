package com.mason.mapgen.paint.components.panes.rightPane.reference;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.components.panes.imagePane.PaintedImagePaneBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent;
import com.mason.mapgen.paint.logic.canvas.CanvasController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageAccessSlotsBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.rightPane.referenceButtons.ReferenceTweakablesBuildComponent.GRAB_REFERENCE_ONLY_STATE;
import static com.mason.mapgen.paint.components.panes.rightPane.referenceButtons.ReferenceTweakablesBuildComponent.REFERENCE_ALPHA_QUERY;

public class ReferenceImageBuildComponent extends ComponentBuildResource<ReferenceImageComponent>{


    public static final ReferenceImageBuildComponent INSTANCE = new ReferenceImageBuildComponent();


    private ReferenceImageBuildComponent(){}


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                VersionedCanvasControllerBuildComponent.INSTANCE,
                REFERENCE_ALPHA_QUERY,
                PaintedImagePaneBuildComponent.INSTANCE,
                REFERENCE_IMAGE_UPDATE_SETTER,
                COLOR_SETTER,
                GRAB_REFERENCE_ONLY_STATE
        );
    }

    @Override
    protected ReferenceImageComponent build(){
        CanvasController canvasController = VersionedCanvasControllerBuildComponent.INSTANCE.get();
        PositionQuery alphaQuery = REFERENCE_ALPHA_QUERY.get();
        RectQuery clampingRect = Rect.buildRect(new Coord(0, 0), canvasController.getSize());
        OnOffQuery canGrab = GRAB_REFERENCE_ONLY_STATE.get();
        return new ReferenceImageComponent(
                alphaQuery,
                REFERENCE_IMAGE_UPDATE_SETTER.get(),
                clampingRect,
                canGrab,
                COLOR_SETTER.get()
        );
    }


    @Override
    public void postBuild(){
        try{
            BufferedImage testImage = ImageIO.read(new File("references/danaReference.jpg"));
            REFERENCE_IMAGE_UPDATE.get().setImage(testImage);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(){
        return "REFERENCE_IMAGE";
    }

}
