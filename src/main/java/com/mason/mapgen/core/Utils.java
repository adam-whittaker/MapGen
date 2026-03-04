package com.mason.mapgen.core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

public final class Utils{


    public static final Random RANDOM = new Random();
    static{
        long seed = RANDOM.nextLong();
        RANDOM.setSeed(seed);
        System.out.println("Random seed: " + seed);
    }


    private Utils(){}


    public static int lerp(int val1, int val2, double t){
        return (int)(val1*(1-t) + val2*t);
    }

    public static int[] getPixelMask(BufferedImage image){
        DataBufferInt dataBufferInt = ((DataBufferInt) image.getRaster().getDataBuffer());
        return dataBufferInt.getData();
    }

}
