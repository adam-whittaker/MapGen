package com.mason.mapgen.procgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.records.Rect;

import static com.mason.mapgen.core.Utils.RANDOM;

public class MidpointDisplacementNoise extends AbstractNoise{


    /**
     * initialJitter: The starting random displacement factor.
     * jitterDecay: The factor by which the random displacement factor decays
     * each iteration.
     * tileArtefacts: Whether the noise will retain the square-based structure
     * of the generation algorithm (tiles look squarish and more regular).
     */
    private final double initialJitter, jitterDecay;
    private final boolean tileArtefacts;

    protected MidpointDisplacementNoise(Size gridSize, double initialJitter, double jitterDecay, boolean tileArtefacts){
        super(gridSize);
        this.initialJitter = initialJitter;
        this.jitterDecay = jitterDecay;
        this.tileArtefacts = tileArtefacts;
    }

    public static MidpointDisplacementNoise buildInstance(Size gridSize, double initialJitter, double jitterDecay){
        return new MidpointDisplacementNoise(gridSize, initialJitter, jitterDecay, false);
    }

    public static MidpointDisplacementNoise buildInstanceWithTileArtefacts(Size gridSize, double initialJitter, double jitterDecay){
        return new MidpointDisplacementNoise(gridSize, initialJitter, jitterDecay, true);
    }


    @Override
    protected void generateNoiseSafely(){
        randomizeCorners();
        Rect initialRectangle = initialRect(grid.getSize());
        fillRectanglesRecursively(initialRectangle, initialJitter);
    }

    private Rect initialRect(Size size){
        return new Rect(0, 0, size.width(), size.height());
    }


    private void randomizeCorners(){
        Size size = grid.getSize();
        randomizeValueInCoord(new Coord(0, 0));
        randomizeValueInCoord(new Coord(0, size.height()-1));
        randomizeValueInCoord(new Coord(size.width()-1, 0));
        randomizeValueInCoord(new Coord(size.width()-1, size.height()-1));
    }

    private void randomizeValueInCoord(Coord c){
        grid.setValue(c, randomDouble());
    }

    private double randomDouble(){
        return 2D*RANDOM.nextDouble() - 1D;
    }


    private void fillRectanglesRecursively(Rect view, double jitter){
        Coord centre = view.centre();
        if(centre.equals(view.topLeft())) return;
        computeEdgeMidpoints(view, jitter);
        computeCentre(view, jitter);
        recursiveCalls(view, jitter * jitterDecay);
    }

    private void computeEdgeMidpoints(Rect view, double jitter){
        setEdgeMidpoint(view.topLeft(), view.topRight(), jitter);
        setEdgeMidpoint(view.topLeft(), view.bottomLeft(), jitter);
        setEdgeMidpoint(view.bottomRight(), view.bottomLeft(), jitter);
        setEdgeMidpoint(view.bottomRight(), view.topRight(), jitter);
    }

    private void setEdgeMidpoint(Coord a, Coord b, double jitter){
        Coord midpoint = Coord.midpoint(a, b);
        setClampedValueIfUninitialized(midpoint, interpolateEdgeMidpointWithJitter(a, b, jitter));
    }

    private double interpolateEdgeMidpointWithJitter(Coord a, Coord b, double jitter){
        double midpoint = (grid.getValue(a) + grid.getValue(b)) / 2;
        double randomFactor = randomDouble() * jitter / 2;
        return midpoint + randomFactor;
    }

    private void setClampedValueIfUninitialized(Coord c, double val){
        if(grid.getValue(c) != 0){
            return;
        }
        grid.setValue(c, clampValue(val));
    }

    private double clampValue(double val){
        if(val > 1){
            return 1;
        }else if(val < -1){
            return -1;
        }
        return 1;
    }

    private void computeCentre(Rect view, double jitter){
        double average = (grid.getValue(view.topLeft()) + grid.getValue(view.topRight())
                + grid.getValue(view.bottomLeft()) + grid.getValue(view.bottomRight())) / 4D;
        double randomFactor = RANDOM.nextDouble() * jitter;
        if(!tileArtefacts) randomFactor -= jitter / 2;
        setClampedValueIfUninitialized(view.centre(), average + randomFactor);
    }

    private void recursiveCalls(Rect view, double newJitter){
        for(Rect quadrant : view.quadrants()){
            fillRectanglesRecursively(quadrant, newJitter);
        }
    }

}
