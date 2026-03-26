package com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel;

import com.mason.libgui.utils.structures.states.onOff.OnOffState;
import com.mason.libgui.utils.structures.states.position.PositionState;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;
import com.mason.mapgen.paint.skeletons.PaintToolQuerySlot;
import com.mason.mapgen.paint.skeletons.UpdaterSlot;

import java.util.function.Supplier;

public class PaintControlSettingsSkeleton implements BrushSettingsSkeleton{


    private IntState brushNumState;
    private int numBrushes = -1;

    private IntState alphaState;

    private PositionState centrePositionState;
    private PositionState certaintyPositionState;

    private IntState sizeState;

    private OnOffState channelIndependenceState;

    private RGBState primaryRGBState;
    private RGBState secondaryRGBState;
    private RGBQuery averageRGBQuery;

    private ColorMixer colorMixer;
    private PaintToolKit paintToolKit;

    private PaintToolQuerySlot paintToolQuerySlot;
    private UpdaterSlot colorMixerUpdateSlot;
    private final UpdaterSlot brushColorDisplayUpdateSlot = new UpdaterSlot();

    private OnOffState colorPickerState;



    public PaintControlSettingsSkeleton(){}


    @Override
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

    @Override
    public IntState getAlphaState(){
        if(alphaState == null){
            throw new IllegalStateException("alphaState is not set");
        }
        return alphaState;
    }

    public void setAlphaState(IntState alphaState){
        if(this.alphaState != null){
            throw new IllegalStateException("alphaState is already set");
        }
        this.alphaState = alphaState;
    }

    @Override
    public PositionState getCentrePositionState(){
        if(centrePositionState == null){
            throw new IllegalStateException("centreState is not set");
        }
        return centrePositionState;
    }

    public void setCentrePositionState(PositionState centrePositionState){
        if(this.centrePositionState != null){
            throw new IllegalStateException("centreState is already set");
        }
        this.centrePositionState = centrePositionState;
    }

    @Override
    public PositionState getCertaintyPositionState(){
        if(certaintyPositionState == null){
            throw new IllegalStateException("certaintyState is not set");
        }
        return certaintyPositionState;
    }

    public void setCertaintyPositionState(PositionState certaintyPositionState){
        if(this.certaintyPositionState != null){
            throw new IllegalStateException("certaintyState is already set");
        }
        this.certaintyPositionState = certaintyPositionState;
    }

    @Override
    public IntState getSizeState(){
        if(sizeState == null){
            throw new IllegalStateException("sizeState is not set");
        }
        return sizeState;
    }

    public void setSizeState(IntState sizeState){
        if(this.sizeState != null){
            throw new IllegalStateException("sizeState is already set");
        }
        this.sizeState = sizeState;
    }

    @Override
    public OnOffState getChannelIndependenceState(){
        if(channelIndependenceState == null){
            throw new IllegalStateException("channelIndependenceState is not set");
        }
        return channelIndependenceState;
    }

    public void setChannelIndependenceState(OnOffState channelIndependenceState){
        if(this.channelIndependenceState != null){
            throw new IllegalStateException("channelIndependenceState is already set");
        }
        this.channelIndependenceState = channelIndependenceState;
    }

    public int getNumBrushes(){
        if(numBrushes <= 0){
            throw new IllegalStateException("numBrushes unset");
        }
        return numBrushes;
    }

    public void setNumBrushes(int numBrushes){
        if(this.numBrushes > 0){
            throw new IllegalStateException("numBrushes already set!");
        }
        this.numBrushes = numBrushes;
    }

    public RGBState getPrimaryRGBState(){
        if(primaryRGBState == null){
            throw new IllegalStateException("primaryColorState is not set");
        }
        return primaryRGBState;
    }

    public void setPrimaryRGBState(RGBState primaryRGBState){
        if(this.primaryRGBState != null){
            throw new IllegalStateException("primaryColorState is already set");
        }
        this.primaryRGBState = primaryRGBState;
    }

    public RGBState getSecondaryRGBState(){
        if(secondaryRGBState == null){
            throw new IllegalStateException("secondaryColorState is not set");
        }
        return secondaryRGBState;
    }

    public void setSecondaryRGBState(RGBState secondaryRGBState){
        if(this.secondaryRGBState != null){
            throw new IllegalStateException("secondaryColorState is already set");
        }
        this.secondaryRGBState = secondaryRGBState;
    }

    @Override
    public RGBQuery getAverageRGBQuery(){
        if(averageRGBQuery == null){
            throw new IllegalStateException("averageRGBQuery is not set");
        }
        return averageRGBQuery;
    }

    public void setAverageRGBQuery(RGBQuery averageRGBQuery){
        if(this.averageRGBQuery != null){
            throw new IllegalStateException("averageRGBQuery is already set");
        }
        this.averageRGBQuery = averageRGBQuery;
    }

    @Override
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

    @Override
    public ColorMixer getColorMixer(){
        if(colorMixer == null){
            throw new IllegalStateException("randomColorSetting is not set");
        }
        return colorMixer;
    }

    public void setColorMixer(ColorMixer colorMixer){
        if(this.colorMixer != null){
            throw new IllegalStateException("colorMixer is already set");
        }
        this.colorMixer = colorMixer;
    }

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

    public void setColorMixerUpdateSlot(UpdaterSlot colorMixerUpdateSlot){
        if(this.colorMixerUpdateSlot != null){
            throw new IllegalStateException("colorMixerUpdateSlot is already set");
        }
        this.colorMixerUpdateSlot = colorMixerUpdateSlot;
    }

    public void setColorMixerUpdate(Runnable colorMixerUpdater){
        if(this.colorMixerUpdateSlot == null){
            throw new IllegalStateException("colorMixerUpdateSlot is unset");
        }
        this.colorMixerUpdateSlot.setUpdate(colorMixerUpdater);
    }

    public Runnable getBrushColorDisplayUpdate(){
        return brushColorDisplayUpdateSlot;
    }

    public UpdaterSlot getBrushColorDisplayUpdaterSlot(){
        return brushColorDisplayUpdateSlot;
    }

    public Supplier<PaintTool> getPaintToolQuery(){
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

    public void setCurrentPaintToolQuery(Supplier<PaintTool> paintToolQuery){
        if(paintToolQuerySlot == null){
            throw new IllegalStateException("paintToolQuerySlot is not set");
        }
        paintToolQuerySlot.setQuery(paintToolQuery);
    }

}
