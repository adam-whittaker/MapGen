package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel;

import com.mason.libgui.utils.structures.states.onOff.OnOffState;
import com.mason.libgui.utils.structures.states.position.PositionState;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.libgui.utils.structures.states.intState.PositionTo8BitIntStateAdapter;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.AverageColorQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.ColorStateWithUpdate;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;

import java.util.Arrays;

public class PaintControlSettingsBuilder{


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
        RGBState primary = createRGBState(skeleton, 200, 0, 0);
        RGBState secondary = createRGBState(skeleton, 0, 0, 200);
        PositionState centre = skeleton.getCentrePositionState();

        skeleton.setPrimaryRGBState(primary);
        skeleton.setSecondaryRGBState(secondary);
        skeleton.setAverageRGBQuery(new AverageColorQuery(primary, secondary, centre));
    }

    private static void initializeColorMixer(PaintControlSettingsSkeleton skeleton){
        ColorMixer colorMixer = new ColorMixer(skeleton);
        skeleton.setColorMixer(colorMixer);
    }

    private static void initializePaintToolKit(PaintControlSettingsSkeleton skeleton){
        new PaintToolKit(skeleton);
    }


    private static RGBState createRGBState(PaintControlSettingsSkeleton skeleton, int initialRed, int initialGreen, int initialBlue){
        Runnable brushDisplayUpdate = skeleton.getBrushColorDisplayUpdate();
        IntState red = createColorChannelState(skeleton, initialRed);
        IntState green = createColorChannelState(skeleton, initialGreen);
        IntState blue = createColorChannelState(skeleton, initialBlue);
        return new ColorStateWithUpdate(red, green, blue, brushDisplayUpdate);
    }

    private static IntState createColorChannelState(PaintControlSettingsSkeleton skeleton, int initialValue){
        IntState brushNum = skeleton.getBrushNumState();
        return new IntState(){

            final int[] channel = new int[skeleton.getNumBrushes()];
            {
                Arrays.fill(channel, initialValue);
            }

            @Override
            public int getState(){
                return channel[brushNum.getState()];
            }

            @Override
            public void setState(int state){
                IntState.verifyStateWithinBounds(state, 0, 256);
                channel[brushNum.getState()] = state;
            }

        };
    }

}
