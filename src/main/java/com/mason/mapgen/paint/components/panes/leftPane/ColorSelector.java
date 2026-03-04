package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libgui.utils.structures.Coord;
import com.mason.mapgen.paint.logic.tools.brush.BrushColor;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

import java.awt.*;

public class ColorSelector extends AbstractUIComponent implements BoundedMouseInputListener{


    private final BrushColor brushColor;
    private final ColorSelectorImage colorSelectorImage;
    private float brightness;


    public ColorSelector(HitboxRect hitbox, BrushColor brushColor){
        super(hitbox);
        this.brushColor = brushColor;
        colorSelectorImage = new ColorSelectorImage(hitbox.getSize());
        brightness = 0.5f;
    }


    @Override
    public void render(Graphics2D g){
        colorSelectorImage.render(g, getCoord());
    }

    @Override
    public void tick(){}


    public void setBrightness(float brightness){
        this.brightness = brightness;
        colorSelectorImage.updateImage(this.brightness);
    }


    @Override
    public void onMousePressed(MouseInputEvent e){
        onMouseDragged(e);
    }

    @Override
    public void onMouseDragged(MouseInputEvent e){
        Color color = obtainColor(e.getCoord());
        if(ColorPicker.secondaryColorMask(e)){
            brushColor.setSecondaryColor(color);
        }else{
            brushColor.setPrimaryColor(color);
        }
    }

    private Color obtainColor(Coord coord){
        float[] hsb = colorSelectorImage.getHueAndSaturationAtPosition(coord.x() - x(), coord.y() - y());
        hsb[2] = brightness;
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

}
