package com.mason.mapgen.gui.states;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;

import java.awt.event.KeyListener;


public abstract class GUIState{


    private final Iterable<UIComponent> components;
    private final Iterable<KeyListener> keyListeners;
    private final Iterable<BoundedMouseInputListener> mouseListeners;


    public GUIState(GUIStateSkeleton skeleton){
        components = skeleton.getComponentsInOrder();
        keyListeners = skeleton.getKeyListeners();
        mouseListeners = skeleton.getMouseInputListeners();
    }


    public Iterable<UIComponent> getComponentsInOrder(){
        return components;
    }

    public Iterable<KeyListener> getKeyListeners(){
        return keyListeners;
    }

    public Iterable<BoundedMouseInputListener> getMouseInputListeners(){
        return mouseListeners;
    }

    public abstract void setUp(GUIState previousState);

    public abstract void tearDown();

}
