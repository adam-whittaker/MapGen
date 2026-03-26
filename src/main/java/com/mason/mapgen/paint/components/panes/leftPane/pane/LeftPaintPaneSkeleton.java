package com.mason.mapgen.paint.components.panes.leftPane.pane;

import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.sliders.Slider;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;
import com.mason.mapgen.paint.skeletons.PaintToolQuerySlot;
import com.mason.mapgen.paint.skeletons.UpdaterSlot;

public class LeftPaintPaneSkeleton extends PaneSkeleton{


    private PaintToolQuerySlot paintToolQuerySlot;
    private final UpdaterSlot colorMixerUpdateSlot = new UpdaterSlot();
    private final UpdaterSlot colorSelectorUpdateSlot = new UpdaterSlot();

    private int numBrushes = -1;
    private IntState brushNumState;

    private Slider alphaSlider;
    private Slider centreSlider;
    private Slider certaintySlider;
    private Slider sizeSlider;
    private Slider brightnessSlider;

    private PaintKeyProcessor paintKeyProcessor;
    private PaneLayout paneLayout;
    private PaintControlSettingsSkeleton paintControlSettingsSkeleton;

    private Size toggleSize;


    public LeftPaintPaneSkeleton(){}


    public Size getToggleSize(){
        if(toggleSize == null){
            throw new IllegalStateException("toggleSize is not set");
        }
        return toggleSize;
    }

    public void setToggleSize(Size toggleSize){
        if(this.toggleSize != null){
            throw new IllegalStateException("toggleSize is already set");
        }
        this.toggleSize = toggleSize;
    }

    public PaintControlSettingsSkeleton getPaintControlSettingsSkeleton(){
        if(paintControlSettingsSkeleton == null){
            throw new IllegalStateException("paintControlSettingsSkeleton is not set");
        }
        return paintControlSettingsSkeleton;
    }

    public void setPaintControlSettingsSkeleton(PaintControlSettingsSkeleton paintControlSettingsSkeleton){
        if(this.paintControlSettingsSkeleton != null){
            throw new IllegalStateException("paintControlSettingsSkeleton is already set");
        }
        this.paintControlSettingsSkeleton = paintControlSettingsSkeleton;
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

    public PaintToolQuerySlot getPaintToolQuerySlot(){
        if(paintToolQuerySlot == null){
            throw new IllegalStateException("paintToolQuerySlot is not set");
        }
        return paintToolQuerySlot;
    }

    public void setPaintToolQuerySlot(PaintToolQuerySlot paintToolQuerySlot){
        if(this.paintToolQuerySlot != null){
            throw new IllegalStateException("paintToolQuerySlot is already set");
        }
        this.paintToolQuerySlot = paintToolQuerySlot;
    }

    public UpdaterSlot getColorMixerUpdateSlot(){
        return colorMixerUpdateSlot;
    }

    public Runnable getColorMixerUpdate(){
        return colorMixerUpdateSlot;
    }

    public void setColorSelectorUpdate(Runnable update){
        colorSelectorUpdateSlot.setUpdate(update);
    }

    public Runnable getColorSelectorUpdate(){
        return colorSelectorUpdateSlot;
    }

    public int getNumBrushes(){
        if(numBrushes <= 0){
            throw new IllegalStateException("numBrushes unset");
        }
        return numBrushes;
    }

    public void setNumBrushes(int numBrushes){
        if(this.numBrushes > 0){
            throw new IllegalStateException("numBrushes already set");
        }
        this.numBrushes = numBrushes;
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

    public Slider getAlphaSlider(){
        if(alphaSlider == null){
            throw new IllegalStateException("alphaSlider is not set");
        }
        return alphaSlider;
    }

    public void setAlphaSlider(Slider alphaSlider){
        if(this.alphaSlider != null){
            throw new IllegalStateException("alphaSlider is already set");
        }
        this.alphaSlider = alphaSlider;
    }

    public Slider getCentreSlider(){
        if(centreSlider == null){
            throw new IllegalStateException("centreSlider is not set");
        }
        return centreSlider;
    }

    public void setCentreSlider(Slider centreSlider){
        if(this.centreSlider != null){
            throw new IllegalStateException("centreSlider is already set");
        }
        this.centreSlider = centreSlider;
    }

    public Slider getCertaintySlider(){
        if(certaintySlider == null){
            throw new IllegalStateException("certaintySlider is not set");
        }
        return certaintySlider;
    }

    public void setCertaintySlider(Slider certaintySlider){
        if(this.certaintySlider != null){
            throw new IllegalStateException("certaintySlider is already set");
        }
        this.certaintySlider = certaintySlider;
    }

    public Slider getSizeSlider(){
        if(sizeSlider == null){
            throw new IllegalStateException("sizeSlider is not set");
        }
        return sizeSlider;
    }

    public void setSizeSlider(Slider sizeSlider){
        if(this.sizeSlider != null){
            throw new IllegalStateException("sizeSlider is already set");
        }
        this.sizeSlider = sizeSlider;
    }

    public Slider getBrightnessSlider(){
        if(brightnessSlider == null){
            throw new IllegalStateException("brightnessSlider is not set");
        }
        return brightnessSlider;
    }

    public void setBrightnessSlider(Slider brightnessSlider){
        if(this.brightnessSlider != null){
            throw new IllegalStateException("brightnessSlider is already set");
        }
        this.brightnessSlider = brightnessSlider;
    }

}
