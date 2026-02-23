package com.mason.mapgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.procgen.noise.LookupFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LookupFunctionTest{

    @Test
    void defaultLookupSwapsXAndYWhenUnderlyingIsZero() {
        Size gridSize = new Size(10, 5); // width unused, height=5
        Coord input = new Coord(2, 3);
        double underlying = 0.0;

        Coord result = LookupFunction.defaultLookupFunction(input, underlying, gridSize);

        // x' = coord.y = 3
        // y' = (int)(coord.x + height * underlying) % height
        //    = (int)(2 + 5 * 0) % 5 = 2
        assertEquals(new Coord(3, 2), result);
    }

    @Test
    void defaultLookupUsesUnderlyingAndWrapsModuloHeight() {
        Size gridSize = new Size(7, 4); // height=4
        Coord input = new Coord(3, 1);
        double underlying = 0.75;

        Coord result = LookupFunction.defaultLookupFunction(input, underlying, gridSize);

        // x' = coord.y = 1
        // y' = (int)(coord.x + height * underlying) % height
        //    = (int)(3 + 4*0.75) % 4
        //    = (int)(3 + 3) % 4
        //    = 6 % 4 = 2
        assertEquals(new Coord(1, 2), result);
    }

    @Test
    void underlyingOneIsEquivalentToUnderlyingZeroPlusFullHeight() {
        Size gridSize = new Size(3, 6); // height=6
        Coord input = new Coord(4, 0);

        Coord withZero = LookupFunction.defaultLookupFunction(input, 0.0, gridSize);
        Coord withOne = LookupFunction.defaultLookupFunction(input, 1.0, gridSize);

        // y0 = (int)(4 + 6*0) % 6 = 4 % 6 = 4
        // y1 = (int)(4 + 6*1) % 6 = 10 % 6 = 4
        assertEquals(withZero, withOne);
    }

    @Test
    void defaultLookupFunctionCanBeUsedAsLookupFunctionImplementation() {
        Size gridSize = new Size(8, 5);
        Coord input = new Coord(1, 4);
        double underlying = 0.5;

        // Use method reference as a LookupFunction implementation
        LookupFunction lf = LookupFunction::defaultLookupFunction;

        Coord viaStatic = LookupFunction.defaultLookupFunction(input, underlying, gridSize);
        Coord viaInterface = lf.lookup(input, underlying, gridSize);

        assertEquals(viaStatic, viaInterface);
    }

}