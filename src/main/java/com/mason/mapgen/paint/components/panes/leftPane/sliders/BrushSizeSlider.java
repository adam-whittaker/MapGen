package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.*;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.function.Consumer;

public class BrushSizeSlider extends PresetSliderWithIcons{


    private final Consumer<Integer> sizeSetter;


    private BrushSizeSlider(SliderWithIconsSkeleton skeleton, Consumer<Integer> sizeSetter){
        super(skeleton);
        this.sizeSetter = sizeSetter;
    }

    public BrushSizeSlider build(Size size, int handleWidth, Consumer<Integer> sizeSetter){
        SliderWithIconsSkeleton skeleton = SliderWithIcons.buildWithBasicDeco("BRUSH_SIZE_SLIDER",
                new HitboxRect(new Coord(0, 0), size),
                handleWidth,
                "assets/paintIcons/tinyBrush.png",
                "assets/paintIcons/largeBrush.png");
        BrushSizeSlider slider = new BrushSizeSlider(skeleton, sizeSetter);
        slider.setPosition(0.2);
        slider.dragIncrementEvent();
        return slider;
    }


    @Override
    public void sliderGrabbed(SliderEvent event){}

    @Override
    public void sliderDragged(SliderEvent event){
        sizeSetter.accept((int)(event.getPosition() * 255D));
    }

    @Override
    public void sliderReleased(SliderEvent event){}

}
