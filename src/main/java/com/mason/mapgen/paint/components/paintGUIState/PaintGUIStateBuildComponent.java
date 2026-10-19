package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.panes.bottomPane.pane.BottomPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.PaintedImagePaneBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.pane.RightPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneBuildComponent;
import com.mason.mapgen.paint.logic.keyProcessor.PaintKeyProcessor;
import com.mason.mapgen.paint.logic.keyProcessor.PaintKeyProcessorBuildComponent;

public class PaintGUIStateBuildComponent extends ComponentBuildResource<PaintGUIState>{


    public final static PaintGUIStateBuildComponent INSTANCE = new PaintGUIStateBuildComponent();

    private GUIStateSkeleton skeleton;


    private PaintGUIStateBuildComponent(){
        super();
    }

    @Override
    public PaintGUIState build(){
        skeleton = new GUIStateSkeleton();
        return new PaintGUIState(skeleton);
    }

    @Override
    public void postBuild(){
        PaintKeyProcessor keyProcessor = PaintKeyProcessorBuildComponent.INSTANCE.get();
        skeleton.addKeyListener(keyProcessor);

        registerPaneWithSkeleton(PaintedImagePaneBuildComponent.INSTANCE.get());
        registerPaneWithSkeleton(LeftPaintPaneBuildComponent.INSTANCE.get());
        registerPaneWithSkeleton(RightPaintPaneBuildComponent.INSTANCE.get());
        registerPaneWithSkeleton(TopPaintPaneBuildComponent.INSTANCE.get());
        registerPaneWithSkeleton(BottomPaintPaneBuildComponent.INSTANCE.get());
    }

    private void registerPaneWithSkeleton(Pane pane){
        skeleton.addComponent(pane);
        pane.setInputSource(skeleton);
    }


    @Override
    public String toString(){
        return "PAINT_GUI_STATE";
    }

}
