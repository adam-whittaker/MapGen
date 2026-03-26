package com.mason.mapgen.core.random;

import org.apache.commons.math3.distribution.BetaDistribution;

public final class RandomSource{


    private static final RandomAdapter RANDOM = new RandomAdapter();
    static{
        long seed = RANDOM.nextLong();
        RANDOM.setSeed(seed);
        System.out.println("Random seed: " + seed);
    }


    private RandomSource(){}


    public static int nextInt(int bound){
        return RANDOM.nextInt(bound);
    }

    public static double nextDouble(){
        return RANDOM.nextDouble();
    }

    public static double nextGaussian(){
        return RANDOM.nextGaussian();
    }

    public static BetaDistribution createBetaDistribution(double alpha, double beta){
        return new BetaDistribution(RANDOM, alpha, beta);
    }

}
