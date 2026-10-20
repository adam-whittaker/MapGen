package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.libstruct.states.onOff.OnOffState;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;
import com.mason.mapgen.paint.skeletons.QuerySetter;
import com.mason.mapgen.paint.skeletons.UpdateSetter;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.PRIMARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.SECONDARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSlidersBuildComponent.*;

public class PaintToolKitBuildComponent extends ComponentBuildResource<PaintToolKit>{


    public static final PaintToolKitBuildComponent INSTANCE = new PaintToolKitBuildComponent();
    public static final SettableBuildResource<ColorMixer> COLOR_MIXER = new SettableBuildResource<>("COLOR_MIXER");


    private PaintToolKitBuildComponent(){}


    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(this,
                COLOR_MIXER);
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
                COLOR_MIXER_UPDATE_SETTER
        );
    }

    @Override
    protected PaintToolKit build(){
        QuerySetter<PaintTool> paintToolQuerySetter = PAINT_TOOL_QUERY_SETTER.get();
        IntQuery sizeQuery = BRUSH_SIZE_QUERY.get();
        OnOffState colorPickerState = COLOR_PICKER_STATE.get();
        RGBState primaryColorState = PRIMARY_COLOR_STATE.get();
        RGBState secondaryColorState = SECONDARY_COLOR_STATE.get();
        PositionQuery centreQuery = BRUSH_CENTRE_QUERY.get();
        PositionQuery certaintyQuery = BRUSH_CERTAINTY_QUERY.get();
        OnOffQuery channelIndependenceQuery = CHANNEL_INDEPENDENCE_STATE.get();
        IntQuery alphaQuery = BRUSH_ALPHA_QUERY.get();
        UpdateSetter colorMixerUpdateSetter = COLOR_MIXER_UPDATE_SETTER.get();

        ColorMixer colorMixer = new ColorMixer(primaryColorState,
                secondaryColorState,
                centreQuery,
                certaintyQuery,
                channelIndependenceQuery,
                alphaQuery,
                colorMixerUpdateSetter);

        COLOR_MIXER.setResource(colorMixer);

        return new PaintToolKit(paintToolQuerySetter,
                sizeQuery,
                colorMixer,
                colorPickerState,
                primaryColorState,
                secondaryColorState);
    }


    @Override
    public String toString(){
        return "PAINT_TOOL_KIT";
    }

}
