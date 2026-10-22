package com.mason.mapgen.paint.logic.keyProcessor;

import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.onOff.OnOffState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PaintKeyProcessor implements KeyListener{


    private final OnOffState colorPickerState;
    private final OnOffState grabReferenceOnlyState;
    private final IntState brushNumState;

    private final Map<Character, Consumer<KeyEvent>> typedMap = new HashMap<>();
    private final Map<Integer, Consumer<KeyEvent>> pressedMap = new HashMap<>();
    private final Map<Integer, Consumer<KeyEvent>> releasedMap = new HashMap<>();


    public PaintKeyProcessor(OnOffState colorPickerState,
                             OnOffState grabReferenceOnlyState,
                             IntState brushNumState,
                             int numBrushes){
        this.colorPickerState = colorPickerState;
        this.grabReferenceOnlyState = grabReferenceOnlyState;
        this.brushNumState = brushNumState;
        mapTypedKeys(numBrushes);
        mapPressedKeys();
        mapReleasedKeys();
    }

    private void mapTypedKeys(int numBrushes){
        for(int n=1; n<=numBrushes; n++){
            int finalN = n;
            typedMap.put((char) ('0'+n), e -> brushNumState.setState(finalN -1));
        }
    }

    private void mapPressedKeys(){
        pressedMap.put(KeyEvent.VK_CONTROL, e -> colorPickerState.turnOn());
        pressedMap.put(KeyEvent.VK_SHIFT, e -> grabReferenceOnlyState.turnOn());
    }

    private void mapReleasedKeys(){
        releasedMap.put(KeyEvent.VK_CONTROL, e -> colorPickerState.turnOff());
        releasedMap.put(KeyEvent.VK_SHIFT, e -> grabReferenceOnlyState.turnOff());
    }


    @Override
    public void keyTyped(KeyEvent e){
        Consumer<KeyEvent> action = typedMap.getOrDefault(e.getKeyChar(), event -> {});
        action.accept(e);
    }

    @Override
    public void keyPressed(KeyEvent e){
        processKeyCodeEvent(e, pressedMap);
    }

    @Override
    public void keyReleased(KeyEvent e){
        processKeyCodeEvent(e, releasedMap);
    }

    private void processKeyCodeEvent(KeyEvent e, Map<Integer, Consumer<KeyEvent>> map){
        Consumer<KeyEvent> action = map.getOrDefault(e.getKeyCode(), event -> {});
        action.accept(e);
    }

}
