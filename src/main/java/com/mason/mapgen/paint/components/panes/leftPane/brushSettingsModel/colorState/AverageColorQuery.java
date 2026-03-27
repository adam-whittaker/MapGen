package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState;

import com.mason.libgui.utils.structures.states.position.PositionQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;

import static com.mason.mapgen.core.Utils.lerp;

public class AverageColorQuery implements RGBQuery{


    private final RGBQuery primary;
    private final RGBQuery secondary;
    private final PositionQuery centre;


    public AverageColorQuery(RGBQuery primary, RGBQuery secondary, PositionQuery centre){
        this.primary = primary;
        this.secondary = secondary;
        this.centre = centre;
    }


    @Override
    public int getRed(){
        return lerp(primary.getRed(), secondary.getRed(), centre.getPosition());
    }

    @Override
    public int getGreen(){
        return lerp(primary.getGreen(), secondary.getGreen(), centre.getPosition());
    }

    @Override
    public int getBlue(){
        return lerp(primary.getBlue(), secondary.getBlue(), centre.getPosition());
    }

}
