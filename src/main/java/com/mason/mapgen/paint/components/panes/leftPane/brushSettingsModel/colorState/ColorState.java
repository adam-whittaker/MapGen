package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;

import java.awt.*;

public class ColorState implements RGBState{


    private final ColorChannelIntState red;
    private final ColorChannelIntState green;
    private final ColorChannelIntState blue;
    private final Runnable updateNotifier;


    public ColorState(ColorChannelIntState red, ColorChannelIntState green, ColorChannelIntState blue, Runnable updateNotifier){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.updateNotifier = updateNotifier;
    }


    @Override
    public void setColor(Color color){
        red.setState(color.getRed());
        green.setState(color.getGreen());
        blue.setState(color.getBlue());
        updateNotifier.run();
    }

    @Override
    public int getRed(){
        return red.getState();
    }

    @Override
    public int getGreen(){
        return green.getState();
    }

    @Override
    public int getBlue(){
        return blue.getState();
    }


    public RGBQuery maskSpecificBrushNum(int brushNum){
        IntQuery redMask = red.maskSpecificBrush(brushNum);
        IntQuery greenMask = green.maskSpecificBrush(brushNum);
        IntQuery blueMask = blue.maskSpecificBrush(brushNum);
        return new RGBQuery(){

            @Override
            public int getRed(){
                return redMask.getState();
            }

            @Override
            public int getGreen(){
                return greenMask.getState();
            }

            @Override
            public int getBlue(){
                return blueMask.getState();
            }
        };
    }

}
