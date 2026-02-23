package com.mason.mapgen.procgen.noise;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.structures.grids.Grid;
import com.mason.mapgen.structures.records.*;

/**
 * Generates wrappable Perlin noise.
 * @author Adam Whittaker
 */
public class PerlinNoise extends AbstractNoise{


    private final Grid<Vector> vectorGrid;
    private final int octaveNum;
    private final double lacunarity;
    private final double persistence;
    private double amplitude;


    public PerlinNoise(Size gridSize,
                       double amplitude,
                       int octaveNum,
                       double lacunarity,
                       double persistence){
        super(gridSize);
        vectorGrid = createRandomVectorGrid(gridSize);
        this.octaveNum = octaveNum;
        this.lacunarity = lacunarity;
        this.persistence = persistence;
        this.amplitude = amplitude;
    }


    private Grid<Vector> createRandomVectorGrid(Size gridSize){
        Vector[][] lattice = new Vector[gridSize.height()][gridSize.width()];
        return Grid.buildGrid(lattice, c -> Vector.generateRandomUnitVector());
    }


    @Override
    protected void generateNoiseSafely(){
        Size boxSize = computeInitialBoxSize();
        for(int n=0; n<octaveNum; n++){
            overlayOctave(boxSize);
            amplitude *= persistence;
            boxSize = getBoxSizeForNextIteration(boxSize);
        }
    }

    private Size computeInitialBoxSize(){
        Size gridSize = getSize();
        return new Size(gridSize.width()/4, gridSize.height()/4);
    }

    private void overlayOctave(Size boxSize){
        grid.transformSelf((coord, val) -> {
            Rect box = boxContainingCoord(coord, boxSize);
            return val + amplitude * getPerlin(coord, box);
        });
    }

    private Rect boxContainingCoord(Coord c, Size boxSize){
        Coord topLeft = new Coord(c.x() - (c.x() % boxSize.width()), c.y() - (c.y() % boxSize.height()));
        return Rect.buildRect(topLeft, boxSize);
    }

    private double getPerlin(Coord c, Rect box){
        double topInterpolation = calculateTopEdgeInterpolation(c, box);
        double bottomInterpolation = calculateBottomEdgeInterpolation(c, box);
        double relativeY = calculateRelativeCoordinate(c.y(), box.height());
        return interpolate(topInterpolation, bottomInterpolation, relativeY);
    }

    private double interpolate(double start, double end, double lambda){
        return start*(1-lambda) + end*lambda;
    }

    private double calculateTopEdgeInterpolation(Coord c, Rect box){
        Size boxSize = box.size();
        double topLeftDot = dotBoxVectorWithDirectionVector(c, box.topLeft(), boxSize);
        double topRightDot = dotBoxVectorWithDirectionVector(c, box.topRight(), boxSize);
        double relativeX = calculateRelativeCoordinate(c.x(), box.width());
        return interpolate(topLeftDot, topRightDot, relativeX);
    }

    private double calculateBottomEdgeInterpolation(Coord c, Rect box){
        Size boxSize = box.size();
        double topLeftDot = dotBoxVectorWithDirectionVector(c, box.bottomLeft(), boxSize);
        double topRightDot = dotBoxVectorWithDirectionVector(c, box.bottomRight(), boxSize);
        double relativeX = calculateRelativeCoordinate(c.x(), box.width());
        return interpolate(topLeftDot, topRightDot, relativeX);
    }

    private double calculateRelativeCoordinate(double coord, double boxDim){
        double ratio = (coord % boxDim) / boxDim;
        return hermiteSmoothe(ratio);
    }
    
    private double hermiteSmoothe(double t){
        return t * t * (3 - 2*t);
    }

    private double dotBoxVectorWithDirectionVector(Coord c, Coord boxCoord, Size boxSize){
        Vector directionVector = calculateRelativeVector(c, boxCoord, boxSize);
        boxCoord = wrapCoord(boxCoord);
        Vector boxVector = vectorGrid.getValue(boxCoord);
        return boxVector.dot(directionVector);
    }

    private Vector calculateRelativeVector(Coord c, Coord boxCoord, Size boxSize){
        double relativeX = (double)(c.x() - boxCoord.x()) / boxSize.width();
        double relativeY = (double)(c.y() - boxCoord.y()) / boxSize.height();
        return Vector.fromCartesian(relativeX, relativeY);
    }

    private Coord wrapCoord(Coord c){
        Size gridSize = vectorGrid.getSize();
        return new Coord(c.x() % gridSize.width(), c.y() % gridSize.height());
    }

    private Size getBoxSizeForNextIteration(Size current){
        int width = (int) (current.width() * lacunarity);
        int height = (int) (current.height() * lacunarity);
        if(width < 1) width = 1;
        if(height < 1) height = 1;
        return new Size(width, height);
    }

}
