package com.mason.mapgen.paint.logic;

import com.mason.libstruct.states.onOff.OnOffState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaintKeyProcessor implements KeyListener{


    private OnOffState colorPickerState;


    public PaintKeyProcessor(){}


    public void registerToggles(OnOffState colorPickerState){
        this.colorPickerState = colorPickerState;
    }


    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(!shouldAcceptEvent(e)){
            return;
        }
        verifyColorPickerStateSet();
        colorPickerState.turnOn();
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(!shouldAcceptEvent(e)){
            return;
        }
        colorPickerState.turnOff();
    }

    private void verifyColorPickerStateSet(){
        if(colorPickerState == null){
            throw new IllegalStateException("colorPickerState unset!");
        }
    }

    private boolean shouldAcceptEvent(KeyEvent event){
        return event.getKeyCode() == KeyEvent.VK_CONTROL;
    }

}
