package com.mason.mapgen.paint.logic.tools;

import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.logic.tools.brush.settings.ColorMixer;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;
import com.mason.libstruct.setters.ConsumerSetter;
import com.mason.libstruct.setters.QuerySetter;
import com.mason.libstruct.setters.UpdateSetter;

public class PaintToolKitSkeleton{


    private QuerySetter<PaintTool> paintToolQuerySetter;
    private IntQuery brushSizeQuery;
    private OnOffQuery setColorQuery;
    private ColorMixer colorMixer;
    private OnOffQuery colorPickerQuery;
    private OnOffQuery eraserQuery;
    private RGBState primaryColorState;
    private RGBState secondaryColorState;
    private ConsumerSetter<ColorPicker.ColorSetterInput> colorSetterSlot;

    private PositionQuery centreQuery;
    private PositionQuery certaintyQuery;
    private OnOffQuery channelIndependenceQuery;
    private IntQuery brushAlphaQuery;
    private UpdateSetter colorMixerUpdateSetter;

    private IntQuery currentLayerQuery;


    public PaintToolKitSkeleton(){}


    public QuerySetter<PaintTool> getPaintToolQuerySetter(){
        if(paintToolQuerySetter == null){
            throw new IllegalStateException("paintToolQuerySetter is not set");
        }
        return paintToolQuerySetter;
    }

    public void setPaintToolQuerySetter(QuerySetter<PaintTool> paintToolQuerySetter){
        if(this.paintToolQuerySetter != null){
            throw new IllegalStateException("paintToolQuerySetter is already set");
        }
        this.paintToolQuerySetter = paintToolQuerySetter;
    }

    public IntQuery getBrushSizeQuery(){
        if(brushSizeQuery == null){
            throw new IllegalStateException("sizeQuery is not set");
        }
        return brushSizeQuery;
    }

    public void setBrushSizeQuery(IntQuery sizeQuery){
        if(this.brushSizeQuery != null){
            throw new IllegalStateException("sizeQuery is already set");
        }
        this.brushSizeQuery = sizeQuery;
    }

    public OnOffQuery getSetColorQuery(){
        if(setColorQuery == null){
            throw new IllegalStateException("setColorQuery is not set");
        }
        return setColorQuery;
    }

    public void setSetColorQuery(OnOffQuery setColorQuery){
        if(this.setColorQuery != null){
            throw new IllegalStateException("setColorQuery is already set");
        }
        this.setColorQuery = setColorQuery;
    }

    public ColorMixer getColorMixer(){
        if(colorMixer == null){
            throw new IllegalStateException("colorMixer is not set");
        }
        return colorMixer;
    }

    public void setColorMixer(ColorMixer colorMixer){
        if(this.colorMixer != null){
            throw new IllegalStateException("colorMixer is already set");
        }
        this.colorMixer = colorMixer;
    }

    public OnOffQuery getColorPickerQuery(){
        if(colorPickerQuery == null){
            throw new IllegalStateException("colorPickerQuery is not set");
        }
        return colorPickerQuery;
    }

    public void setColorPickerQuery(OnOffQuery colorPickerQuery){
        if(this.colorPickerQuery != null){
            throw new IllegalStateException("colorPickerQuery is already set");
        }
        this.colorPickerQuery = colorPickerQuery;
    }

    public OnOffQuery getEraserQuery(){
        if(eraserQuery == null){
            throw new IllegalStateException("eraserQuery is not set");
        }
        return eraserQuery;
    }

    public void setEraserQuery(OnOffQuery eraserQuery){
        if(this.eraserQuery != null){
            throw new IllegalStateException("eraserQuery is already set");
        }
        this.eraserQuery = eraserQuery;
    }

    public RGBState getPrimaryColorState(){
        if(primaryColorState == null){
            throw new IllegalStateException("primaryColorState is not set");
        }
        return primaryColorState;
    }

    public void setPrimaryColorState(RGBState primaryColorState){
        if(this.primaryColorState != null){
            throw new IllegalStateException("primaryColorState is already set");
        }
        this.primaryColorState = primaryColorState;
    }

    public RGBState getSecondaryColorState(){
        if(secondaryColorState == null){
            throw new IllegalStateException("secondaryColorState is not set");
        }
        return secondaryColorState;
    }

    public void setSecondaryColorState(RGBState secondaryColorState){
        if(this.secondaryColorState != null){
            throw new IllegalStateException("secondaryColorState is already set");
        }
        this.secondaryColorState = secondaryColorState;
    }

    public PositionQuery getCentreQuery(){
        if(centreQuery == null){
            throw new IllegalStateException("centreQuery is not set");
        }
        return centreQuery;
    }

    public void setCentreQuery(PositionQuery centreQuery){
        if(this.centreQuery != null){
            throw new IllegalStateException("centreQuery is already set");
        }
        this.centreQuery = centreQuery;
    }

    public PositionQuery getCertaintyQuery(){
        if(certaintyQuery == null){
            throw new IllegalStateException("certaintyQuery is not set");
        }
        return certaintyQuery;
    }

    public void setCertaintyQuery(PositionQuery certaintyQuery){
        if(this.certaintyQuery != null){
            throw new IllegalStateException("certaintyQuery is already set");
        }
        this.certaintyQuery = certaintyQuery;
    }

    public OnOffQuery getChannelIndependenceQuery(){
        if(channelIndependenceQuery == null){
            throw new IllegalStateException("channelIndependenceQuery is not set");
        }
        return channelIndependenceQuery;
    }

    public void setChannelIndependenceQuery(OnOffQuery channelIndependenceQuery){
        if(this.channelIndependenceQuery != null){
            throw new IllegalStateException("channelIndependenceQuery is already set");
        }
        this.channelIndependenceQuery = channelIndependenceQuery;
    }

    public IntQuery getBrushAlphaQuery(){
        if(brushAlphaQuery == null){
            throw new IllegalStateException("brushAlphaQuery is not set");
        }
        return brushAlphaQuery;
    }

    public void setBrushAlphaQuery(IntQuery brushAlphaQuery){
        if(this.brushAlphaQuery != null){
            throw new IllegalStateException("brushAlphaQuery is already set");
        }
        this.brushAlphaQuery = brushAlphaQuery;
    }

    public UpdateSetter getColorMixerUpdateSetter(){
        if(colorMixerUpdateSetter == null){
            throw new IllegalStateException("colorMixerUpdateSetter is not set");
        }
        return colorMixerUpdateSetter;
    }

    public void setColorMixerUpdateSetter(UpdateSetter colorMixerUpdateSetter){
        if(this.colorMixerUpdateSetter != null){
            throw new IllegalStateException("colorMixerUpdateSetter is already set");
        }
        this.colorMixerUpdateSetter = colorMixerUpdateSetter;
    }

    public ConsumerSetter<ColorPicker.ColorSetterInput> getColorSetterSlot(){
        if(colorSetterSlot == null){
            throw new IllegalStateException("colorSetterSlot is not set");
        }
        return colorSetterSlot;
    }

    public void setColorSetterSlot(ConsumerSetter<ColorPicker.ColorSetterInput> colorSetterSlot){
        if(this.colorSetterSlot != null){
            throw new IllegalStateException("colorSetterSlot is already set");
        }
        this.colorSetterSlot = colorSetterSlot;
    }

    public IntQuery getCurrentLayerQuery(){
        if(currentLayerQuery == null){
            throw new IllegalStateException("currentLayerQuery is not set");
        }
        return currentLayerQuery;
    }

    public void setCurrentLayerQuery(IntQuery currentLayerQuery){
        if(this.currentLayerQuery != null){
            throw new IllegalStateException("currentLayerQuery is already set");
        }
        this.currentLayerQuery = currentLayerQuery;
    }

}
