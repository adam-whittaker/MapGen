package com.mason.mapgen.core;

import java.util.Random;

public final class Utils{


    public static final Random RANDOM = new Random();
    static{
        long seed = RANDOM.nextLong();
        RANDOM.setSeed(seed);
        System.out.println("Random seed: " + seed);
    }


    private Utils(){}

}
