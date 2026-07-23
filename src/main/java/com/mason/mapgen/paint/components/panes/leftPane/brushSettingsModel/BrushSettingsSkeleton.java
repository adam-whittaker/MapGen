package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel;

import com.mason.libstruct.states.position.PositionState;
import com.mason.libstruct.states.onOff.OnOffState;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.libstruct.states.intState.IntState;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;

public interface BrushSettingsSkeleton{


    IntState getBrushNumState();
    IntState getAlphaState();
    PositionState getCentrePositionState();
    PositionState getCertaintyPositionState();
    IntState getSizeState();
    OnOffState getChannelIndependenceState();

    RGBState getPrimaryColorState();
    RGBState getSecondaryColorState();
    RGBQuery getAverageRGBQuery();

    ColorMixer getColorMixer();
    PaintToolKit getPaintToolKit();
    void setColorMixerUpdate(Runnable update);

}
