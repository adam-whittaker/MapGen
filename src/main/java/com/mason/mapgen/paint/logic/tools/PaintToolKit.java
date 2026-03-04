package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.components.sliders.SliderEvent;
import com.mason.libgui.components.sliders.SliderEventListener;
import com.mason.libgui.components.toggles.ToggleEvent;
import com.mason.libgui.components.toggles.ToggleEventListener;
import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.paint.logic.tools.brush.BrushColor;
import com.mason.mapgen.paint.logic.tools.brush.BrushTool;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

import java.util.function.Consumer;

import static com.mason.libgui.components.ComponentIDRegister.getID;

public class PaintToolKit implements ToggleEventListener, SliderEventListener{


    private final BrushColor brushColor;
    private final BrushTool brush;
    private final ColorPicker colorPicker;
    private final Consumer<PaintTool> setPaintTool;
    private final Consumer<Float> brightnessSetter;


    public PaintToolKit(BrushColor brushColor, PaintManager paintManager, Consumer<Float> brightnessSetter){
        this.brushColor = brushColor;
        this.brush = new BrushTool(brushColor);
        this.colorPicker = new ColorPicker(brushColor);
        this.setPaintTool = paintManager::setCurrentTool;
        setPaintTool.accept(brush);
        this.brightnessSetter = brightnessSetter;
    }


    @Override
    public void toggleSelected(ToggleEvent event){
        int eventID = event.getToggleID();
        if(eventID == getID("BRUSH_TOGGLE")){
            setPaintTool.accept(brush);
        }else if(eventID == getID("COLOR_PICKER_TOGGLE")){
            setPaintTool.accept(colorPicker);
        }else if(eventID == getID("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE")){
            brushColor.setChannelIndependence(true);
        }else{
            throw new IllegalArgumentException("Invalid toggle ID: " + eventID);
        }
    }

    @Override
    public void toggleUnselected(ToggleEvent event){
        int eventID = event.getToggleID();
        if(eventID == getID("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE")){
            brushColor.setChannelIndependence(false);
        }
    }

    @Override
    public void sliderGrabbed(SliderEvent event){

    }

    @Override
    public void sliderDragged(SliderEvent event){
        int eventID = event.getSliderID();
        double position = event.getPosition();
        if(eventID == getID("BRUSH_ALPHA_SLIDER")){
            brushColor.setAlpha(to255Int(position));
        }else if(eventID == getID("BRUSH_CENTRE_SLIDER")){
            brushColor.setCentre(position);
        }else if(eventID == getID("BRUSH_CERTAINTY_SLIDER")){
            brushColor.setCertainty(position);
        }else if(eventID == getID("BRUSH_SIZE_SLIDER")){
            brush.setBrushSize(to255Int(position));
        }else if(eventID == getID("BRUSH_BRIGHTNESS_SLIDER")){
            brightnessSetter.accept((float)position);
        }
    }

    private int to255Int(double position){
        return (int)(position * 255D);
    }

    @Override
    public void sliderReleased(SliderEvent event){

    }

}
