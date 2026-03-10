package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.*;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;

import java.util.function.Consumer;

public class BrushCentreSlider extends PresetSliderWithIcons{


    private final Consumer<Double> centreSetter;


    private BrushCentreSlider(SliderWithIconsSkeleton skeleton, Consumer<Double> centreSetter){
        super(skeleton);
        this.centreSetter = centreSetter;
    }

    public BrushCentreSlider build(Size size, int handleWidth, Consumer<Double> centreSetter){
        SliderWithIconsSkeleton skeleton = SliderWithIcons.buildWithBasicDeco("BRUSH_CENTRE_SLIDER",
                new HitboxRect(new Coord(0, 0), size),
                handleWidth,
                "assets/paintIcons/primarySelected.png",
                "assets/paintIcons/secondarySelected.png");
        BrushCentreSlider slider = new BrushCentreSlider(skeleton, centreSetter);
        slider.setPosition(0);
        slider.dragIncrementEvent();
        return slider;
    }


    @Override
    public void sliderGrabbed(SliderEvent event){}

    @Override
    public void sliderDragged(SliderEvent event){
        centreSetter.accept(event.getPosition());
    }

    @Override
    public void sliderReleased(SliderEvent event){}

}
