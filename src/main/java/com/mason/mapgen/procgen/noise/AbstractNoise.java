package com.mason.mapgen.procgen.noise;

import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.grids.MutableGrid;

public abstract class AbstractNoise implements Noise{


    final MutableGrid<Double> grid;
    private boolean isNoiseGenerated = false;


    protected AbstractNoise(Size size){
        grid = createDoubleGridOfZeros(size);
    }


    //Accessors
    @Override
    public MutableGrid<Double> getGrid(){
        return grid;
    }

    @Override
    public boolean isNoiseGenerated(){
        return isNoiseGenerated;
    }


    //Mutators
    @Override
    public final void generateNoise(){
        if(isNoiseGenerated()){
            throw new IllegalStateException("Noise has already been generated!");
        }
        isNoiseGenerated = true;
        generateNoiseSafely();
    }

    protected abstract void generateNoiseSafely();


    public static MutableGrid<Double> createDoubleGridOfZeros(Size size){
        return MutableGrid.buildGrid(new Double[size.height()][size.width()], (coord) -> 0d);
    }

}
