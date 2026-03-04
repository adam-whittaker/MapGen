package com.mason.mapgen.paint.logic;

import com.mason.libgui.components.toggles.Toggle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaintKeyProcessor implements KeyListener{


    private Toggle brushToggle;
    private Toggle colorPickerToggle;
    private boolean active;


    public PaintKeyProcessor(){}


    public void registerToggles(Toggle brushToggle, Toggle colorPickerToggle){
        this.brushToggle = brushToggle;
        this.colorPickerToggle = colorPickerToggle;
    }


    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        verifyTogglesExist();
        if(!active){
            active = true;
            colorPickerToggle.select();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        verifyTogglesExist();
        active = false;
        brushToggle.select();
    }

    private void verifyTogglesExist(){
        if(brushToggle == null || colorPickerToggle == null){
            throw new IllegalStateException("toggles unset!");
        }
    }

}
