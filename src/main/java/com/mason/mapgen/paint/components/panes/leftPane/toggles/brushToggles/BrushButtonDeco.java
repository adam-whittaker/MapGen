package com.mason.mapgen.paint.components.panes.leftPane.toggles.brushToggles;

import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.toggles.ToggleRenderState;
import com.mason.libgui.utils.ImageUtils;
import com.mason.libgui.utils.structures.interfaces.RectQuery;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BrushButtonDeco extends BasicButtonDeco{


    private final ColorIcon primaryIcon;
    private final ColorIcon secondaryIcon;


    private BrushButtonDeco(Image icon, ColorIcon primaryIcon, ColorIcon secondaryIcon){
        super(icon);
        this.primaryIcon = primaryIcon;
        this.secondaryIcon = secondaryIcon;
    }

    public static BrushButtonDeco buildBrushButtonDeco(String iconFilepath, ColorIcon[] icons){
        BufferedImage image = ImageUtils.readImage(iconFilepath);
        return new BrushButtonDeco(image, icons[0], icons[1]);
    }


    @Override
    protected void drawButtonDecoWithGraphicsContext(Graphics2D g, RectQuery box, ToggleRenderState state){
        super.drawButtonDecoWithGraphicsContext(g, box, state);
        primaryIcon.render(g);
        secondaryIcon.render(g);
    }

}
