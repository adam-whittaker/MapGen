package com.mason.mapgen.paint.components.panes.leftPane.colorSelector;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneBuildComponent;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBState;
import com.mason.mapgen.paint.setters.UpdateSetter;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.COLOR_SELECTOR_UPDATE_SETTER;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.PRIMARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.SECONDARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSlidersBuildComponent.BRUSH_BRIGHTNESS_QUERY;

public class ColorSelectorBuildComponent extends ComponentBuildResource<ColorSelector>{


    public static final ColorSelectorBuildComponent INSTANCE = new ColorSelectorBuildComponent();


    private ColorSelectorBuildComponent(){}


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                LeftPaintPaneBuildComponent.LAYOUT,
                PRIMARY_COLOR_STATE,
                SECONDARY_COLOR_STATE,
                BRUSH_BRIGHTNESS_QUERY,
                COLOR_SELECTOR_UPDATE_SETTER
        );
    }

    @Override
    protected ColorSelector build(){
        PaneLayout paneLayout = LeftPaintPaneBuildComponent.LAYOUT.get();
        RectQuery bounds = paneLayout.getBounds("COLOR_SELECTOR");
        RGBState primaryColor = PRIMARY_COLOR_STATE.get();
        RGBState secondaryColor = SECONDARY_COLOR_STATE.get();
        PositionQuery brightness = BRUSH_BRIGHTNESS_QUERY.get();
        UpdateSetter updateSetter = COLOR_SELECTOR_UPDATE_SETTER.get();
        return new ColorSelector(
                bounds,
                primaryColor,
                secondaryColor,
                brightness,
                updateSetter
        );
    }

    @Override
    public String toString(){
        return "COLOR_SELECTOR";
    }

}
