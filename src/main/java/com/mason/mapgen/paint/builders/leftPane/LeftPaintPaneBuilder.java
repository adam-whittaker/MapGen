package com.mason.mapgen.paint.builders.leftPane;

import com.mason.libgui.components.panes.PaneLayout;
import com.mason.libgui.components.sliders.BasicSlider;
import com.mason.libgui.components.toggles.BasicToggle;
import com.mason.libgui.components.toggles.ExclusiveToggleGroup;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import com.mason.mapgen.paint.components.panes.leftPane.ColorSelector;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.logic.tools.brush.BrushColor;
import com.mason.mapgen.paint.skeletons.LeftPaintPaneSkeleton;
import com.mason.mapgen.paint.skeletons.PaintGUIStateSkeleton;

public class LeftPaintPaneBuilder{


    public static LeftPaintPaneSkeleton buildSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton, int sidePaneWidth){
        Size screenSize = paintGUIStateSkeleton.getScreenSize();
        Size size = new Size(sidePaneWidth, screenSize.height());
        paintGUIStateSkeleton.setBrushColorDisplay(buildBrushColorDisplay(size));
        LeftPaintPaneSkeleton skeleton = buildSkeletonWithInitialFields(paintGUIStateSkeleton, size);
        setUpPalette(skeleton, paintGUIStateSkeleton.getPaletteImageComponent());
        setUpToggles(skeleton);
        setUpSliders(skeleton);

        return skeleton;
    }

    private static BrushColorDisplay buildBrushColorDisplay(Size paneSize){
        Size size = new Size(132, 132);
        return BrushColorDisplay.build(size);
    }

    private static LeftPaintPaneSkeleton buildSkeletonWithInitialFields(PaintGUIStateSkeleton paintGUIStateSkeleton, Size paneSize){
        LeftPaintPaneSkeleton skeleton = new LeftPaintPaneSkeleton();
        skeleton.setPaintKeyProcessor(paintGUIStateSkeleton.getPaintKeyProcessor());
        skeleton.setBoundary(new HitboxRect(new Coord(0, 0), paneSize));
        skeleton.setToggleGroup(new ExclusiveToggleGroup());

        BrushColorDisplay brushColorDisplay = paintGUIStateSkeleton.getBrushColorDisplay();
        PaneLayout paneLayout = constructPaneLayout(skeleton.getBoundary());
        brushColorDisplay.setCoord(paneLayout.centre("BRUSH_COLOR_DISPLAY_BOX", brushColorDisplay.getSize()));

        BrushColor brushColor = new BrushColor(brushColorDisplay);
        ColorSelector colorSelector = constructColorSelector(brushColor, paneSize, paneLayout);
        skeleton.addComponent(colorSelector);

        skeleton.setPaintToolKit(new PaintToolKit(brushColor, paintGUIStateSkeleton.getPaintManager(), colorSelector::setBrightness));
        skeleton.setPaneLayout(paneLayout);
        skeleton.addComponent(brushColorDisplay);

        return skeleton;
    }

    private static ColorSelector constructColorSelector(BrushColor brushColor, Size paneSize, PaneLayout paneLayout){
        Size selectorSize = new Size(paneSize.width()-128, paneSize.height()/3 - 64);
        HitboxRect hitbox = new HitboxRect(paneLayout.centre("COLOR_SELECTOR_BOX", selectorSize), selectorSize);
        return new ColorSelector(hitbox, brushColor);
    }


    private static void setUpPalette(LeftPaintPaneSkeleton skeleton, PaletteImageComponent paletteImageComponent){
        Size paletteSize = paletteImageComponent.getSize();
        PaneLayout paneLayout = skeleton.getPaneLayout();
        paletteImageComponent.setCoord(paneLayout.centre("PALETTE_BOX", paletteSize));
        skeleton.addComponent(paletteImageComponent);
    }


    private static void setUpToggles(LeftPaintPaneSkeleton skeleton){
        PaneLayout paneLayout = skeleton.getPaneLayout();
        Size toggleSize = new Size(36, 36);

        BasicToggle brush = initializeBrushToggle(paneLayout, toggleSize);
        BasicToggle colorPicker = initializeColorPickerToggle(paneLayout, toggleSize);
        BasicToggle channelIndependence = initializeBrushChannelIndependenceToggle(paneLayout, toggleSize);

        wireUpToggleFully(brush, skeleton);
        wireUpToggleFully(colorPicker, skeleton);
        wireUpToggleWithoutGroup(channelIndependence, skeleton);

        skeleton.getPaintKeyProcessor().registerToggles(brush, colorPicker);

        brush.select();
    }

    private static void wireUpToggleFully(BasicToggle toggle, LeftPaintPaneSkeleton skeleton){
        wireUpToggleWithoutGroup(toggle, skeleton);
        ExclusiveToggleGroup toggleGroup = skeleton.getToggleGroup();
        toggleGroup.addToggle(toggle);
    }

    private static void wireUpToggleWithoutGroup(BasicToggle toggle, LeftPaintPaneSkeleton skeleton){
        skeleton.addComponent(toggle);
        toggle.addToggleEventListener(skeleton.getPaintToolKit());
    }


    private static void setUpSliders(LeftPaintPaneSkeleton skeleton){
        PaneLayout paneLayout = skeleton.getPaneLayout();
        Size fullSliderSize = new Size(198, 24);
        Size brightnessSliderSize = new Size(330, 24);
        int handleWidth = 24;

        BasicSlider brushSizeSlider = initializeBrushSizeSlider(paneLayout, fullSliderSize, handleWidth);
        BasicSlider brushAlphaSlider = initializeBrushAlphaSlider(paneLayout, fullSliderSize, handleWidth);
        BasicSlider brushCentreSlider = initializeBrushCentreSlider(paneLayout, fullSliderSize, handleWidth);
        BasicSlider brushCertaintySlider = initializeBrushCertaintySlider(paneLayout, fullSliderSize, handleWidth);
        BasicSlider brushBrightnessSlider = initializeBrushBrightnessSlider(paneLayout, brightnessSliderSize, handleWidth);

        wireUpSlider(brushSizeSlider, skeleton);
        wireUpSlider(brushAlphaSlider, skeleton);
        wireUpSlider(brushCentreSlider, skeleton);
        wireUpSlider(brushCertaintySlider, skeleton);
        wireUpSlider(brushBrightnessSlider, skeleton);
    }

}
