package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel;

import com.mason.libgui.utils.structures.states.onOff.OnOffState;
import com.mason.libgui.utils.structures.states.position.PositionState;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.AverageColorQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorChannelIntState;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorState;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.libgui.utils.structures.states.intState.PositionTo8BitIntStateAdapter;

public class PaintControlSettingsBuilder{


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


    public static PaintControlSettingsSkeleton buildSkeletonWithSliderBacking(LeftPaintPaneSkeleton paneSkeleton){
        PaintControlSettingsSkeleton skeleton = buildSkeletonWithInitialFields(paneSkeleton);
        initializeColorPickerState(skeleton);
        initializeChannelIndependenceState(skeleton);
        initializeColorStates(skeleton);
        initializeColorMixer(skeleton);
        initializePaintToolKit(skeleton);
        return skeleton;
    }

    private static PaintControlSettingsSkeleton buildSkeletonWithInitialFields(LeftPaintPaneSkeleton paneSkeleton){
        PaintControlSettingsSkeleton skeleton = new PaintControlSettingsSkeleton();
        skeleton.setColorMixerUpdateSlot(paneSkeleton.getColorMixerUpdateSlot());
        skeleton.setBrushColorDisplayUpdate(paneSkeleton.getBrushColorDisplayUpdate());
        skeleton.setBrushNumState(paneSkeleton.getBrushNumState());
        skeleton.setNumBrushes(paneSkeleton.getNumBrushes());
        skeleton.setPaintToolQuerySlot(paneSkeleton.getPaintToolQuerySlot());
        skeleton.setAlphaState(new PositionTo8BitIntStateAdapter(paneSkeleton.getAlphaSlider()));
        skeleton.setCentrePositionState(paneSkeleton.getCentreSlider());
        skeleton.setCertaintyPositionState(paneSkeleton.getCertaintySlider());
        skeleton.setSizeState(new PositionTo8BitIntStateAdapter(paneSkeleton.getSizeSlider()));
        return skeleton;
    }


    private static void initializeColorPickerState(PaintControlSettingsSkeleton skeleton){
        OnOffState universalState = OnOffState.newDefault();
        skeleton.setColorPickerState(universalState);
    }


    private static void initializeChannelIndependenceState(PaintControlSettingsSkeleton skeleton){
        OnOffState state = createOnOffState(skeleton);
        skeleton.setChannelIndependenceState(state);
    }

    private static OnOffState createOnOffState(PaintControlSettingsSkeleton skeleton){
        IntState brushNum = skeleton.getBrushNumState();
        return new OnOffState(){

            final boolean[] on = new boolean[skeleton.getNumBrushes()];

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


    private static void initializeColorStates(PaintControlSettingsSkeleton skeleton){
        ColorState primary = createColorState(skeleton, primaryRed, primaryGreen, primaryBlue);
        ColorState secondary = createColorState(skeleton, secondaryRed, secondaryGreen, secondaryBlue);
        PositionState centre = skeleton.getCentrePositionState();

        skeleton.setPrimaryColorState(primary);
        skeleton.setSecondaryColorState(secondary);
        skeleton.setAverageRGBQuery(new AverageColorQuery(primary, secondary, centre));
    }

    private static void initializeColorMixer(PaintControlSettingsSkeleton skeleton){
        ColorMixer colorMixer = new ColorMixer(skeleton);
        skeleton.setColorMixer(colorMixer);
    }

    private static void initializePaintToolKit(PaintControlSettingsSkeleton skeleton){
        new PaintToolKit(skeleton);
    }


    private static ColorState createColorState(PaintControlSettingsSkeleton skeleton, int[] r, int[] g, int[] b){
        Runnable brushDisplayUpdate = skeleton.getBrushColorDisplayUpdate();
        ColorChannelIntState red =   createColorChannelState(skeleton, r);
        ColorChannelIntState green = createColorChannelState(skeleton, g);
        ColorChannelIntState blue =  createColorChannelState(skeleton, b);
        return new ColorState(red, green, blue, brushDisplayUpdate);
    }

    private static ColorChannelIntState createColorChannelState(PaintControlSettingsSkeleton skeleton, int[] initialValues){
        IntState brushNum = skeleton.getBrushNumState();
        int numBrushes = skeleton.getNumBrushes();
        return new ColorChannelIntState(brushNum, numBrushes, initialValues);
    }

}
