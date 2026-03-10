package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.*;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.function.Consumer;

public class BrushCertaintySlider extends PresetSliderWithIcons{


    private final Consumer<Double> certaintySetter;


    private BrushCertaintySlider(SliderWithIconsSkeleton skeleton, Consumer<Double> certaintySetter){
        super(skeleton);
        this.certaintySetter = certaintySetter;
    }

    public BrushCertaintySlider build(Size size, int handleWidth, Consumer<Double> certaintySetter){
        SliderWithIconsSkeleton skeleton = SliderWithIcons.buildWithBasicDeco("BRUSH_CERTAINTY_SLIDER",
                new HitboxRect(new Coord(0, 0), size),
                handleWidth,
                "assets/paintIcons/uncertain.png",
                "assets/paintIcons/certain.png");
        BrushCertaintySlider slider = new BrushCertaintySlider(skeleton, certaintySetter);
        slider.setPosition(1);
        slider.dragIncrementEvent();
        return slider;
    }


    @Override
    public void sliderGrabbed(SliderEvent event){}

    @Override
    public void sliderDragged(SliderEvent event){
        certaintySetter.accept(event.getPosition());
    }

    @Override
    public void sliderReleased(SliderEvent event){}

}
