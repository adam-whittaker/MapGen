package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.sliders.Slider;
import com.mason.libgui.construction.*;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.intState.PositionTo8BitIntStateAdapter;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneBuildComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.*;

public class BrushSlidersBuildComponent implements BuildComponent{


    public static final BrushSlidersBuildComponent INSTANCE = new BrushSlidersBuildComponent();

    public static final SettableBuildResource<List<UIComponent>> BRUSH_SLIDERS = new SettableBuildResource<>("BRUSH_SLIDERS");

    public static final SettableBuildResource<IntQuery> BRUSH_ALPHA_QUERY = new SettableBuildResource<>("BRUSH_ALPHA_QUERY");
    public static final SettableBuildResource<PositionQuery> BRUSH_CERTAINTY_QUERY = new SettableBuildResource<>("BRUSH_CERTAINTY_QUERY");
    public static final SettableBuildResource<PositionQuery> BRUSH_CENTRE_QUERY = new SettableBuildResource<>("BRUSH_CENTRE_QUERY");
    public static final SettableBuildResource<IntQuery> BRUSH_SIZE_QUERY = new SettableBuildResource<>("BRUSH_SIZE_QUERY");
    public static final SettableBuildResource<PositionQuery> BRUSH_BRIGHTNESS_QUERY = new SettableBuildResource<>("BRUSH_BRIGHTNESS_QUERY");


    private BrushSlidersBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                LeftPaintPaneBuildComponent.LAYOUT,
                BRUSH_NUM_STATE,
                NUM_BRUSHES,
                COLOR_MIXER_UPDATE,
                AVERAGE_BRUSH_DISPLAY_UPDATE,
                COLOR_SELECTOR_UPDATE);
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                BRUSH_SLIDERS,
                BRUSH_ALPHA_QUERY,
                BRUSH_CERTAINTY_QUERY,
                BRUSH_CENTRE_QUERY,
                BRUSH_SIZE_QUERY,
                BRUSH_BRIGHTNESS_QUERY
        );
    }

    @Override
    public void ensureBuilt(){
        PaneLayout layout = LeftPaintPaneBuildComponent.LAYOUT.get();
        BrushSliderFactory factory = new BrushSliderFactory(
                BRUSH_NUM_STATE.get(),
                NUM_BRUSHES.get(),
                COLOR_MIXER_UPDATE.get(),
                AVERAGE_BRUSH_DISPLAY_UPDATE.get(),
                COLOR_SELECTOR_UPDATE.get()
        );
        List<UIComponent> sliders = new ArrayList<>();
        BRUSH_SLIDERS.setResource(sliders);

        Slider alphaSlider = factory.buildAlphaSlider(layout);
        sliders.add(alphaSlider);
        BRUSH_ALPHA_QUERY.setResource(new PositionTo8BitIntStateAdapter(alphaSlider));

        Slider certaintySlider = factory.buildCertaintySlider(layout);
        sliders.add(certaintySlider);
        BRUSH_CERTAINTY_QUERY.setResource(certaintySlider);

        Slider centreSlider = factory.buildCentreSlider(layout);
        sliders.add(centreSlider);
        BRUSH_CENTRE_QUERY.setResource(centreSlider);

        Slider sizeSlider = factory.buildSizeSlider(layout);
        sliders.add(sizeSlider);
        BRUSH_SIZE_QUERY.setResource(new PositionTo8BitIntStateAdapter(sizeSlider));

        Slider brightnessSlider = factory.buildBrightnessSlider(layout);
        sliders.add(brightnessSlider);
        BRUSH_BRIGHTNESS_QUERY.setResource(brightnessSlider);
    }


    @Override
    public String toString(){
        return "BRUSH_SLIDERS";
    }

}
