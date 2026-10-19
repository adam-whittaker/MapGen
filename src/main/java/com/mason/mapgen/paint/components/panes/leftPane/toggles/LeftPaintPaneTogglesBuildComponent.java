package com.mason.mapgen.paint.components.panes.leftPane.toggles;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.components.toggles.sourced.IntegerToggleGroup;
import com.mason.libgui.components.toggles.sourced.SourcedToggle;
import com.mason.libgui.construction.*;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneBuildComponent;

import java.util.Set;
import java.util.function.Function;

import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.PRIMARY_COLOR_STATE;
import static com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent.SECONDARY_COLOR_STATE;

public class LeftPaintPaneTogglesBuildComponent implements BuildComponent{


    public static final LeftPaintPaneTogglesBuildComponent INSTANCE = new LeftPaintPaneTogglesBuildComponent();
    public static final SettableBuildResource<Toggle[]> BRUSH_TOGGLES = new SettableBuildResource<>("BRUSH_TOGGLES");
    public static final SettableBuildResource<Toggle> BRUSH_CHANNEL_INDEPENDENCE_TOGGLE = new SettableBuildResource<>("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE");
    public static final SettableBuildResource<Toggle> COLOR_PICKER_TOGGLE = new SettableBuildResource<>("COLOR_PICKER_TOGGLE");


    private ColorIconBuilder iconBuilder;
    private IntegerToggleGroup toggleGroup;
    private Function<String, HitboxRect> toggleBoundaryBuilder;


    private LeftPaintPaneTogglesBuildComponent(){}


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PRIMARY_COLOR_STATE,
                SECONDARY_COLOR_STATE,
                BRUSH_NUM_STATE,
                BRUSH_COLOR_DISPLAY_UPDATE,
                LeftPaintPaneBuildComponent.LAYOUT,
                NUM_BRUSHES,
                CHANNEL_INDEPENDENCE_STATE,
                COLOR_PICKER_STATE
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                BRUSH_TOGGLES,
                BRUSH_CHANNEL_INDEPENDENCE_TOGGLE,
                COLOR_PICKER_TOGGLE
        );
    }

    @Override
    public void ensureBuilt(){
        iconBuilder = new ColorIconBuilder(PRIMARY_COLOR_STATE.get(), SECONDARY_COLOR_STATE.get());
        toggleGroup = new IntegerToggleGroup(BRUSH_NUM_STATE.get(), BRUSH_COLOR_DISPLAY_UPDATE.get());
        toggleBoundaryBuilder = createToggleBoundaryBuilder();
        BRUSH_TOGGLES.setResource(buildBrushToggles());
        BRUSH_CHANNEL_INDEPENDENCE_TOGGLE.setResource(buildChannelIndependenceToggle());
        COLOR_PICKER_TOGGLE.setResource(buildColorPickerToggle());
    }

    private static Function<String, HitboxRect> createToggleBoundaryBuilder(){
        Size toggleSize = new Size(36, 36);
        PaneLayout layout = LeftPaintPaneBuildComponent.LAYOUT.get();
        return (brushName) -> {
            Coord coord = layout.centre(brushName, toggleSize);
            return new BasicHitboxRect(coord, toggleSize);
        };
    }

    private Toggle[] buildBrushToggles(){
        Toggle[] toggles = new Toggle[NUM_BRUSHES.get()];
        for(int brushNum=0; brushNum<toggles.length; brushNum++){
            toggles[brushNum] = constructBrushToggle(brushNum);
        }
        return toggles;
    }

    private Toggle constructBrushToggle(int brushNum){
        String brushName = "BRUSH_" + brushNum + "_TOGGLE";
        HitboxRect boundary = toggleBoundaryBuilder.apply(brushName);
        ButtonDeco deco = createBrushToggleDeco(boundary, brushNum);
        return toggleGroup.createToggle(brushName, boundary, deco, brushNum);
    }

    private BrushButtonDeco createBrushToggleDeco(RectQuery bounds, int brushNum){
        ColorIcon[] icons = iconBuilder.buildIconPair(bounds, brushNum);
        return BrushButtonDeco.buildBrushButtonDeco("assets/paintIcons/brush.png", icons);
    }


    private Toggle buildChannelIndependenceToggle(){
        String name = "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE";
        return new SourcedToggle(
                name,
                toggleBoundaryBuilder.apply(name),
                BasicButtonDeco.build("assets/paintIcons/channelIndependence.png"),
                CHANNEL_INDEPENDENCE_STATE.get()
        );
    }


    private Toggle buildColorPickerToggle(){
        String name = "COLOR_PICKER_TOGGLE";
        return new SourcedToggle(
                name,
                toggleBoundaryBuilder.apply(name),
                BasicButtonDeco.build("assets/paintIcons/colorPicker.png"),
                COLOR_PICKER_STATE.get()
        );
    }


    @Override
    public String toString(){
        return "LEFT_PAINT_PANE_TOGGLES";
    }

}
