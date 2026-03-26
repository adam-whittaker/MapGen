package com.mason.mapgen.paint.components.panes.leftPane.sliders;

import com.mason.libgui.components.sliders.sliderPositionState.*;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Rect;
import com.mason.libgui.utils.structures.interfaces.Movable;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.libgui.utils.structures.states.intState.IntState;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneSkeleton;

import java.util.Arrays;

public class BrushSliderPositionStateFactories{


    private final IntState brushNumState;
    private final int numBrushes;
    private final Runnable colorMixerUpdate;
    private final Runnable colorSelectorUpdate;
    private final int sliderHandleWidth;


    BrushSliderPositionStateFactories(LeftPaintPaneSkeleton skeleton, int sliderHandleWidth){
        brushNumState = skeleton.getBrushNumState();
        numBrushes = skeleton.getNumBrushes();
        colorMixerUpdate = skeleton.getColorMixerUpdate();
        colorSelectorUpdate = skeleton.getColorSelectorUpdate();
        this.sliderHandleWidth = sliderHandleWidth;
    }


    public SliderPositionState buildAlphaState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactory(1);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildCentreState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactoryWithColorMixerUpdate(1);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildCertaintyState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactoryWithColorMixerUpdate(0.5);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildSizeState(RectQuery railClamp){
        SliderPositionStateFactory factory = createBrushNumIndexedFactory(0.2);
        return factory.buildSliderPositionState(railClamp);
    }

    public SliderPositionState buildBrightnessState(RectQuery railClamp){
        SliderPositionStateFactory factory = createUnindexedFactoryWithColorSelectorUpdate(0.5);
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


    private SliderPositionStateFactory createBrushNumIndexedFactoryWithColorMixerUpdate(double initialPosition){
        return (railClamp) -> {
            RectQuery coordClamp = getCoordClamp(railClamp);
            Coord initialCoord = getInitialCoordFromCoordClamp(coordClamp, initialPosition);
            Movable movable = createBrushNumIndexedMovableWithInitialCoord(initialCoord);
            IntRange range = getIntRange(coordClamp);
            SliderPositionStateWithUpdater state = new SliderPositionStateWithUpdater(movable, range);
            state.setUpdater(colorMixerUpdate);
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
