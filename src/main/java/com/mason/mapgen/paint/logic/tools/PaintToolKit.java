package com.mason.mapgen.paint.logic.tools;

import com.mason.libgui.components.ComponentIDRegister;
import com.mason.libgui.components.toggles.ToggleEvent;
import com.mason.libgui.components.toggles.ToggleEventListener;
import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import com.mason.mapgen.paint.logic.tools.brush.BrushColor;
import com.mason.mapgen.paint.logic.tools.brush.BrushTool;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

import java.util.function.Consumer;

public class PaintToolKit implements ToggleEventListener{


    private final BrushColor brushColor;
    private final BrushTool brush;
    private final ColorPicker colorPicker;
    private final Consumer<PaintTool> setPaintTool;


    public PaintToolKit(BrushColorDisplay brushColorDisplay, Consumer<PaintTool> setPaintTool){
        brushColor = new BrushColor(brushColorDisplay);
        this.brush = new BrushTool(brushColor);
        this.colorPicker = new ColorPicker(brushColor);
        this.setPaintTool = setPaintTool;
        setPaintTool.accept(brush);
    }


    @Override
    public void toggleSelected(ToggleEvent event){
        int eventID = event.getToggleID();
        if(eventID == ComponentIDRegister.getID("BRUSH_TOGGLE")){
            setPaintTool.accept(brush);
        }else if(eventID == ComponentIDRegister.getID("COLOR_PICKER_TOGGLE")){
            setPaintTool.accept(colorPicker);
        }else if(eventID == ComponentIDRegister.getID("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE")){
            brushColor.setChannelIndependence(true);
        }else{
            throw new IllegalArgumentException("Invalid toggle ID: " + eventID);
        }
    }

    @Override
    public void toggleUnselected(ToggleEvent event){
        int eventID = event.getToggleID();
        if(eventID == ComponentIDRegister.getID("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE")){
            brushColor.setChannelIndependence(false);
        }
    }

}
