package com.mason.mapgen.paint.components.panes.leftPane.sliders;


import com.mason.libgui.components.deco.BasicSliderDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.sliders.Slider;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIcons;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsBuilder;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsSkeleton;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;

public class BrushSliderBuilder extends SliderWithIconsBuilder{


    private final BrushSliderPositionStateFactories stateFactories;


    public BrushSliderBuilder(LeftPaintPaneSkeleton skeleton){
        super(6, new BasicSliderDeco(), new Size(12, 24));
        stateFactories = new BrushSliderPositionStateFactories(skeleton, sliderHandleSize.width());
    }


    private RectQuery getBoundary(LeftPaintPaneSkeleton skeleton, String address){
        PaneLayout layout = skeleton.getPaneLayout();
        return layout.getBounds(address);
    }


    public Slider buildAlphaSlider(LeftPaintPaneSkeleton paneSkeleton){
        String name = "BRUSH_ALPHA_SLIDER";
        RectQuery boundary = getBoundary(paneSkeleton, name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                name,
                boundary,
                "assets/paintIcons/transparent.png",
                "assets/paintIcons/opaque.png",
                stateFactories::buildAlphaState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildCentreSlider(LeftPaintPaneSkeleton paneSkeleton){
        String name = "BRUSH_CENTRE_SLIDER";
        RectQuery boundary = getBoundary(paneSkeleton, name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                name,
                boundary,
                "assets/paintIcons/primarySelected.png",
                "assets/paintIcons/secondarySelected.png",
                stateFactories::buildCentreState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildCertaintySlider(LeftPaintPaneSkeleton paneSkeleton){
        String name = "BRUSH_CERTAINTY_SLIDER";
        RectQuery boundary = getBoundary(paneSkeleton, name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                name,
                boundary,
                "assets/paintIcons/uncertain.png",
                "assets/paintIcons/certain.png",
                stateFactories::buildCertaintyState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildSizeSlider(LeftPaintPaneSkeleton paneSkeleton){
        String name = "BRUSH_SIZE_SLIDER";
        RectQuery boundary = getBoundary(paneSkeleton, name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                name,
                boundary,
                "assets/paintIcons/tinyBrush.png",
                "assets/paintIcons/largeBrush.png",
                stateFactories::buildSizeState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildBrightnessSlider(LeftPaintPaneSkeleton paneSkeleton){
        String name = "BRUSH_BRIGHTNESS_SLIDER";
        RectQuery boundary = getBoundary(paneSkeleton, name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                name,
                boundary,
                "assets/paintIcons/dark.png",
                "assets/paintIcons/bright.png",
                stateFactories::buildBrightnessState);
        return new SliderWithIcons(skeleton);
    }

}
