package com.mason.mapgen.paint.logic.keyProcessor;

import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.onOff.OnOffState;

public class PaintKeyProcessorSkeleton{


    private OnOffState colorPickerState;
    private OnOffState grabReferenceOnlyState;
    private IntState brushNumState;
    private int numBrushes;
    private Runnable undoCallable;
    private Runnable redoCallable;


    public PaintKeyProcessorSkeleton(){}


    public OnOffState getColorPickerState(){
        if(colorPickerState == null){
            throw new IllegalStateException("colorPickerState is not set");
        }
        return colorPickerState;
    }

    public void setColorPickerState(OnOffState colorPickerState){
        if(this.colorPickerState != null){
            throw new IllegalStateException("colorPickerState is already set");
        }
        this.colorPickerState = colorPickerState;
    }

    public OnOffState getGrabReferenceOnlyState(){
        if(grabReferenceOnlyState == null){
            throw new IllegalStateException("grabReferenceOnlyState is not set");
        }
        return grabReferenceOnlyState;
    }

    public void setGrabReferenceOnlyState(OnOffState grabReferenceOnlyState){
        if(this.grabReferenceOnlyState != null){
            throw new IllegalStateException("grabReferenceOnlyState is already set");
        }
        this.grabReferenceOnlyState = grabReferenceOnlyState;
    }

    public IntState getBrushNumState(){
        if(brushNumState == null){
            throw new IllegalStateException("brushNumState is not set");
        }
        return brushNumState;
    }

    public void setBrushNumState(IntState brushNumState){
        if(this.brushNumState != null){
            throw new IllegalStateException("brushNumState is already set");
        }
        this.brushNumState = brushNumState;
    }

    public int getNumBrushes(){
        return numBrushes;
    }

    public void setNumBrushes(int numBrushes){
        this.numBrushes = numBrushes;
    }

    public Runnable getUndoCallable(){
        if(undoCallable == null){
            throw new IllegalStateException("undoCallable is not set");
        }
        return undoCallable;
    }

    public void setUndoCallable(Runnable undoCallable){
        if(this.undoCallable != null){
            throw new IllegalStateException("undoCallable is already set");
        }
        this.undoCallable = undoCallable;
    }

    public Runnable getRedoCallable(){
        if(redoCallable == null){
            throw new IllegalStateException("redoCallable is not set");
        }
        return redoCallable;
    }

    public void setRedoCallable(Runnable redoCallable){
        if(this.redoCallable != null){
            throw new IllegalStateException("redoCallable is already set");
        }
        this.redoCallable = redoCallable;
    }

}
