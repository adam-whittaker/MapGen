package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState;

import com.mason.libgui.construction.BuildComponent;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSlidersBuildComponent.BRUSH_CENTRE_QUERY;

public class ColorStatesBuildComponent implements BuildComponent{


    public static final ColorStatesBuildComponent INSTANCE = new ColorStatesBuildComponent();

    public static final SettableBuildResource<ColorState> PRIMARY_COLOR_STATE = new SettableBuildResource<>("PRIMARY_COLOR_STATE");
    public static final SettableBuildResource<ColorState> SECONDARY_COLOR_STATE = new SettableBuildResource<>("SECONDARY_COLOR_STATE");
    public static final SettableBuildResource<RGBQuery> AVERAGE_COLOR_QUERY = new SettableBuildResource<>("AVERAGE_COLOR_QUERY");

    private static final int[] primaryRed = {
            47, 142, 242,
            242, 243, 247
    };
    private static final int[] secondaryRed = {
            243, 191, 174,
            169, 199, 215
    };

    private static final int[] primaryGreen = {
            47, 138, 238,
            184, 162, 197
    };
    private static final int[] secondaryGreen = {
            232, 216, 229,
            199, 179, 163
    };

    private static final int[] primaryBlue = {
            51, 134, 232,
            181, 143, 159
    };
    private static final int[] secondaryBlue = {
            161, 184, 216,
            232, 242, 199
    };


    private ColorStatesBuildComponent(){}


    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                PRIMARY_COLOR_STATE,
                SECONDARY_COLOR_STATE,
                AVERAGE_COLOR_QUERY
        );
    }

    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(BRUSH_CENTRE_QUERY,
                BRUSH_COLOR_DISPLAY_UPDATE,
                BRUSH_NUM_STATE,
                NUM_BRUSHES);
    }

    @Override
    public void ensureBuilt(){
        ColorState primary = createColorState(primaryRed, primaryGreen, primaryBlue);
        ColorState secondary = createColorState(secondaryRed, secondaryGreen, secondaryBlue);
        PositionQuery centre = BRUSH_CENTRE_QUERY.get();

        PRIMARY_COLOR_STATE.setResource(primary);
        SECONDARY_COLOR_STATE.setResource(secondary);
        AVERAGE_COLOR_QUERY.setResource(new AverageColorQuery(primary, secondary, centre));
    }

    private ColorState createColorState(int[] r, int[] g, int[] b){
        Runnable brushDisplayUpdate = BRUSH_COLOR_DISPLAY_UPDATE.get();
        ColorChannelIntState red =   createColorChannelState(r);
        ColorChannelIntState green = createColorChannelState(g);
        ColorChannelIntState blue =  createColorChannelState(b);
        return new ColorState(red, green, blue, brushDisplayUpdate);
    }

    private ColorChannelIntState createColorChannelState(int[] initialValues){
        IntState brushNum = BRUSH_NUM_STATE.get();
        int numBrushes = NUM_BRUSHES.get();
        return new ColorChannelIntState(brushNum, numBrushes, initialValues);
    }


    @Override
    public String toString(){
        return "COLOR_STATES";
    }

}
