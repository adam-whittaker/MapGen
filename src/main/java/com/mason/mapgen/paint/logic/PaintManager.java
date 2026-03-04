package com.mason.mapgen.paint.logic;

import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.logic.tools.brush.BrushTool;
import com.mason.mapgen.paint.skeletons.PaintManagerSkeleton;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaintManager implements BoundedMouseInputListener, KeyListener{


    private final PaintCanvas mainCanvas;
    private final PaintCanvas palette;
    private final PaintKeyProcessor paintKeyProcessor;
    private PaintTool currentTool;


    protected PaintManager(PaintManagerSkeleton skeleton){
        this.mainCanvas = skeleton.getMainCanvas();
        this.palette = skeleton.getPalette();
        this.paintKeyProcessor = skeleton.getPaintKeyProcessor();
    }

    public static PaintManager build(PaintManagerSkeleton skeleton){
        return new PaintManager(skeleton);
    }


    public void setCurrentTool(PaintTool tool){
        currentTool = tool;
    }

    @Override
    public boolean withinBounds(Coord c){
        return true;
    }


    @Override
    public void onMouseDragged(MouseInputEvent event){
        onMousePressed(event);
    }

    private boolean toolAcceptsMouseOrReject(MouseInputEvent event){
        if(!currentTool.acceptMouseInput(event)){
            event.reject();
            return false;
        }
        return true;
    }

    @Override
    public void onMousePressed(MouseInputEvent event){
        onMousePressedOnGivenCanvas(event, mainCanvas);
    }

    private void onMousePressedOnGivenCanvas(MouseInputEvent event, PaintCanvas canvas){
        if(toolAcceptsMouseOrReject(event)){
            currentTool.apply(canvas, event);
        }
    }

    @Override
    public void onMouseReleased(MouseInputEvent event){
        onMouseReleasedOnGivenCanvas(event, mainCanvas);
    }

    private void onMouseReleasedOnGivenCanvas(MouseInputEvent event, PaintCanvas canvas){
        if(currentTool.isActive()){
            currentTool.releaseTool();
        }
    }


    public void onMousePressedOnPalette(MouseInputEvent event){
        if(currentTool instanceof BrushTool brush){
            double certainty = brush.getCertainty();
            brush.setCertainty(1);
            onMousePressedOnGivenCanvas(event, palette);
            brush.setCertainty(certainty);
        }else{
            onMousePressedOnGivenCanvas(event, palette);
        }
    }

    public void onMouseReleasedOnPalette(MouseInputEvent event){
        onMouseReleasedOnGivenCanvas(event, palette);
    }


    @Override
    public void keyTyped(KeyEvent e){
        paintKeyProcessor.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e){
        paintKeyProcessor.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        paintKeyProcessor.keyReleased(e);
    }

}
