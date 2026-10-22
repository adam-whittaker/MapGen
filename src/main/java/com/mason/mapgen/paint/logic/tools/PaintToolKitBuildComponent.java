package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.PRIMARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.SECONDARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSlidersBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageAccessSlotsBuildComponent.COLOR_SETTER_SLOT;

public class PaintToolKitBuildComponent extends ComponentBuildResource<PaintToolKit>{


    public static final PaintToolKitBuildComponent INSTANCE = new PaintToolKitBuildComponent();
    public static final SettableBuildResource<ColorMixer> COLOR_MIXER = new SettableBuildResource<>("COLOR_MIXER");


    private PaintToolKitBuildComponent(){}


    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                INSTANCE,
                COLOR_MIXER
        );
    }

    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PAINT_TOOL_QUERY_SETTER,
                BRUSH_SIZE_QUERY,
                COLOR_PICKER_STATE,
                PRIMARY_COLOR_STATE,
                SECONDARY_COLOR_STATE,
                BRUSH_CENTRE_QUERY,
                BRUSH_CERTAINTY_QUERY,
                CHANNEL_INDEPENDENCE_STATE,
                BRUSH_ALPHA_QUERY,
                COLOR_MIXER_UPDATE_SETTER,
                COLOR_SETTER_SLOT
        );
    }

    @Override
    protected PaintToolKit build(){
        PaintToolKitSkeleton skeleton = new PaintToolKitSkeleton();

        skeleton.setPaintToolQuerySetter(PAINT_TOOL_QUERY_SETTER.get());
        skeleton.setBrushSizeQuery(BRUSH_SIZE_QUERY.get());
        skeleton.setColorPickerState(COLOR_PICKER_STATE.get());
        skeleton.setPrimaryColorState(PRIMARY_COLOR_STATE.get());
        skeleton.setSecondaryColorState(SECONDARY_COLOR_STATE.get());
        skeleton.setCentreQuery(BRUSH_CENTRE_QUERY.get());
        skeleton.setCertaintyQuery(BRUSH_CERTAINTY_QUERY.get());
        skeleton.setChannelIndependenceQuery(CHANNEL_INDEPENDENCE_STATE.get());
        skeleton.setBrushAlphaQuery(BRUSH_ALPHA_QUERY.get());
        skeleton.setColorMixerUpdateSetter(COLOR_MIXER_UPDATE_SETTER.get());
        skeleton.setColorSetterSlot(COLOR_SETTER_SLOT.get());

        ColorMixer colorMixer = new ColorMixer(skeleton);
        skeleton.setColorMixer(colorMixer);

        COLOR_MIXER.setResource(colorMixer);

        return new PaintToolKit(skeleton);
    }


    @Override
    public String toString(){
        return "PAINT_TOOL_KIT";
    }

}
