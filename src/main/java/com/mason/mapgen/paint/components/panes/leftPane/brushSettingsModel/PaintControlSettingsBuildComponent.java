package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel;

import com.mason.libgui.construction.BuildComponent;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.states.intState.BoundedIntState;
import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.onOff.OnOffState;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.setters.QuerySetter;
import com.mason.mapgen.paint.setters.UpdateSetter;

import java.util.Set;
import java.util.function.Supplier;

public class PaintControlSettingsBuildComponent implements BuildComponent{


    public static final PaintControlSettingsBuildComponent INSTANCE = new PaintControlSettingsBuildComponent();

    public static final SettableBuildResource<Integer> NUM_BRUSHES = new SettableBuildResource<>("NUM_BRUSHES");
    public static final SettableBuildResource<IntState> BRUSH_NUM_STATE = new SettableBuildResource<>("BRUSH_NUM_STATE");
    public static final SettableBuildResource<OnOffState> COLOR_PICKER_STATE = new SettableBuildResource<>("COLOR_PICKER_STATE");
    public static final SettableBuildResource<OnOffState> CHANNEL_INDEPENDENCE_STATE = new SettableBuildResource<>("CHANNEL_INDEPENDENCE_STATE");

    public static final BuildResource<QuerySetter<PaintTool>> PAINT_TOOL_QUERY_SETTER;
    public static final BuildResource<Supplier<PaintTool>> PAINT_TOOL_QUERY;
    public static final BuildResource<UpdateSetter> COLOR_MIXER_UPDATE_SETTER;
    public static final BuildResource<Runnable> COLOR_MIXER_UPDATE;
    public static final BuildResource<UpdateSetter> BRUSH_COLOR_DISPLAY_UPDATE_SETTER;
    public static final BuildResource<Runnable> BRUSH_COLOR_DISPLAY_UPDATE;
    public static final BuildResource<UpdateSetter> AVERAGE_BRUSH_DISPLAY_UPDATE_SETTER;
    public static final BuildResource<Runnable> AVERAGE_BRUSH_DISPLAY_UPDATE;
    public static final BuildResource<UpdateSetter> COLOR_SELECTOR_UPDATE_SETTER;
    public static final BuildResource<Runnable> COLOR_SELECTOR_UPDATE;
    static{
        QuerySetter.QuerySlot<PaintTool> querySlot = new QuerySetter.QuerySlot<>();
        PAINT_TOOL_QUERY_SETTER = BuildResource.getPreBuiltResource(querySlot, "PAINT_TOOL_QUERY_SETTER");
        PAINT_TOOL_QUERY = BuildResource.getPreBuiltResource(querySlot, "PAINT_TOOL_QUERY");

        UpdateSetter.UpdateSlot colorMixerSlot = new UpdateSetter.UpdateSlot();
        COLOR_MIXER_UPDATE_SETTER = BuildResource.getPreBuiltResource(colorMixerSlot, "COLOR_MIXER_UPDATE_SETTER");
        COLOR_MIXER_UPDATE = BuildResource.getPreBuiltResource(colorMixerSlot, "COLOR_MIXER");

        UpdateSetter.UpdateSlot brushColorDisplaySlot = new UpdateSetter.UpdateSlot();
        BRUSH_COLOR_DISPLAY_UPDATE_SETTER = BuildResource.getPreBuiltResource(brushColorDisplaySlot, "BRUSH_COLOR_DISPLAY_UPDATE_SETTER");
        BRUSH_COLOR_DISPLAY_UPDATE = BuildResource.getPreBuiltResource(brushColorDisplaySlot, "BRUSH_COLOR_DISPLAY_UPDATE");

        UpdateSetter.UpdateSlot averageDisplayUpdateSlot = new UpdateSetter.UpdateSlot();
        AVERAGE_BRUSH_DISPLAY_UPDATE_SETTER = BuildResource.getPreBuiltResource(averageDisplayUpdateSlot, "AVERAGE_BRUSH_DISPLAY_UPDATE_SETTER");
        AVERAGE_BRUSH_DISPLAY_UPDATE = BuildResource.getPreBuiltResource(averageDisplayUpdateSlot, "AVERAGE_BRUSH_DISPLAY_UPDATE");

        UpdateSetter.UpdateSlot colorSelectorUpdateSlot = new UpdateSetter.UpdateSlot();
        COLOR_SELECTOR_UPDATE_SETTER = BuildResource.getPreBuiltResource(colorSelectorUpdateSlot, "COLOR_SELECTOR_UPDATE_SETTER");
        COLOR_SELECTOR_UPDATE = BuildResource.getPreBuiltResource(colorSelectorUpdateSlot, "COLOR_SELECTOR_UPDATE");
    }


    private PaintControlSettingsBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                NUM_BRUSHES,
                BRUSH_NUM_STATE,
                COLOR_PICKER_STATE,
                CHANNEL_INDEPENDENCE_STATE,
                PAINT_TOOL_QUERY,
                PAINT_TOOL_QUERY_SETTER,
                COLOR_MIXER_UPDATE,
                COLOR_MIXER_UPDATE_SETTER,
                BRUSH_COLOR_DISPLAY_UPDATE,
                BRUSH_COLOR_DISPLAY_UPDATE_SETTER,
                AVERAGE_BRUSH_DISPLAY_UPDATE,
                AVERAGE_BRUSH_DISPLAY_UPDATE_SETTER,
                COLOR_SELECTOR_UPDATE,
                COLOR_SELECTOR_UPDATE_SETTER
        );
    }

    @Override
    public void ensureBuilt(){
        NUM_BRUSHES.setResource(4);
        BRUSH_NUM_STATE.setResource(new BoundedIntState(0, NUM_BRUSHES.get()));
        COLOR_PICKER_STATE.setResource(OnOffState.newDefault());
        CHANNEL_INDEPENDENCE_STATE.setResource(createBrushNumStateBackedOnOffState());
    }

    private OnOffState createBrushNumStateBackedOnOffState(){
        IntState brushNum = BRUSH_NUM_STATE.get();
        int numBrushes = NUM_BRUSHES.get();
        return new OnOffState(){

            final boolean[] on = new boolean[numBrushes];

            @Override
            public void turnOn(){
                on[brushNum.getState()] = true;
            }

            @Override
            public void turnOff(){
                on[brushNum.getState()] = false;
            }

            @Override
            public boolean isOn(){
                return on[brushNum.getState()];
            }
        };
    }


    @Override
    public String toString(){
        return "PAINT_CONTROL_SETTINGS";
    }

}
