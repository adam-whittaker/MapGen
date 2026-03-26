package com.mason.mapgen.paint.logic.tools.brush.settings;

import com.mason.libgui.utils.structures.states.intState.IntQuery;
import com.mason.libgui.utils.structures.states.onOff.OnOffQuery;
import com.mason.libgui.utils.structures.states.position.PositionQuery;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.BrushSettingsSkeleton;

import java.awt.*;

public class BrushSettingsModel{


    private final IntQuery brushNumState;
    private final IntQuery alphaState;
    private final PositionQuery centreState;
    private final PositionQuery certaintyState;
    private final IntQuery sizeState;
    private final OnOffQuery channelIndependenceState;

    private final RGBQuery primaryColor;
    private final RGBQuery secondaryColor;

    private final ColorMixer colorMixer;
    private final PaintToolKit paintToolKit;


    public BrushSettingsModel(BrushSettingsSkeleton skeleton){
        brushNumState = skeleton.getBrushNumState();
        alphaState = skeleton.getAlphaState();
        centreState = skeleton.getCentrePositionState();
        certaintyState = skeleton.getCertaintyPositionState();
        sizeState = skeleton.getSizeState();
        channelIndependenceState = skeleton.getChannelIndependenceState();
        primaryColor = skeleton.getPrimaryRGBState();
        secondaryColor = skeleton.getSecondaryRGBState();
        colorMixer = skeleton.getColorMixer();
        paintToolKit = skeleton.getPaintToolKit();
    }


    public int getBrushSize(){
        return sizeState.getState();
    }

    public Color nextRandomColor(){
        return colorMixer.nextRandomColor();
    }

}
