package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.sliderPositionState.BasicSliderPositionState;
import com.mason.libgui.components.sliders.sliderPositionState.IntRange;
import com.mason.libgui.utils.structures.interfaces.Movable;
import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.paint.skeletons.UpdaterSlot;

public class SliderPositionStateWithUpdater extends BasicSliderPositionState{


    private final UpdaterSlot updaterSlot = new UpdaterSlot();


    SliderPositionStateWithUpdater(Movable positionCoord, IntRange range){
        super(positionCoord, range);
    }


    public void setUpdater(Runnable update){
        updaterSlot.setUpdate(update);
    }

    @Override
    public void setCoord(Coord coord){
        super.setCoord(coord);
        updaterSlot.run();
    }

}
