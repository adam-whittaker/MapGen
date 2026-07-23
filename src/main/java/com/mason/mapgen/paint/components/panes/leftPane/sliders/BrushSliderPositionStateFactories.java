package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.sliderPositionState.*;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.interfaces.Movable;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;

import java.util.Arrays;

public class BrushSliderPositionStateFactories{


    private final IntState brushNumState;
    private final int numBrushes;
    private final Runnable colorMixerUpdate;
    private final Runnable averageBrushDisplayUpdate;
    private final Runnable colorSelectorUpdate;
    private final int sliderHandleWidth;


    BrushSliderPositionStateFactories(LeftPaintPaneSkeleton skeleton, int sliderHandleWidth){
        brushNumState = skeleton.getBrushNumState();
        numBrushes = skeleton.getNumBrushes();
        colorMixerUpdate = skeleton.getColorMixerUpdate();
        averageBrushDisplayUpdate = skeleton.getAverageBrushColorDisplayUpdate();
        colorSelectorUpdate = skeleton.getColorSelectorUpdate();
        this.sliderHandleWidth = sliderHandleWidth;
    }


    public SliderPositionState buildAlphaState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactory(1);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildCentreState(RectQuery railClamp){
        Runnable combinedUpdate = () -> {
            colorMixerUpdate.run();
            averageBrushDisplayUpdate.run();
        };
        SliderPositionStateFactory factory = createBrushNumIndexedFactoryWithUpdate(0, combinedUpdate);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildCertaintyState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactoryWithUpdate(0.5, colorMixerUpdate);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildSizeState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactory(0.2);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildBrightnessState(RectQuery railClamp){
        SliderPositionStateFactory factory = createUnindexedFactoryWithColorSelectorUpdate(0.7);
        return factory.buildSliderPositionState(railClamp);
    }


    private SliderPositionStateFactory createBrushNumIndexedFactory(double initialPosition){
        return (railClamp) -> {
            RectQuery coordClamp = getCoordClamp(railClamp);
            Coord initialCoord = getInitialCoordFromCoordClamp(coordClamp, initialPosition);
            Movable movable = createBrushNumIndexedMovableWithInitialCoord(initialCoord);
            IntRange range = getIntRange(coordClamp);
            return new BasicSliderPositionState(movable, range);
        };
    }

    private RectQuery getCoordClamp(RectQuery railClamp){
        return new Rect(railClamp.x(), railClamp.y(), railClamp.width()-sliderHandleWidth, railClamp.height());
    }

    private Coord getInitialCoordFromCoordClamp(RectQuery coordClamp, double initialPosition){
        int x = coordClamp.x() + (int)(coordClamp.width() * initialPosition);
        return new Coord(x, coordClamp.y());
    }

    private Movable createBrushNumIndexedMovableWithInitialCoord(Coord initialCoord){
        return new Movable(){

            final Coord[] coords = new Coord[numBrushes];
            {
                Arrays.fill(coords, initialCoord);
            }


            @Override
            public void setCoord(Coord c){
                coords[brushNumState.getState()] = c;
            }

            @Override
            public Coord getCoord(){
                return coords[brushNumState.getState()];
            }

        };
    }

    private IntRange getIntRange(RectQuery coordClamp){
        return new IntRange(coordClamp.x(), coordClamp.x() + coordClamp.width());
    }


    private SliderPositionStateFactory createBrushNumIndexedFactoryWithUpdate(double initialPosition, Runnable update){
        return (railClamp) -> {
            RectQuery coordClamp = getCoordClamp(railClamp);
            Coord initialCoord = getInitialCoordFromCoordClamp(coordClamp, initialPosition);
            Movable movable = createBrushNumIndexedMovableWithInitialCoord(initialCoord);
            IntRange range = getIntRange(coordClamp);
            SliderPositionStateWithUpdater state = new SliderPositionStateWithUpdater(movable, range);
            state.setUpdater(update);
            return state;
        };
    }


    private SliderPositionStateFactory createUnindexedFactoryWithColorSelectorUpdate(double initialPosition){
        return (railClamp) -> {
            RectQuery coordClamp = getCoordClamp(railClamp);
            Coord initialCoord = getInitialCoordFromCoordClamp(coordClamp, initialPosition);
            Movable movable = Movable.buildDefaultMovable(initialCoord);
            IntRange range = getIntRange(coordClamp);
            SliderPositionStateWithUpdater state = new SliderPositionStateWithUpdater(movable, range);
            state.setUpdater(colorSelectorUpdate);
            return state;
        };
    }

}
