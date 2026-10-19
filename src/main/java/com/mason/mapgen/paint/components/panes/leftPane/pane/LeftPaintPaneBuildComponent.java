package com.mason.mapgen.paint.components.panes.leftPane.pane;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay.BrushColorDisplayBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.colorSelector.ColorSelectorBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas.PaletteCanvasController;
import com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas.PaletteCanvasControllerBuildComponent;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSlidersBuildComponent.*;
import static com.mason.mapgen.paint.components.panes.leftPane.toggles.LeftPaintPaneTogglesBuildComponent.*;

public class LeftPaintPaneBuildComponent extends ComponentBuildResource<LeftPaintPane>{


    public static final LeftPaintPaneBuildComponent INSTANCE = new LeftPaintPaneBuildComponent();
    public static final SettableBuildResource<PaneLayout> LAYOUT = new SettableBuildResource<>("LEFT_PAINT_PANE_LAYOUT");


    private LeftPaintPaneBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(PaintGUIStateLayoutBuildComponent.INSTANCE);
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                this,
                LAYOUT
        );
    }

    @Override
    protected LeftPaintPane build(){
        HitboxRect boundary = buildPaneBoundary();
        LAYOUT.setResource(new LeftPaintPaneLayout(boundary, 24));
        return LeftPaintPane.buildEmptyPane(boundary);
    }

    private HitboxRect buildPaneBoundary(){
        PaneLayout layout = PaintGUIStateLayoutBuildComponent.INSTANCE.get();
        return HitboxRect.fromRect(layout.getBounds("LEFT_PAINT_PANE"));
    }


    @Override
    public void postBuild(){
        Pane pane = this.get();

        for(Toggle brushToggle : BRUSH_TOGGLES.get()) pane.addComponent(brushToggle);
        pane.addComponent(BRUSH_CHANNEL_INDEPENDENCE_TOGGLE.get());
        pane.addComponent(COLOR_PICKER_TOGGLE.get());

        pane.addComponent(BrushColorDisplayBuildComponent.INSTANCE.get());

        pane.addComponent(BRUSH_ALPHA_SLIDER.get());
        pane.addComponent(BRUSH_CERTAINTY_SLIDER.get());
        pane.addComponent(BRUSH_CENTRE_SLIDER.get());
        pane.addComponent(BRUSH_SIZE_SLIDER.get());
        pane.addComponent(BRUSH_BRIGHTNESS_SLIDER.get());

        pane.addComponent(ColorSelectorBuildComponent.INSTANCE.get());

        PaletteCanvasController paletteCanvasController = PaletteCanvasControllerBuildComponent.INSTANCE.get();
        paletteCanvasController.addToContainer(pane);
    }


    @Override
    public String toString(){
        return "LEFT_PAINT_PANE";
    }

}
