package com.mason.mapgen.paint.skeletons;

import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.panes.*;
import com.mason.mapgen.paint.components.panes.leftPane.LeftPaintPane;
import com.mason.mapgen.paint.logic.PaintManager;

public class PaintGUIStateSkeleton{


    private GUIStateSkeleton guiStateSkeleton;
    private PaintedImagePane paintedImagePane;
    private LeftPaintPane leftPaintPane;
    private RightPaintPane rightPaintPane;
    private TopPaintPane topPaintPane;
    private BottomPaintPane bottomPaintPane;
    private PaintManager paintManager;


    public PaintGUIStateSkeleton(){}


    public GUIStateSkeleton getGuiStateSkeleton(){
        return guiStateSkeleton;
    }

    public void setGuiStateSkeleton(GUIStateSkeleton guiStateSkeleton){
        this.guiStateSkeleton = guiStateSkeleton;
    }


    public PaintedImagePane getPaintedImagePane(){
        return paintedImagePane;
    }

    public void setPaintedImagePane(PaintedImagePane paintedImageComponent){
        this.paintedImagePane = paintedImageComponent;
    }

    public LeftPaintPane getLeftPaintPane(){
        return leftPaintPane;
    }

    public void setLeftPaintPane(LeftPaintPane leftPaintPane){
        this.leftPaintPane = leftPaintPane;
    }

    public RightPaintPane getRightPaintPane(){
        return rightPaintPane;
    }

    public void setRightPaintPane(RightPaintPane rightPaintPane){
        this.rightPaintPane = rightPaintPane;
    }

    public TopPaintPane getTopPaintPane(){
        return topPaintPane;
    }

    public void setTopPaintPane(TopPaintPane topPaintPane){
        this.topPaintPane = topPaintPane;
    }

    public BottomPaintPane getBottomPaintPane(){
        return bottomPaintPane;
    }

    public void setBottomPaintPane(BottomPaintPane bottomPaintPane){
        this.bottomPaintPane = bottomPaintPane;
    }

    public PaintManager getPaintManager(){
        return paintManager;
    }

    public void setPaintManager(PaintManager paintManager){
        this.paintManager = paintManager;
    }

}
