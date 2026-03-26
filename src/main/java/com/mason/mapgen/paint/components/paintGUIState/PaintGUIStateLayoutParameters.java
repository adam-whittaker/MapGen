package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libgui.utils.structures.Size;

public class PaintGUIStateLayoutParameters{


    private Size screenSize;
    private double topPaneHeightRatio = -1;
    private double bottomPaneHeightRatio = -1;
    private double leftPaneWidthRatio = -1;
    private double rightPaneWidthRatio = -1;


    public PaintGUIStateLayoutParameters(){

    }


    public Size getScreenSize(){
        if(screenSize == null){
            throw new IllegalStateException("screenSize is not set");
        }
        return screenSize;
    }

    public void setScreenSize(Size screenSize){
        if(this.screenSize != null){
            throw new IllegalStateException("screenSize is already set");
        }
        this.screenSize = screenSize;
    }

    public double getTopPaneHeightRatio(){
        if(this.topPaneHeightRatio < 0){
            throw new IllegalStateException("topPaneHeight not set");
        }
        return topPaneHeightRatio;
    }

    public void setTopPaneHeightRatio(double topPaneHeightRatio){
        this.topPaneHeightRatio = topPaneHeightRatio;
    }

    public double getBottomPaneHeightRatio(){
        if(this.bottomPaneHeightRatio < 0){
            throw new IllegalStateException("bottomPaneHeight not set");
        }
        return bottomPaneHeightRatio;
    }

    public void setBottomPaneHeightRatio(double bottomPaneHeightRatio){
        this.bottomPaneHeightRatio = bottomPaneHeightRatio;
    }

    public double getLeftPaneWidthRatio(){
        if(this.leftPaneWidthRatio < 0){
            throw new IllegalStateException("leftPaneWidth not set");
        }
        return leftPaneWidthRatio;
    }

    public void setLeftPaneWidthRatio(double leftPaneWidthRatio){
        this.leftPaneWidthRatio = leftPaneWidthRatio;
    }

    public double getRightPaneWidthRatio(){
        if(this.rightPaneWidthRatio < 0){
            throw new IllegalStateException("rightPaneWidth not set");
        }
        return rightPaneWidthRatio;
    }

    public void setRightPaneWidthRatio(double rightPaneWidthRatio){
        this.rightPaneWidthRatio = rightPaneWidthRatio;
    }

}
