package com.mason.mapgen.gui;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.guiManagement.GUI;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.mapgen.gui.states.NullGUIState;

import java.awt.event.KeyListener;

public class MapGenGUI{


    private final GUI gui;
    private GUIState state;


    public MapGenGUI(Size size, String title, String appIconFilepath){
        gui = GUI.buildSimpleGUIWithAppIcon(size, title, appIconFilepath);
        state = new NullGUIState();
    }


    public void start(){
        gui.start();
    }

    public void terminate(){
        gui.terminate();
    }


    public void switchState(GUIState newState){
        removeFromGUI(state);
        addToGUI(newState);
        state.tearDown();
        newState.setUp(state);
        state = newState;
    }

    private void removeFromGUI(GUIState oldState){
        for(UIComponent component : oldState.getComponentsInOrder()){
            gui.removeComponent(component);
        }
        for(KeyListener listener : oldState.getKeyListeners()){
            gui.removeKeyListener(listener);
        }
        for(BoundedMouseInputListener listener : oldState.getMouseInputListeners()){
            gui.removeMouseInputListener(listener);
        }
    }

    private void addToGUI(GUIState newState){
        for(UIComponent component : newState.getComponentsInOrder()){
            gui.addComponent(component);
        }
        for(KeyListener listener : newState.getKeyListeners()){
            gui.addKeyListener(listener);
        }
        for(BoundedMouseInputListener listener : newState.getMouseInputListeners()){
            gui.addMouseInputListener(listener);
        }
    }

}
