package com.mason.mapgen.gui.states;

public class NullGUIState extends GUIState{


    public NullGUIState(){
        super(new GUIStateSkeleton());
    }


    @Override
    public void setUp(GUIState previousState){

    }

    @Override
    public void tearDown(){

    }


}
