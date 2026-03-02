package com.mason.mapgen.paint.components.panes.leftPane;

import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class PaletteImageComponent extends GridImageComponent implements BoundedMouseInputListener{


    private PaintManager paintManager;


    public PaletteImageComponent(ChunkingGrid<PaintCentroidData> grid){
        super(grid);
    }


    public void setPaintManager(PaintManager paintManager){
        this.paintManager = paintManager;
    }

    @Override
    public void onMousePressed(MouseInputEvent e){
        e.setCoordRelativeTo(getCoord());
        paintManager.onMousePressedOnPalette(e);
    }

    @Override
    public void onMouseReleased(MouseInputEvent e){
        e.setCoordRelativeTo(getCoord());
        paintManager.onMouseReleasedOnPalette(e);
    }

}
