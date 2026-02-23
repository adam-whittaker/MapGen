package com.mason.mapgen.procgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.grids.Grid;

public class LookupNoise extends AbstractNoise{


    private final Noise underlyingNoise;
    private final LookupFunction lookupFunction;


    public LookupNoise(Noise underlyingNoise, LookupFunction lookupFunction){
        super(underlyingNoise.getSize());
        this.underlyingNoise = underlyingNoise;
        this.lookupFunction = lookupFunction;
    }

    public static LookupNoise buildPerlinLookupNoise(Size gridSize,
                                                     double amplitude,
                                                     int octaveNum,
                                                     double lacunarity,
                                                     double persistence,
                                                     LookupFunction lookupFunction){
        Noise noise = new PerlinNoise(gridSize, amplitude, octaveNum, lacunarity, persistence);
        return new LookupNoise(noise, lookupFunction);
    }

    public static LookupNoise buildWithDefaultPerlinLookupFunction(Size gridSize,
                                                                   double amplitude,
                                                                   int octaveNum,
                                                                   double lacunarity,
                                                                   double persistence){
        return buildPerlinLookupNoise(gridSize, amplitude, octaveNum, lacunarity, persistence, LookupFunction::defaultLookupFunction);
    }


    @Override
    protected void generateNoiseSafely(){
        underlyingNoise.generateNoise();
        underlyingNoise.normalise();
        lookupNoiseInUnderlyingGrid();
    }

    private void lookupNoiseInUnderlyingGrid(){
        Grid<Double> underlyingGrid = underlyingNoise.getGrid();
        Size gridSize = underlyingGrid.getSize();
        grid.transformSelf((coord, val) -> {
            Coord lookupCoord = lookupFunction.lookup(coord, underlyingGrid.getValue(coord), gridSize);
            return underlyingGrid.getValue(lookupCoord);
        });
    }

}
