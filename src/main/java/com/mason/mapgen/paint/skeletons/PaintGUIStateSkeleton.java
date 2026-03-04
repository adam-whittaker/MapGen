package com.mason.mapgen.paint.skeletons;

import com.mason.libgui.core.component.HitboxRect;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.GridImageComponent;
import com.mason.mapgen.paint.components.PaintCentroidData;
import com.mason.mapgen.paint.components.panes.*;
import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import com.mason.mapgen.paint.components.panes.leftPane.LeftPaintPane;
import com.mason.mapgen.paint.components.panes.leftPane.PaletteImageComponent;
import com.mason.mapgen.paint.logic.PaintCanvas;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;
import com.mason.mapgen.paint.logic.PaintManager;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class PaintGUIStateSkeleton{


    private GUIStateSkeleton guiStateSkeleton;
    private PaintedImagePane paintedImagePane;
    private LeftPaintPane leftPaintPane;
    private RightPaintPane rightPaintPane;
    private TopPaintPane topPaintPane;
    private BottomPaintPane bottomPaintPane;
    private PaintManager paintManager;
    private Size screenSize;
    private HitboxRect paintedImagePaneBoundary;
    private ChunkingGrid<PaintCentroidData> mainCanvasChunkingGrid;
    private PaletteImageComponent paletteImageComponent;
    private PaintCanvas palette;
    private PaintKeyProcessor paintKeyProcessor;
    private GridImageComponent paintImageComponent;
    private BrushColorDisplay brushColorDisplay;


    public PaintGUIStateSkeleton(){}


    public GUIStateSkeleton getGuiStateSkeleton(){
        if(guiStateSkeleton == null){
            throw new IllegalStateException("guiStateSkeleton is not set");
        }
        return guiStateSkeleton;
    }

    public void setGuiStateSkeleton(GUIStateSkeleton guiStateSkeleton){
        if(this.guiStateSkeleton != null){
            throw new IllegalStateException("guiStateSkeleton is already set");
        }
        this.guiStateSkeleton = guiStateSkeleton;
    }

    public PaintedImagePane getPaintedImagePane(){
        if(paintedImagePane == null){
            throw new IllegalStateException("paintedImagePane is not set");
        }
        return paintedImagePane;
    }

    public void setPaintedImagePane(PaintedImagePane paintedImagePane){
        if(this.paintedImagePane != null){
            throw new IllegalStateException("paintedImagePane is already set");
        }
        this.paintedImagePane = paintedImagePane;
    }

    public LeftPaintPane getLeftPaintPane(){
        if(leftPaintPane == null){
            throw new IllegalStateException("leftPaintPane is not set");
        }
        return leftPaintPane;
    }

    public void setLeftPaintPane(LeftPaintPane leftPaintPane){
        if(this.leftPaintPane != null){
            throw new IllegalStateException("leftPaintPane is already set");
        }
        this.leftPaintPane = leftPaintPane;
    }

    public RightPaintPane getRightPaintPane(){
        if(rightPaintPane == null){
            throw new IllegalStateException("rightPaintPane is not set");
        }
        return rightPaintPane;
    }

    public void setRightPaintPane(RightPaintPane rightPaintPane){
        if(this.rightPaintPane != null){
            throw new IllegalStateException("rightPaintPane is already set");
        }
        this.rightPaintPane = rightPaintPane;
    }

    public TopPaintPane getTopPaintPane(){
        if(topPaintPane == null){
            throw new IllegalStateException("topPaintPane is not set");
        }
        return topPaintPane;
    }

    public void setTopPaintPane(TopPaintPane topPaintPane){
        if(this.topPaintPane != null){
            throw new IllegalStateException("topPaintPane is already set");
        }
        this.topPaintPane = topPaintPane;
    }

    public BottomPaintPane getBottomPaintPane(){
        if(bottomPaintPane == null){
            throw new IllegalStateException("bottomPaintPane is not set");
        }
        return bottomPaintPane;
    }

    public void setBottomPaintPane(BottomPaintPane bottomPaintPane){
        if(this.bottomPaintPane != null){
            throw new IllegalStateException("bottomPaintPane is already set");
        }
        this.bottomPaintPane = bottomPaintPane;
    }

    public PaintManager getPaintManager(){
        if(paintManager == null){
            throw new IllegalStateException("paintManager is not set");
        }
        return paintManager;
    }

    public void setPaintManager(PaintManager paintManager){
        if(this.paintManager != null){
            throw new IllegalStateException("paintManager is already set");
        }
        this.paintManager = paintManager;
    }

    public Size getScreenSize(){
        if(screenSize == null){
            throw new IllegalStateException("screenSize is not set");
        }
        return screenSize;
    }

    public void setScreenSize(Size screenSize){
        if(this.screenSize != null){
            throw new IllegalStateException("screenSize is already set");
        }
        this.screenSize = screenSize;
    }

    public HitboxRect getPaintedImagePaneBoundary(){
        if(paintedImagePaneBoundary == null){
            throw new IllegalStateException("paintedImagePaneBoundary is not set");
        }
        return paintedImagePaneBoundary;
    }

    public void setPaintedImagePaneBoundary(HitboxRect paintedImagePaneBoundary){
        if(this.paintedImagePaneBoundary != null){
            throw new IllegalStateException("paintedImagePaneBoundary is already set");
        }
        this.paintedImagePaneBoundary = paintedImagePaneBoundary;
    }

    public ChunkingGrid<PaintCentroidData> getMainCanvasChunkingGrid(){
        if(mainCanvasChunkingGrid == null){
            throw new IllegalStateException("mainCanvasChunkingGrid is not set");
        }
        return mainCanvasChunkingGrid;
    }

    public void setMainCanvasChunkingGrid(ChunkingGrid<PaintCentroidData> mainCanvasChunkingGrid){
        if(this.mainCanvasChunkingGrid != null){
            throw new IllegalStateException("mainCanvasChunkingGrid is already set");
        }
        this.mainCanvasChunkingGrid = mainCanvasChunkingGrid;
    }

    public PaletteImageComponent getPaletteImageComponent(){
        if(paletteImageComponent == null){
            throw new IllegalStateException("paletteImageComponent is not set");
        }
        return paletteImageComponent;
    }

    public void setPaletteImageComponent(PaletteImageComponent paletteImageComponent){
        if(this.paletteImageComponent != null){
            throw new IllegalStateException("paletteImageComponent is already set");
        }
        this.paletteImageComponent = paletteImageComponent;
    }

    public PaintCanvas getPalette(){
        if(palette == null){
            throw new IllegalStateException("palette is not set");
        }
        return palette;
    }

    public void setPalette(PaintCanvas palette){
        if(this.palette != null){
            throw new IllegalStateException("palette is already set");
        }
        this.palette = palette;
    }

    public PaintKeyProcessor getPaintKeyProcessor(){
        if(paintKeyProcessor == null){
            throw new IllegalStateException("paintKeyProcessor is not set");
        }
        return paintKeyProcessor;
    }

    public void setPaintKeyProcessor(PaintKeyProcessor paintKeyProcessor){
        if(this.paintKeyProcessor != null){
            throw new IllegalStateException("paintKeyProcessor is already set");
        }
        this.paintKeyProcessor = paintKeyProcessor;
    }

    public GridImageComponent getPaintImageComponent(){
        if(paintImageComponent == null){
            throw new IllegalStateException("paintImageComponent is not set");
        }
        return paintImageComponent;
    }

    public void setPaintImageComponent(GridImageComponent paintImageComponent){
        if(this.paintImageComponent != null){
            throw new IllegalStateException("paintImageComponent is already set");
        }
        this.paintImageComponent = paintImageComponent;
    }

    public BrushColorDisplay getBrushColorDisplay(){
        if(brushColorDisplay == null){
            throw new IllegalStateException("brushColorDisplay is not set");
        }
        return brushColorDisplay;
    }

    public void setBrushColorDisplay(BrushColorDisplay brushColorDisplay){
        if(this.brushColorDisplay != null){
            throw new IllegalStateException("brushColorDisplay is already set");
        }
        this.brushColorDisplay = brushColorDisplay;
    }

}
