package com.mason.mapgen.paint.components.panes.leftPane.toggles.brushToggles;


import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Rect;
import com.mason.libgui.utils.structures.Size;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorState;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;

public class ColorIconBuilder{


    private final ColorState primaryColor;
    private final ColorState secondaryColor;


    public ColorIconBuilder(ColorState primaryColor, ColorState secondaryColor){
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }


    public ColorIcon[] buildIconPair(RectQuery toggleBounds, int brushNum){
        RectQuery primaryBounds = buildPrimaryBounds(toggleBounds);
        RectQuery secondaryBounds = buildSecondaryBounds(toggleBounds);
        RGBQuery primaryMask = primaryColor.maskSpecificBrushNum(brushNum);
        RGBQuery secondaryMask = secondaryColor.maskSpecificBrushNum(brushNum);
        return new ColorIcon[]{
                new ColorIcon(primaryBounds, primaryMask),
                new ColorIcon(secondaryBounds, secondaryMask)
        };
    }

    private RectQuery buildPrimaryBounds(RectQuery toggleBounds){
        Size size = buildIconSize(toggleBounds);
        int x = toggleBounds.x() + size.width()/2;
        int y = toggleBounds.y() + size.height()/2;
        return Rect.buildRect(new Coord(x, y), size);
    }

    private RectQuery buildSecondaryBounds(RectQuery toggleBounds){
        Size size = buildIconSize(toggleBounds);
        int x = toggleBounds.x() + toggleBounds.width() - 3*size.width()/2;
        int y = toggleBounds.y() + toggleBounds.height() - 3*size.height()/2;
        return Rect.buildRect(new Coord(x, y), size);
    }

    private Size buildIconSize(RectQuery toggleBounds){
        int width = toggleBounds.width()/4;
        int height = toggleBounds.height()/4;
        return new Size(width, height);
    }

}
