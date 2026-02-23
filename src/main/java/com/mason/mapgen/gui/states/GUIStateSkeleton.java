package com.mason.mapgen.gui.states;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.input.componentLayer.GUIInputRegister;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GUIStateSkeleton implements GUIInputRegister<BoundedMouseInputListener>{


    private final List<UIComponent> components;
    private final List<KeyListener> keyListeners;
    private final List<BoundedMouseInputListener> mouseInputListeners;


    public GUIStateSkeleton(){
        components = new ArrayList<>();
        keyListeners = new ArrayList<>();
        mouseInputListeners = new ArrayList<>();
    }


    Iterable<UIComponent> getComponentsInOrder(){
        return components;
    }

    Iterable<KeyListener> getKeyListeners(){
        return keyListeners;
    }

    Iterable<BoundedMouseInputListener> getMouseInputListeners(){
        return mouseInputListeners;
    }

    public void addComponent(UIComponent component){
        components.add(component);
    }

    public void addKeyListener(KeyListener listener){
        keyListeners.add(listener);
    }

    @Override
    public void removeKeyListener(KeyListener listener){
        throw new UnsupportedOperationException("Should not be removing KeyListener from Skeleton.");
    }

    public void addMouseInputListener(BoundedMouseInputListener listener){
        mouseInputListeners.add(listener);
    }

    @Override
    public void removeMouseInputListener(BoundedMouseInputListener listener){
        throw new UnsupportedOperationException("Should not be removing MouseInputListener from Skeleton.");
    }

}
