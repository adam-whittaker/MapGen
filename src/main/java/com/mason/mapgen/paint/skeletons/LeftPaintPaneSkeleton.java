package com.mason.mapgen.paint.skeletons;

import com.mason.libgui.components.panes.PaneSkeleton;
import com.mason.libgui.components.toggles.ToggleGroup;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;

public class LeftPaintPaneSkeleton extends PaneSkeleton{


    private ToggleGroup toggleGroup;
    private PaintToolKit paintToolKit;


    public LeftPaintPaneSkeleton(){}


    public ToggleGroup getToggleGroup(){
        if(toggleGroup == null){
            throw new IllegalStateException("Component uninitialized");
        }
        return toggleGroup;
    }

    public void setToggleGroup(ToggleGroup toggleGroup){
        this.toggleGroup = toggleGroup;
    }

    public PaintToolKit getPaintToolKit(){
        if(paintToolKit == null){
            throw new IllegalStateException("Component uninitialized");
        }
        return paintToolKit;
    }

    public void setPaintToolKit(PaintToolKit paintToolKit){
        this.paintToolKit = paintToolKit;
    }

}
