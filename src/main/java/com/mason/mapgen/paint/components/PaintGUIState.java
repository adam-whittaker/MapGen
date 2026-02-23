package com.mason.mapgen.paint.components;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIState;
import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.panes.PaintedImagePane;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class PaintGUIState extends GUIState{


    private final PaintedImageComponent imageComponent;


    private PaintGUIState(GUIStateSkeleton skeleton, PaintedImageComponent imageComponent){
        super(skeleton);
        this.imageComponent = imageComponent;
    }

    public static PaintGUIState build(Size screenSize, ChunkingGrid<PaintCentroidData> chunkingGrid){
        PaintedImageComponent imageComponent = new PaintedImageComponent(chunkingGrid);
        HitboxRect paneBoundary = constructPaneBoundary(screenSize);
        Pane pane = PaintedImagePane.build(paneBoundary, imageComponent);
        GUIStateSkeleton paintStateSkeleton = new GUIStateSkeleton();
        paintStateSkeleton.addComponent(pane);
        pane.setInputSource(paintStateSkeleton);
        return new PaintGUIState(paintStateSkeleton, imageComponent);
    }

    private static HitboxRect constructPaneBoundary(Size screenSize){
        int height = screenSize.height();
        int width = height;
        int y = 0;
        int x = (screenSize.width() - width)/2;
        return new HitboxRect(new Coord(x, y), new Size(width, height));
    }


    @Override
    public void setUp(GUIState previousState){

    }

    @Override
    public void tearDown(){

    }

}
