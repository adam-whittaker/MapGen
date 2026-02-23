package com.mason.mapgen.procgen.noise;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.grids.MutableGrid;

public interface Noise{

    MutableGrid<Double> getGrid();

    default Size getSize(){
        return getGrid().getSize();
    }

    boolean isNoiseGenerated();

    void generateNoise();

    default void normalise(){
        double min = calculateGlobalMinimum();
        double max = calculateGlobalMaximum();
        shiftMinAndMaxToUnitInterval(min, max);
    }

    private double calculateGlobalMinimum(){
        double min = Double.MAX_VALUE;
        for(Double d : getGrid()){
            if(min > d){
                min = d;
            }
        }
        return min;
    }

    private double calculateGlobalMaximum(){
        double max = -Double.MAX_VALUE;
        for(Double d : getGrid()){
            if(max < d){
                max = d;
            }
        }
        return max;
    }

    private void shiftMinAndMaxToUnitInterval(double min, double max){
        double range = max - min;
        if(range == 0){
            transformToDefaultValueOneHalf();
        }else{
            transformRangeToUnitIntervalSafely(min, range);
        }
    }

    private void transformToDefaultValueOneHalf(){
        getGrid().transformSelf((coord, val) -> {
            return 0.5;
        });
    }

    private void transformRangeToUnitIntervalSafely(double min, double range){
        getGrid().transformSelf((coord, val) -> {
            return (val - min)/range;
        });
    }

}
