package com.mason.mapgen.paint.components.panes.leftPane.pane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.libgui.utils.structures.states.intState.BoundedIntState;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuilder;
import com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas.PaletteCanvasController;
import com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay.BrushColorDisplay;
import com.mason.mapgen.paint.components.panes.leftPane.colorSelector.ColorSelector;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSliderBuilder;
import com.mason.mapgen.paint.components.panes.leftPane.toggles.brushChannelIndependenceToggle.BrushChannelIndependenceToggleBuilder;
import com.mason.mapgen.paint.components.panes.leftPane.toggles.brushToggles.BrushTogglesBuilder;
import com.mason.mapgen.paint.components.panes.leftPane.toggles.colorPickerToggle.ColorPickerToggleBuilder;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;

public class LeftPaintPaneBuilder{


    public static LeftPaintPaneSkeleton buildSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        LeftPaintPaneSkeleton skeleton = buildSkeletonWithInitialFieldsFromGUIStateSkeleton(paintGUIStateSkeleton);
        setUpBrushNumState(skeleton);
        setUpPaneLayout(skeleton);
        setUpSliders(skeleton);
        setUpPaintControlSettingsSkeleton(skeleton);
        setUpBrushColorDisplay(skeleton);
        setUpColorSelector(skeleton);
        setUpPalette(skeleton);
        setUpToggles(skeleton);
        return skeleton;
    }

    private static LeftPaintPaneSkeleton buildSkeletonWithInitialFieldsFromGUIStateSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        LeftPaintPaneSkeleton skeleton = new LeftPaintPaneSkeleton();
        skeleton.setBoundary(buildPaneBoundary(paintGUIStateSkeleton));
        skeleton.setPaintKeyProcessor(paintGUIStateSkeleton.getPaintKeyProcessor());
        skeleton.setPaintToolQuerySlot(paintGUIStateSkeleton.getPaintToolQuerySlot());
        return skeleton;
    }

    private static HitboxRect buildPaneBoundary(PaintGUIStateSkeleton paintGUIStateSkeleton){
        PaneLayout layout = paintGUIStateSkeleton.getLayout();
        return HitboxRect.fromRect(layout.getBounds("LEFT_PAINT_PANE"));
    }


    private static void setUpBrushNumState(LeftPaintPaneSkeleton skeleton){
        int numBrushes = 4;
        skeleton.setNumBrushes(numBrushes);
        skeleton.setBrushNumState(new BoundedIntState(0, numBrushes));
    }

    private static void setUpPaneLayout(LeftPaintPaneSkeleton skeleton){
        PaneLayout layout = new LeftPaintPaneLayout(skeleton.getBoundary(), 24);
        skeleton.setPaneLayout(layout);
    }

    private static void setUpSliders(LeftPaintPaneSkeleton skeleton){
        BrushSliderBuilder sliderBuilder = new BrushSliderBuilder(skeleton);
        skeleton.setAlphaSlider(sliderBuilder.buildAlphaSlider(skeleton));
        skeleton.setCertaintySlider(sliderBuilder.buildCertaintySlider(skeleton));
        skeleton.setCentreSlider(sliderBuilder.buildCentreSlider(skeleton));
        skeleton.setSizeSlider(sliderBuilder.buildSizeSlider(skeleton));
        skeleton.setBrightnessSlider(sliderBuilder.buildBrightnessSlider(skeleton));

        skeleton.addComponent(skeleton.getAlphaSlider());
        skeleton.addComponent(skeleton.getCentreSlider());
        skeleton.addComponent(skeleton.getCertaintySlider());
        skeleton.addComponent(skeleton.getSizeSlider());
        skeleton.addComponent(skeleton.getBrightnessSlider());
    }

    private static void setUpPaintControlSettingsSkeleton(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = PaintControlSettingsBuilder.buildSkeletonWithSliderBacking(skeleton);
        skeleton.setPaintControlSettingsSkeleton(settingsSkeleton);
        PaintKeyProcessor paintKeyProcessor = skeleton.getPaintKeyProcessor();
        paintKeyProcessor.registerToggles(settingsSkeleton.getColorPickerState());
    }

    private static void setUpBrushColorDisplay(LeftPaintPaneSkeleton skeleton){
        BrushColorDisplay brushColorDisplay = BrushColorDisplay.build(skeleton);
        skeleton.addComponent(brushColorDisplay);
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        Runnable displayUpdate = settingsSkeleton.getBrushColorDisplayUpdate();
        displayUpdate.run();
    }

    private static void setUpColorSelector(LeftPaintPaneSkeleton skeleton){
        PaneLayout paneLayout = skeleton.getPaneLayout();
        RectQuery bounds = paneLayout.getBounds("COLOR_SELECTOR");
        ColorSelector colorSelector = new ColorSelector(bounds, skeleton);
        skeleton.addComponent(colorSelector);
    }

    private static void setUpPalette(LeftPaintPaneSkeleton skeleton){
        PaletteCanvasController paletteCanvasController = PaletteCanvasController.build(skeleton);
        paletteCanvasController.addToContainer(skeleton);
    }

    private static void setUpToggles(LeftPaintPaneSkeleton skeleton){
        skeleton.setToggleSize(new Size(36, 36));
        setUpBrushToggles(skeleton);
        skeleton.addComponent(ColorPickerToggleBuilder.buildToggle(skeleton));
        skeleton.addComponent(BrushChannelIndependenceToggleBuilder.buildToggle(skeleton));
    }

    private static void setUpBrushToggles(LeftPaintPaneSkeleton skeleton){
        BrushTogglesBuilder builder = new BrushTogglesBuilder(skeleton);
        Toggle[] brushToggles = builder.buildToggles(skeleton);
        for(Toggle toggle : brushToggles){
            skeleton.addComponent(toggle);
        }
    }

}
