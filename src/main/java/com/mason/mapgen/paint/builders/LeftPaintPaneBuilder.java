package com.mason.mapgen.paint.builders;

import com.mason.libgui.components.toggles.BasicToggle;
import com.mason.libgui.components.toggles.ToggleGroup;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import com.mason.mapgen.paint.components.panes.leftPane.PaletteImageComponent;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.paint.logic.tools.PaintToolKit;
import com.mason.mapgen.paint.skeletons.LeftPaintPaneSkeleton;

public class LeftPaintPaneBuilder{


    public static LeftPaintPaneSkeleton buildSkeleton(Size screenSize, int sidePaneWidth, PaletteImageComponent paletteImageComponent, PaintManager paintManager){
        Size size = new Size(sidePaneWidth, screenSize.height());
        BrushColorDisplay brushColorDisplay = buildBrushColorDisplay(size);
        LeftPaintPaneSkeleton skeleton = buildSkeletonWithInitialFields(size, paintManager, brushColorDisplay);
        setUpToggles(skeleton, size);

        paletteImageComponent.setCoord(new Coord(64, screenSize.height()/3));
        skeleton.addComponent(paletteImageComponent);
        skeleton.addComponent(brushColorDisplay);

        return skeleton;
    }

    private static BrushColorDisplay buildBrushColorDisplay(Size paneSize){
        Size size = new Size(132, 132);
        BrushColorDisplay brushColorDisplay = BrushColorDisplay.build(size);
        brushColorDisplay.setCoord(new Coord(paneSize.width()/2 - 64, 3*paneSize.height()/4));
        return brushColorDisplay;
    }

    private static LeftPaintPaneSkeleton buildSkeletonWithInitialFields(Size size, PaintManager paintManager, BrushColorDisplay brushColorDisplay){
        LeftPaintPaneSkeleton skeleton = new LeftPaintPaneSkeleton();
        skeleton.setBoundary(new HitboxRect(new Coord(0, 0), size));
        skeleton.setToggleGroup(new ToggleGroup());
        skeleton.setPaintToolKit(new PaintToolKit(brushColorDisplay, paintManager::setCurrentTool));
        return skeleton;
    }

    private static void setUpToggles(LeftPaintPaneSkeleton skeleton, Size size){
        BasicToggle brush = BasicToggle.buildWithBasicDeco(
                "BRUSH_TOGGLE", new HitboxRect(new Coord(32, 32), new Size(36, 36)), "assets/paintIcons/brush.png");
        BasicToggle colorPicker = BasicToggle.buildWithBasicDeco(
                "COLOR_PICKER_TOGGLE", new HitboxRect(new Coord(72, 32), new Size(36, 36)), "assets/paintIcons/colorPicker.png");
        BasicToggle channelIndependence = BasicToggle.buildWithBasicDeco(
                "BRUSH_CHANNEL_INDEPENDENCE_TOGGLE", new HitboxRect(new Coord(112, 32), new Size(36, 36)), "assets/paintIcons/channelIndependence.png");

        wireUpToggleFully(brush, skeleton);
        wireUpToggleFully(colorPicker, skeleton);
        wireUpToggleWithoutGroup(channelIndependence, skeleton);
    }

    private static void wireUpToggleFully(BasicToggle toggle, LeftPaintPaneSkeleton skeleton){
        wireUpToggleWithoutGroup(toggle, skeleton);
        ToggleGroup toggleGroup = skeleton.getToggleGroup();
        toggleGroup.addToggle(toggle);
    }

    private static void wireUpToggleWithoutGroup(BasicToggle toggle, LeftPaintPaneSkeleton skeleton){
        skeleton.addComponent(toggle);
        toggle.addToggleEventListener(skeleton.getPaintToolKit());
    }

}
