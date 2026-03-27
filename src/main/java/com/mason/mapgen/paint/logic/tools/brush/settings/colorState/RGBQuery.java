package com.mason.mapgen.paint.logic.tools.brush.settings.colorState;

import java.awt.*;

public interface RGBQuery{

    int getRed();

    int getGreen();

    int getBlue();

    default Color sampleRGBColor(){
        return new Color(getRed(), getGreen(), getBlue());
    }

}
