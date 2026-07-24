package com.mason.mapgen.paint.components.panes.leftPane.toggles.brushToggles;

import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.components.toggles.sourced.IntegerToggleGroup;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsSkeleton;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorState;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;

import java.util.function.Function;

public class BrushTogglesBuilder{
    

    private final ColorIconBuilder iconBuilder;
    private final int numBrushes;
    private final IntegerToggleGroup toggleGroup;
    private final Function<String, HitboxRect> toggleBoundaryBuilder;
    
    
    public BrushTogglesBuilder(LeftPaintPaneSkeleton skeleton){
        iconBuilder = createColorIconBuilder(skeleton);
        numBrushes = getNumBrushes(skeleton);
        toggleGroup = createToggleGroup(skeleton);
        toggleBoundaryBuilder = createToggleBoundaryBuilder(skeleton);
    }

    private static ColorIconBuilder createColorIconBuilder(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        ColorState primary = settingsSkeleton.getPrimaryColorState();
        ColorState secondary = settingsSkeleton.getSecondaryColorState();
        return new ColorIconBuilder(primary, secondary);
    }

    private static int getNumBrushes(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        return settingsSkeleton.getNumBrushes();
    }

    private static IntegerToggleGroup createToggleGroup(LeftPaintPaneSkeleton skeleton){
        PaintControlSettingsSkeleton settingsSkeleton = skeleton.getPaintControlSettingsSkeleton();
        Runnable brushColorDisplayUpdate = skeleton.getBrushColorDisplayUpdate();
        return new IntegerToggleGroup(settingsSkeleton.getBrushNumState(), brushColorDisplayUpdate);
    }

    private static Function<String, HitboxRect> createToggleBoundaryBuilder(LeftPaintPaneSkeleton skeleton){
        PaneLayout layout = skeleton.getPaneLayout();
        Size toggleSize = skeleton.getToggleSize();
        return (brushName) -> {
            Coord coord = layout.centre(brushName, toggleSize);
            return new BasicHitboxRect(coord, toggleSize);
        };
    }


    public Toggle[] buildToggles(){
        Toggle[] toggles = new Toggle[numBrushes];
        for(int brushNum=0; brushNum<numBrushes; brushNum++){
            toggles[brushNum] = constructBrushToggle(brushNum);
        }
        return toggles;
    }

    private Toggle constructBrushToggle(int brushNum){
        String brushName = "BRUSH_" + brushNum + "_TOGGLE";
        HitboxRect boundary = toggleBoundaryBuilder.apply(brushName);
        ButtonDeco deco = createToggleDeco(boundary, brushNum);
        return toggleGroup.createToggle(brushName, boundary, deco, brushNum);
    }

    private BrushButtonDeco createToggleDeco(RectQuery bounds, int brushNum){
        ColorIcon[] icons = iconBuilder.buildIconPair(bounds, brushNum);
        return BrushButtonDeco.buildBrushButtonDeco("assets/paintIcons/brush.png", icons);
    }

}
