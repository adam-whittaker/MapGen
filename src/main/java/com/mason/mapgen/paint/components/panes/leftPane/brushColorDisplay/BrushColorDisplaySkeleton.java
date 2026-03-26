package com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.interfaces.RectQuery;
import com.mason.libgui.utils.structures.Size;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.skeletons.UpdaterSlot;
import com.mason.mapgen.procgen.algorithms.chunking.components.ChunkingGrid;

public class BrushColorDisplaySkeleton{


    private ChunkingGrid<PaintCentroidData> grid;
    private RectQuery boundary;

    private PaintCentroidData primaryColorCentroid;
    private PaintCentroidData secondaryColorCentroid;
    private PaintCentroidData averageColorCentroid;

    private RGBQuery primaryRGBQuery;
    private RGBQuery secondaryRGBQuery;
    private RGBQuery averageRGBQuery;

    private UpdaterSlot brushDisplayUpdateSlot;


    public BrushColorDisplaySkeleton(){}


    public ChunkingGrid<PaintCentroidData> getGrid(){
        if(grid == null){
            throw new IllegalStateException("grid is not set");
        }
        return grid;
    }

    public void setGrid(ChunkingGrid<PaintCentroidData> grid){
        if(this.grid != null){
            throw new IllegalStateException("grid is already set");
        }
        this.grid = grid;
    }

    public RectQuery getBoundary(){
        if(boundary == null){
            throw new IllegalStateException("boundary is not set");
        }
        return boundary;
    }

    public void setBoundary(RectQuery boundary){
        if(this.boundary != null){
            throw new IllegalStateException("boundary is already set");
        }
        this.boundary = boundary;
    }

    public Coord getCoord(){
        if(boundary == null){
            throw new IllegalStateException("boundary is not set");
        }
        return boundary.getCoord();
    }

    public Size getSize(){
        if(boundary == null){
            throw new IllegalStateException("boundary is not set");
        }
        return boundary.getSize();
    }

    public PaintCentroidData getPrimaryColorCentroid(){
        if(primaryColorCentroid == null){
            throw new IllegalStateException("primaryColorCentroid is not set");
        }
        return primaryColorCentroid;
    }

    public void setPrimaryColorCentroid(PaintCentroidData primaryColorCentroid){
        if(this.primaryColorCentroid != null){
            throw new IllegalStateException("primaryColorCentroid is already set");
        }
        this.primaryColorCentroid = primaryColorCentroid;
    }

    public PaintCentroidData getSecondaryColorCentroid(){
        if(secondaryColorCentroid == null){
            throw new IllegalStateException("secondaryColorCentroid is not set");
        }
        return secondaryColorCentroid;
    }

    public void setSecondaryColorCentroid(PaintCentroidData secondaryColorCentroid){
        if(this.secondaryColorCentroid != null){
            throw new IllegalStateException("secondaryColorCentroid is already set");
        }
        this.secondaryColorCentroid = secondaryColorCentroid;
    }

    public PaintCentroidData getAverageColorCentroid(){
        if(averageColorCentroid == null){
            throw new IllegalStateException("averageColorCentroid is not set");
        }
        return averageColorCentroid;
    }

    public void setAverageColorCentroid(PaintCentroidData averageColorCentroid){
        if(this.averageColorCentroid != null){
            throw new IllegalStateException("averageColorCentroid is already set");
        }
        this.averageColorCentroid = averageColorCentroid;
    }

    public RGBQuery getPrimaryRGBQuery(){
        if(primaryRGBQuery == null){
            throw new IllegalStateException("primaryRGBQuery is not set");
        }
        return primaryRGBQuery;
    }

    public void setPrimaryRGBQuery(RGBQuery primaryRGBQuery){
        if(this.primaryRGBQuery != null){
            throw new IllegalStateException("primaryRGBQuery is already set");
        }
        this.primaryRGBQuery = primaryRGBQuery;
    }

    public RGBQuery getSecondaryRGBQuery(){
        if(secondaryRGBQuery == null){
            throw new IllegalStateException("secondaryRGBQuery is not set");
        }
        return secondaryRGBQuery;
    }

    public void setSecondaryRGBQuery(RGBQuery secondaryRGBQuery){
        if(this.secondaryRGBQuery != null){
            throw new IllegalStateException("secondaryRGBQuery is already set");
        }
        this.secondaryRGBQuery = secondaryRGBQuery;
    }

    public RGBQuery getAverageRGBQuery(){
        if(averageRGBQuery == null){
            throw new IllegalStateException("averageRGBQuery is not set");
        }
        return averageRGBQuery;
    }

    public void setAverageRGBQuery(RGBQuery averageRGBQuery){
        if(this.averageRGBQuery != null){
            throw new IllegalStateException("averageRGBQuery is already set");
        }
        this.averageRGBQuery = averageRGBQuery;
    }

    public void setBrushDisplayUpdate(Runnable update){
        if(brushDisplayUpdateSlot == null){
            throw new IllegalStateException("brushDisplayUpdateSlot is not set");
        }
        brushDisplayUpdateSlot.setUpdate(update);
    }

    public void setBrushDisplayUpdaterSlot(UpdaterSlot brushDisplayUpdateSlot){
        if(this.brushDisplayUpdateSlot != null){
            throw new IllegalStateException("brushDisplayUpdateSlot is already set");
        }
        this.brushDisplayUpdateSlot = brushDisplayUpdateSlot;
    }

}
