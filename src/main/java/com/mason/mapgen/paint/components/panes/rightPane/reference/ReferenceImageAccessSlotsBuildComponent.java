package com.mason.mapgen.paint.components.panes.rightPane.reference;

import com.mason.libgui.construction.BuildComponent;
import com.mason.libgui.construction.BuildResource;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;
import com.mason.mapgen.paint.setters.ConsumerSetter;
import java.util.Set;
import java.util.function.Consumer;

public class ReferenceImageAccessSlotsBuildComponent implements BuildComponent{


    public static final ReferenceImageAccessSlotsBuildComponent INSTANCE = new ReferenceImageAccessSlotsBuildComponent();

    public static final BuildResource<ImageUpdate> REFERENCE_IMAGE_UPDATE;
    public static final BuildResource<ImageUpdate.ImageUpdateSetter> REFERENCE_IMAGE_UPDATE_SETTER;
    public static final BuildResource<Consumer<ColorPicker.ColorSetterInput>> COLOR_SETTER;
    public static final BuildResource<ConsumerSetter<ColorPicker.ColorSetterInput>> COLOR_SETTER_SLOT;
    static{
        ImageUpdate.ImageUpdateSlot updateSlot = new ImageUpdate.ImageUpdateSlot();
        REFERENCE_IMAGE_UPDATE = BuildResource.getPreBuiltResource(updateSlot, "REFERENCE_IMAGE_UPDATE");
        REFERENCE_IMAGE_UPDATE_SETTER = BuildResource.getPreBuiltResource(updateSlot, "REFERENCE_IMAGE_UPDATE_SETTER");

        ConsumerSetter.ConsumerSlot<ColorPicker.ColorSetterInput> colorSetterSlot = new ConsumerSetter.ConsumerSlot<>();
        COLOR_SETTER = BuildResource.getPreBuiltResource(colorSetterSlot, "COLOR_SETTER");
        COLOR_SETTER_SLOT = BuildResource.getPreBuiltResource(colorSetterSlot, "COLOR_SETTER_SLOT");
    }


    private ReferenceImageAccessSlotsBuildComponent(){}


    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                REFERENCE_IMAGE_UPDATE,
                REFERENCE_IMAGE_UPDATE_SETTER,
                COLOR_SETTER,
                COLOR_SETTER_SLOT
        );
    }

    @Override
    public void ensureBuilt(){}


    @Override
    public String toString(){
        return "REFERENCE_IMAGE_ACCESS_SLOTS";
    }

}
