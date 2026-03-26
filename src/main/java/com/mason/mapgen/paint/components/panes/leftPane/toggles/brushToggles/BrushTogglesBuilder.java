package com.mason.mapgen.paint.components.panes.leftPane.toggles.brushToggles;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.components.toggles.sourced.IntegerToggleGroup;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;

public class BrushTogglesBuilder{


    public static Toggle[] buildToggles(LeftPaintPaneSkeleton skeleton){
        IntegerToggleGroup group = createToggleGroup(skeleton);
        int numBrushes = getNumBrushes(skeleton);
        Toggle[] toggles = new Toggle[numBrushes];
        for(int brushNum=0; brushNum<numBrushes; brushNum++){
            toggles[brushNum] = constructBrushToggle(skeleton, group, brushNum);
        }
        return toggles;
    }

    private static IntegerToggleGroup createToggleGroup(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        return new IntegerToggleGroup(settingsSkeleton.getBrushNumState());
    }

    private static int getNumBrushes(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        return settingsSkeleton.getNumBrushes();
    }

    private static Toggle constructBrushToggle(LeftPaintPaneSkeleton skeleton, IntegerToggleGroup group, int brushNum){
        String brushName = "BRUSH_" + brushNum + "_TOGGLE";
        HitboxRect boundary = createToggleBoundary(skeleton, brushName);
        ButtonDeco deco = createToggleDeco(brushName);
        return group.createToggle(brushName, boundary, deco, brushNum);
    }

    private static HitboxRect createToggleBoundary(LeftPaintPaneSkeleton skeleton, String brushName){
        PaneLayout layout = skeleton.getPaneLayout();
        Size toggleSize = skeleton.getToggleSize();
        Coord coord = layout.centre(brushName, toggleSize);
        return new BasicHitboxRect(coord, toggleSize);
    }

    private static ButtonDeco createToggleDeco(String brushName){
        return BasicButtonDeco.build("assets/paintIcons/brush.png");
    }

}
