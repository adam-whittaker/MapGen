package com.mason.mapgen.paint.logic.history;

import com.mason.mapgen.paint.logic.canvas.PaintCanvas;

import java.util.ArrayList;
import java.util.List;

public class PaintHistory{


    private final PaintCanvas canvas;
    private final int maxLength;
    private final List<PaintAction> history;
    private int previousActionIndex;


    public PaintHistory(PaintCanvas canvas, int maxLength){
        this.canvas = canvas;
        this.maxLength = maxLength;
        history = new ArrayList<>();
        previousActionIndex = -1;
    }


    public void registerAction(PaintAction action){
        if(action.isEmpty()){
            return;
        }
        clearFutureActions();
        history.add(action);
        previousActionIndex++;
        trimToSize();
    }

    private void clearFutureActions(){
        history.subList(previousActionIndex + 1, history.size()).clear();
    }

    private void trimToSize(){
        while(history.size() > maxLength){
            history.remove(0);
            previousActionIndex--;
        }
    }


    public void undo(){
        if(previousActionIndex >= 0){
            PaintAction previousAction = history.get(previousActionIndex);
            previousAction.undo(canvas);
            previousActionIndex--;
        }
    }

    public void redo(){
        if(history.size()-1 > previousActionIndex){
            previousActionIndex++;
            PaintAction nextAction = history.get(previousActionIndex);
            nextAction.apply(canvas);
        }
    }

}
