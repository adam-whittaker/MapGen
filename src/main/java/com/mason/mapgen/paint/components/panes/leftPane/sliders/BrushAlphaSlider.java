package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.*;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.function.Consumer;

public class BrushAlphaSlider extends PresetSliderWithIcons{


    private final Consumer<Integer> alphaSetter;


    private BrushAlphaSlider(SliderWithIconsSkeleton skeleton, Consumer<Integer> alphaSetter){
        super(skeleton);
        this.alphaSetter = alphaSetter;
    }

    public BrushAlphaSlider build(Size size, int handleWidth, Consumer<Integer> alphaSetter){
        SliderWithIconsSkeleton skeleton = SliderWithIcons.buildWithBasicDeco("BRUSH_ALPHA_SLIDER",
                new HitboxRect(new Coord(0, 0), size),
                handleWidth,
                "assets/paintIcons/transparent.png",
                "assets/paintIcons/opaque.png");
        BrushAlphaSlider slider = new BrushAlphaSlider(skeleton, alphaSetter);
        slider.setPosition(1);
        slider.dragIncrementEvent();
        return slider;
    }


    @Override
    public void sliderGrabbed(SliderEvent event){}

    @Override
    public void sliderDragged(SliderEvent event){
        alphaSetter.accept((int)(event.getPosition() * 255D));
    }

    @Override
    public void sliderReleased(SliderEvent event){}

}
