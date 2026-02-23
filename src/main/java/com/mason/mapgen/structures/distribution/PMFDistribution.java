package com.mason.mapgen.structures.distribution;

import java.util.List;
import java.util.function.Function;

import static com.mason.mapgen.core.Utils.RANDOM;

public class PMFDistribution<T> implements Distribution<T>{


    private final List<T> items;
    private final double[] pmf;
    private final double totalMass;


    private PMFDistribution(List<T> items, double[] pmf){
        this.items = items;
        this.pmf = pmf;
        totalMass = calculateTotalMassFromPMF(pmf);
    }

    private static double calculateTotalMassFromPMF(double[] pmf){
        double totalMass = 0;
        for(double d : pmf) totalMass += d;
        return totalMass;
    }

    public static <E> PMFDistribution<E> withPMFArray(List<E> items, double[] pmf){
        return new PMFDistribution<>(items, pmf);
    }

    public static <E> PMFDistribution<E> uniform(List<E> items){
           return withPMFArray(items, arrayOfOnes(items.size()));
    }

    private static double[] arrayOfOnes(int length){
        double[] array = new double[length];
        for(int i=0; i<length; i++){
            array[i] = 1;
        }
        return array;
    }

    public static <E> PMFDistribution<E> withPMFFunction(List<E> items, Function<? super E, Double> pmf){
        return withPMFArray(items, calculatePmf(items, pmf));
    }

    private static <E> double[] calculatePmf(List<E> items, Function<? super E, Double> func){
        double[] ret = new double[items.size()];
        for(int n=0; n<ret.length; n++){
            ret[n] = func.apply(items.get(n));
        }
        return ret;
    }


    @Override
    public T next(){
        double chance = RANDOM.nextDouble() * totalMass;
        for(int n=0; n<pmf.length; n++){
            if(chance < pmf[n]) return items.get(n);
            else chance -= pmf[n];
        }
        throw new IllegalStateException("Failed to select a random item from the distribution.");
    }

}
