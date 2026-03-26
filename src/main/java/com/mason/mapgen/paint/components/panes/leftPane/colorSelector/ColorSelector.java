package com.mason.mapgen.paint.components.panes.leftPane.colorSelector;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.libgui.utils.structures.states.position.PositionQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.sliders.SliderPositionStateWithUpdater;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

import java.awt.*;

public class ColorSelector extends AbstractUIComponent implements BoundedMouseInputListener{


    private final RGBState primaryColor;
    private final RGBState secondaryColor;
    private final ColorSelectorImage colorSelectorImage;
    private final PositionQuery brightness;


    public ColorSelector(RectQuery bounds, LeftPaintPaneSkeleton skeleton){
        super(HitboxRect.fromRect(bounds));
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        this.primaryColor = settingsSkeleton.getPrimaryRGBState();
        this.secondaryColor = settingsSkeleton.getSecondaryRGBState();
        colorSelectorImage = new ColorSelectorImage(bounds.getSize());
        brightness = skeleton.getBrightnessSlider();
        skeleton.setColorSelectorUpdate(this::updateBrightness);
    }

    private void updateBrightness(){
        colorSelectorImage.updateImage(getBrightness());
    }

    private float getBrightness(){
        return (float) brightness.getPosition();
    }


    @Override
    public void render(Graphics2D g){
        colorSelectorImage.render(g, getCoord());
    }

    @Override
    public void tick(){}


    @Override
    public void onMousePressed(MouseInputEvent e){
        onMouseDragged(e);
    }

    @Override
    public void onMouseDragged(MouseInputEvent e){
        Color color = obtainColorAtCoord(e.getCoord());
        if(ColorPicker.secondaryColorMask(e)){
            secondaryColor.setColor(color);
        }else{
            primaryColor.setColor(color);
        }
    }

    private Color obtainColorAtCoord(Coord coord){
        float[] hsb = colorSelectorImage.getHueAndSaturationAtPosition(coord.x() - x(), coord.y() - y());
        hsb[2] = getBrightness();
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

}
