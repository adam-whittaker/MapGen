package com.mason.mapgen.paint.logic;

import com.mason.mapgen.paint.logic.tools.PaintTool;

public class PaintManager{


    private PaintTool currentTool;


    protected PaintManager(){}


    public void setCurrentTool(PaintTool tool){
        currentTool = tool;
    }

    public PaintTool getCurrentTool(){
        return currentTool;
    }

}
