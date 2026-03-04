package com.mason.mapgen.paint.skeletons;

import com.mason.libgui.components.panes.PaneSkeleton;
import com.mason.libgui.components.toggles.ExclusiveToggleGroup;
import com.mason.mapgen.paint.builders.PaneLayout;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;

import java.util.function.Consumer;

public class LeftPaintPaneSkeleton extends PaneSkeleton{


    private ExclusiveToggleGroup toggleGroup;
    private PaintToolKit paintToolKit;
    private PaintKeyProcessor paintKeyProcessor;
    private PaneLayout paneLayout;
    private Consumer<Float> brightnessSetter;


    public LeftPaintPaneSkeleton(){}


    public ExclusiveToggleGroup getToggleGroup(){
        if(toggleGroup == null){
            throw new IllegalStateException("toggleGroup is not set");
        }
        return toggleGroup;
    }

    public void setToggleGroup(ExclusiveToggleGroup toggleGroup){
        if(this.toggleGroup != null){
            throw new IllegalStateException("toggleGroup is already set");
        }
        this.toggleGroup = toggleGroup;
    }

    public PaintToolKit getPaintToolKit(){
        if(paintToolKit == null){
            throw new IllegalStateException("paintToolKit is not set");
        }
        return paintToolKit;
    }

    public void setPaintToolKit(PaintToolKit paintToolKit){
        if(this.paintToolKit != null){
            throw new IllegalStateException("paintToolKit is already set");
        }
        this.paintToolKit = paintToolKit;
    }

    public PaintKeyProcessor getPaintKeyProcessor(){
        if(paintKeyProcessor == null){
            throw new IllegalStateException("paintKeyProcessor is not set");
        }
        return paintKeyProcessor;
    }

    public void setPaintKeyProcessor(PaintKeyProcessor paintKeyProcessor){
        if(this.paintKeyProcessor != null){
            throw new IllegalStateException("paintKeyProcessor is already set");
        }
        this.paintKeyProcessor = paintKeyProcessor;
    }

    public PaneLayout getPaneLayout(){
        if(paneLayout == null){
            throw new IllegalStateException("paneLayout is not set");
        }
        return paneLayout;
    }

    public void setPaneLayout(PaneLayout paneLayout){
        if(this.paneLayout != null){
            throw new IllegalStateException("paneLayout is already set");
        }
        this.paneLayout = paneLayout;
    }

    public Consumer<Float> getBrightnessSetter(){
        if(brightnessSetter == null){
            throw new IllegalStateException("brightnessSetter is not set");
        }
        return brightnessSetter;
    }

    public void setBrightnessSetter(Consumer<Float> brightnessSetter){
        if(this.brightnessSetter != null){
            throw new IllegalStateException("brightnessSetter is already set");
        }
        this.brightnessSetter = brightnessSetter;
    }

}
