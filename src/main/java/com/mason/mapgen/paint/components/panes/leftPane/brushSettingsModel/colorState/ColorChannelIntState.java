package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.intState.IntState;

public class ColorChannelIntState implements IntState{


    private final int[] channel;
    private final IntQuery brushNum;


    public ColorChannelIntState(IntQuery brushNum, int numBrushes, int[] initialValues){
        channel = new int[numBrushes];
        this.brushNum = brushNum;
        for(int n=0; n<channel.length; n++){
            channel[n] = initialValues[n];
        }
    }


    @Override
    public int getState(){
        return channel[brushNum.getState()];
    }

    @Override
    public void setState(int state){
        IntQuery.verifyStateWithinBounds(state, 0, 256);
        channel[brushNum.getState()] = state;
    }


    IntQuery maskSpecificBrush(int brushNum){
        return () -> channel[brushNum];
    }

}
