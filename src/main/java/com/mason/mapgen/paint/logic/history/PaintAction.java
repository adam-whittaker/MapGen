package com.mason.mapgen.paint.logic.history;

import com.mason.mapgen.paint.logic.canvas.PaintCanvas;

public interface PaintAction{

    void apply(PaintCanvas canvas);

    void undo(PaintCanvas canvas);

    default boolean isEmpty(){
        return false;
    }


    PaintAction EMPTY_ACTION = new PaintAction(){

        @Override
        public void apply(PaintCanvas canvas){
            throw new IllegalStateException("Should not be applying/undoing empty action!");
        }

        @Override
        public void undo(PaintCanvas canvas){
            throw new IllegalStateException("Should not be applying/undoing empty action!");
        }

        @Override
        public boolean isEmpty(){
            return true;
        }

    };

}
