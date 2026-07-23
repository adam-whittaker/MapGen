package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libstruct.geo.Size;
import com.mason.mapgen.gui.states.GUIStateSkeleton;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.mapgen.paint.components.panes.bottomPane.BottomPaintPane;
import com.mason.mapgen.paint.components.panes.imagePane.PaintedImagePane;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPane;
import com.mason.mapgen.paint.components.panes.rightPane.RightPaintPane;
import com.mason.mapgen.paint.components.panes.topPane.TopPaintPane;
import com.mason.mapgen.paint.logic.PaintKeyProcessor;
import com.mason.mapgen.paint.logic.tools.PaintTool;
import com.mason.mapgen.paint.skeletons.PaintToolQuerySlot;

import java.util.function.Supplier;

public class PaintGUIStateSkeleton extends GUIStateSkeleton{


    private PaintedImagePane paintedImagePane;
    private LeftPaintPane leftPaintPane;
    private RightPaintPane rightPaintPane;
    private TopPaintPane topPaintPane;
    private BottomPaintPane bottomPaintPane;
    private Size screenSize;
    private PaintGUIStateLayout layout;
    private ChunkingGrid<PaintCentroidData> mainCanvasChunkingGrid;
    private PaintKeyProcessor paintKeyProcessor;
    private final PaintToolQuerySlot currentPaintToolQuery = new PaintToolQuerySlot();


    public PaintGUIStateSkeleton(){}


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

    public PaintGUIStateLayout getLayout(){
        if(layout == null){
            throw new IllegalStateException("layout is not set");
        }
        return layout;
    }

    public void setLayout(PaintGUIStateLayout layout){
        if(this.layout != null){
            throw new IllegalStateException("layout is already set");
        }
        this.layout = layout;
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

    public Supplier<PaintTool> getCurrentPaintToolQuery(){
        return currentPaintToolQuery;
    }

    public PaintToolQuerySlot getPaintToolQuerySlot(){
        return currentPaintToolQuery;
    }

}
