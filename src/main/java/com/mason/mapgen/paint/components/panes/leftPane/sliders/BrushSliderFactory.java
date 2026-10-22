package com.mason.mapgen.paint.components.panes.leftPane.sliders;


import com.mason.libgui.components.deco.BasicSliderDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.sliders.Slider;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIcons;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsBuilder;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsSkeleton;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.intState.IntState;

public class BrushSliderFactory extends SliderWithIconsBuilder{


    private final BrushSliderPositionStateFactories stateFactories;

    
    BrushSliderFactory(IntState brushNumState,
                       int numBrushes,
                       Runnable colorMixerUpdate,
                       Runnable averageBrushDisplayUpdate,
                       Runnable colorSelectorUpdate){
        super(6, new BasicSliderDeco(), new Size(12, 24));
        stateFactories = new BrushSliderPositionStateFactories(
                brushNumState,
                numBrushes,
                colorMixerUpdate,
                averageBrushDisplayUpdate,
                colorSelectorUpdate, 
                getSliderHandleSize().width());
    }


    public Slider buildAlphaSlider(PaneLayout layout){
        String name = "BRUSH_ALPHA_SLIDER";
        RectQuery boundary = layout.getBounds(name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                boundary,
                "assets/paintIcons/transparent.png",
                "assets/paintIcons/opaque.png",
                stateFactories::buildAlphaState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildCentreSlider(PaneLayout layout){
        String name = "BRUSH_CENTRE_SLIDER";
        RectQuery boundary = layout.getBounds(name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                boundary,
                "assets/paintIcons/primarySelected.png",
                "assets/paintIcons/secondarySelected.png",
                stateFactories::buildCentreState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildCertaintySlider(PaneLayout layout){
        String name = "BRUSH_CERTAINTY_SLIDER";
        RectQuery boundary = layout.getBounds(name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                boundary,
                "assets/paintIcons/uncertain.png",
                "assets/paintIcons/certain.png",
                stateFactories::buildCertaintyState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildSizeSlider(PaneLayout layout){
        String name = "BRUSH_SIZE_SLIDER";
        RectQuery boundary = layout.getBounds(name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                boundary,
                "assets/paintIcons/tinyBrush.png",
                "assets/paintIcons/largeBrush.png",
                stateFactories::buildSizeState);
        return new SliderWithIcons(skeleton);
    }

    public Slider buildBrightnessSlider(PaneLayout layout){
        String name = "BRUSH_BRIGHTNESS_SLIDER";
        RectQuery boundary = layout.getBounds(name);
        SliderWithIconsSkeleton skeleton = buildSkeleton(
                boundary,
                "assets/paintIcons/dark.png",
                "assets/paintIcons/bright.png",
                stateFactories::buildBrightnessState);
        return new SliderWithIcons(skeleton);
    }

}
