package com.mason.mapgen.paint.builders;

import com.mason.libgui.components.sliders.BasicSlider;
import com.mason.libgui.components.sliders.SliderWithIcons;
import com.mason.libgui.components.toggles.BasicToggle;
import com.mason.libgui.components.toggles.ExclusiveToggleGroup;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.RectQuery;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import com.mason.mapgen.paint.components.panes.leftPane.ColorSelector;
import com.mason.mapgen.paint.components.panes.leftPane.PaletteImageComponent;
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
        BrushColorDisplay brushColorDisplay = BrushColorDisplay.build(size);
        brushColorDisplay.setCoord(new Coord(paneSize.width()/2 - 64, 3*paneSize.height()/4));
        return brushColorDisplay;
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

    private static PaneLayout constructPaneLayout(RectQuery bounds){
        PaneLayout layout = new PaneLayout(bounds);
        layout.divide("Root", 3, 1);
        layout.nameAddress("[2,0]", "PALETTE_BOX");

        layout.verticalDissect("[1,0]", 0.8);
        layout.nameAddress("[1,0]-[0,0]", "COLOR_SELECTOR_BOX");
        layout.nameAddress("[1,0]-[1,0]", "BRUSH_BRIGHTNESS_SLIDER_BOX");

        layout.divide("[0,0]", 2, 2);
        layout.nameAddress("[0,0]-[0,0]", "TOGGLE_BOX");
        layout.nameAddress("[0,0]-[0,1]", "BRUSH_COLOR_DISPLAY_BOX");

        layout.divide("TOGGLE_BOX", 1, 6);
        layout.nameAddress("TOGGLE_BOX-[0,1]", "BRUSH_TOGGLE_BOX");
        layout.nameAddress("TOGGLE_BOX-[0,2]", "COLOR_PICKER_TOGGLE_BOX");
        layout.nameAddress("TOGGLE_BOX-[0,3]", "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE_BOX");

        layout.divide("[0,0]-[1,0]", 2, 1);
        layout.divide("[0,0]-[1,1]", 2, 1);
        layout.nameAddress("[0,0]-[1,0]-[0,0]", "BRUSH_SIZE_SLIDER_BOX");
        layout.nameAddress("[0,0]-[1,0]-[1,0]", "BRUSH_ALPHA_SLIDER_BOX");
        layout.nameAddress("[0,0]-[1,1]-[0,0]", "BRUSH_CENTRE_SLIDER_BOX");
        layout.nameAddress("[0,0]-[1,1]-[1,0]", "BRUSH_CERTAINTY_SLIDER_BOX");

        return  layout;
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

    private static BasicToggle initializeBrushToggle(PaneLayout paneLayout, Size size){
        Coord coord = paneLayout.centre("BRUSH_TOGGLE_BOX", size);
        return BasicToggle.buildWithBasicDeco(
                "BRUSH_TOGGLE", new HitboxRect(coord, size), "assets/paintIcons/brush.png");
    }

    private static BasicToggle initializeColorPickerToggle(PaneLayout paneLayout, Size size){
        Coord coord = paneLayout.centre("COLOR_PICKER_TOGGLE_BOX", size);
        return BasicToggle.buildWithBasicDeco(
                "COLOR_PICKER_TOGGLE", new HitboxRect(coord, size), "assets/paintIcons/colorPicker.png");
    }

    private static BasicToggle initializeBrushChannelIndependenceToggle(PaneLayout paneLayout, Size size){
        Coord coord = paneLayout.centre("BRUSH_CHANNEL_INDEPENDENCE_TOGGLE_BOX", size);
        return BasicToggle.buildWithBasicDeco(
                "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE", new HitboxRect(coord, size), "assets/paintIcons/channelIndependence.png");
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

    private static BasicSlider initializeBrushSizeSlider(PaneLayout paneLayout, Size size, int handleWidth){
        Coord coord = paneLayout.centre("BRUSH_SIZE_SLIDER_BOX", size);
        BasicSlider slider = SliderWithIcons.buildWithBasicDeco(
                "BRUSH_SIZE_SLIDER",
                new HitboxRect(coord, size),
                handleWidth,
                "assets/paintIcons/tinyBrush.png",
                "assets/paintIcons/largeBrush.png");
        slider.setPosition(0.2);
        return slider;
    }

    private static BasicSlider initializeBrushAlphaSlider(PaneLayout paneLayout, Size size, int handleWidth){
        Coord coord = paneLayout.centre("BRUSH_ALPHA_SLIDER_BOX", size);
        BasicSlider slider = SliderWithIcons.buildWithBasicDeco(
                "BRUSH_ALPHA_SLIDER",
                new HitboxRect(coord, size),
                handleWidth,
                "assets/paintIcons/transparent.png",
                "assets/paintIcons/opaque.png");
        slider.setPosition(1);
        return slider;
    }

    private static BasicSlider initializeBrushCentreSlider(PaneLayout paneLayout, Size size, int handleWidth){
        Coord coord = paneLayout.centre("BRUSH_CENTRE_SLIDER_BOX", size);
        BasicSlider slider = SliderWithIcons.buildWithBasicDeco(
                "BRUSH_CENTRE_SLIDER",
                new HitboxRect(coord, size),
                handleWidth,
                "assets/paintIcons/primarySelected.png",
                "assets/paintIcons/secondarySelected.png");
        slider.setPosition(0);
        return slider;
    }

    private static BasicSlider initializeBrushCertaintySlider(PaneLayout paneLayout, Size size, int handleWidth){
        Coord coord = paneLayout.centre("BRUSH_CERTAINTY_SLIDER_BOX", size);
        BasicSlider slider = SliderWithIcons.buildWithBasicDeco(
                "BRUSH_CERTAINTY_SLIDER",
                new HitboxRect(coord, size),
                handleWidth,
                "assets/paintIcons/uncertain.png",
                "assets/paintIcons/certain.png");
        slider.setPosition(1);
        return slider;
    }

    private static BasicSlider initializeBrushBrightnessSlider(PaneLayout paneLayout, Size size, int handleWidth){
        Coord coord = paneLayout.centre("BRUSH_BRIGHTNESS_SLIDER_BOX", size);
        BasicSlider slider = SliderWithIcons.buildWithBasicDeco(
                "BRUSH_BRIGHTNESS_SLIDER",
                new HitboxRect(coord, size),
                handleWidth,
                "assets/paintIcons/dark.png",
                "assets/paintIcons/bright.png");
        slider.setPosition(0.5);
        return slider;
    }

    private static void wireUpSlider(BasicSlider slider, LeftPaintPaneSkeleton skeleton){
        skeleton.addComponent(slider);
        slider.addSliderEventListener(skeleton.getPaintToolKit());
        slider.dragIncrementEventForPaintToolkitSetUp();
    }

}
