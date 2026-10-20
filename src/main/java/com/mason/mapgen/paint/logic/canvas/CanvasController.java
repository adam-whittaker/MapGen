package com.mason.mapgen.paint.logic.canvas;

import com.mason.libgui.core.componentManagement.InteractiveContainer;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.logic.tools.PaintTool;

import java.util.function.Supplier;

public class CanvasController implements BoundedMouseInputListener{


    private final PaintCanvas canvas;
    private final Supplier<PaintTool> currentToolQuery;


    public CanvasController(PaintCanvas canvas, Supplier<PaintTool> currentToolQuery){
        this.canvas = canvas;
        this.currentToolQuery = currentToolQuery;
    }


    @Override
    public boolean withinBounds(Coord c){
        return canvas.withinBounds(c);
    }

    public Size getSize(){
        return canvas.getSize();
    }

    protected Coord getCoord(){
        return canvas.getCoord();
    }


    @Override
    public void onMouseDragged(MouseInputEvent event){
        onMousePressed(event);
    }

    @Override
    public void onMousePressed(MouseInputEvent event){
        PaintTool currentTool = currentToolQuery.get();
        if(toolAcceptsMouseOrReject(currentTool, event)){
            currentTool.apply(canvas, event);
        }
    }

    private boolean toolAcceptsMouseOrReject(PaintTool currentTool, MouseInputEvent event){
        if(!currentTool.shouldAcceptMouseInput(event)){
            event.reject();
            return false;
        }
        return true;
    }

    @Override
    public void onMouseReleased(MouseInputEvent event){
        PaintTool currentTool = getCurrentPaintTool();
        if(currentTool.isActive()){
            currentTool.releaseTool();
        }
    }


    protected PaintTool getCurrentPaintTool(){
        return currentToolQuery.get();
    }


    public void addToContainer(InteractiveContainer container){
        container.addMouseInputListener(this);
        container.addComponent(canvas.getImageComponent());
    }

}
