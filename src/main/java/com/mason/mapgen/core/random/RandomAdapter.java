package com.mason.mapgen.core.random;

import org.apache.commons.math3.random.RandomGenerator;

import java.util.Random;

public class RandomAdapter implements RandomGenerator{


    private final Random random;


    RandomAdapter(){
        this.random = new Random();
    }


    @Override
    public void setSeed(int i){
        random.setSeed(i);
    }

    @Override
    public void setSeed(int[] ints){
        long s = 0;
        for(int v : ints){
            s = s * 31 + v;
        }
        random.setSeed(s);
    }

    @Override
    public void setSeed(long l){
        random.setSeed(l);
    }

    @Override
    public void nextBytes(byte[] bytes){
        random.nextBytes(bytes);
    }

    @Override
    public int nextInt(){
        return random.nextInt();
    }

    @Override
    public int nextInt(int i){
        return random.nextInt(i);
    }

    @Override
    public long nextLong(){
        return random.nextLong();
    }

    @Override
    public boolean nextBoolean(){
        return random.nextBoolean();
    }

    @Override
    public float nextFloat(){
        return random.nextFloat();
    }

    @Override
    public double nextDouble(){
        return random.nextDouble();
    }

    @Override
    public double nextGaussian(){
        return random.nextGaussian();
    }

}
