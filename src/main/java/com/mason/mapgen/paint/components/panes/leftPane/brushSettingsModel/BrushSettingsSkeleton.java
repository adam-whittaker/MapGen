package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel;

import com.mason.libgui.utils.structures.states.position.PositionState;
import com.mason.libgui.utils.structures.states.onOff.OnOffState;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;

public interface BrushSettingsSkeleton{


    IntState getBrushNumState();
    IntState getAlphaState();
    PositionState getCentrePositionState();
    PositionState getCertaintyPositionState();
    IntState getSizeState();
    OnOffState getChannelIndependenceState();

    RGBState getPrimaryRGBState();
    RGBState getSecondaryRGBState();
    RGBQuery getAverageRGBQuery();

    ColorMixer getColorMixer();
    PaintToolKit getPaintToolKit();
    void setColorMixerUpdate(Runnable update);

}
