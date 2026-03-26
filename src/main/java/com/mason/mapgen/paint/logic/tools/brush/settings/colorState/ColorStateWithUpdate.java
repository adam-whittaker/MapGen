package com.mason.mapgen.paint.logic.tools.brush.settings.colorState;

import com.mason.libgui.utils.structures.states.intState.IntState;

import java.awt.*;

public class ColorStateWithUpdate implements RGBState{


    private final IntState red;
    private final IntState green;
    private final IntState blue;
    private final Runnable updateNotifier;


    public ColorStateWithUpdate(IntState red, IntState green, IntState blue, Runnable updateNotifier){
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

}
