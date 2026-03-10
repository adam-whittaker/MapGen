package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.*;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.function.Consumer;

public class BrushBrightnessSlider extends PresetSliderWithIcons{


    private final Consumer<Double> brightnessSetter;


    private BrushBrightnessSlider(SliderWithIconsSkeleton skeleton, Consumer<Double> brightnessSetter){
        super(skeleton);
        this.brightnessSetter = brightnessSetter;
    }

    public BrushBrightnessSlider build(Size size, int handleWidth, Consumer<Double> brightnessSetter){
        SliderWithIconsSkeleton skeleton = SliderWithIcons.buildWithBasicDeco("BRUSH_BRIGHTNESS_SLIDER",
                new HitboxRect(new Coord(0, 0), size),
                handleWidth,
                "assets/paintIcons/dark.png",
                "assets/paintIcons/bright.png");
        BrushBrightnessSlider slider = new BrushBrightnessSlider(skeleton, brightnessSetter);
        slider.setPosition(0.5);
        slider.dragIncrementEvent();
        return slider;
    }


    @Override
    public void sliderGrabbed(SliderEvent event){}

    @Override
    public void sliderDragged(SliderEvent event){
        brightnessSetter.accept(event.getPosition());
    }

    @Override
    public void sliderReleased(SliderEvent event){}

}
