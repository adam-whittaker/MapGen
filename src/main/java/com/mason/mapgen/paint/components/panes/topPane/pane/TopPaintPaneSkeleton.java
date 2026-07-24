package com.mason.mapgen.paint.components.panes.topPane.pane;

import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.panes.topPane.resources.*;
import com.mason.mapgen.paint.components.panes.topPane.buttons.*;

public class TopPaintPaneSkeleton extends PaneSkeleton{


    private PaneLayout paneLayout;
    private Size buttonSize;
    private PaintGridQuery gridQuery;
    private PaintGridReceiver gridReceiver;
    private ImageQuery imageQuery;
    private Runnable undoCallable;
    private Runnable redoCallable;
    private SaveLocation saveLocation;
    private SaveAsButton saveAsButton;
    private SaveButton saveButton;
    private LoadButton loadButton;
    private ExportButton exportButton;
    private UndoButton undoButton;
    private RedoButton redoButton;


    public TopPaintPaneSkeleton(){}


    public PaneLayout getPaneLayout(){
        if(paneLayout == null){
            throw new IllegalStateException("paneLayout is not set");
        }
        return paneLayout;
    }

    public void setPaneLayout(PaneLayout paneLayout){
        if(this.paneLayout != null){
            throw new IllegalStateException("paneLayout is already set");
        }
        this.paneLayout = paneLayout;
    }

    public PaintGridReceiver getGridReceiver(){
        if(gridReceiver == null){
            throw new IllegalStateException("gridReceiver is not set");
        }
        return gridReceiver;
    }

    public void setGridReceiver(PaintGridReceiver gridReceiver){
        if(this.gridReceiver != null){
            throw new IllegalStateException("gridReceiver is already set");
        }
        this.gridReceiver = gridReceiver;
    }

    public Size getButtonSize(){
        if(buttonSize == null){
            throw new IllegalStateException("buttonSize is not set");
        }
        return buttonSize;
    }

    public void setButtonSize(Size buttonSize){
        if(this.buttonSize != null){
            throw new IllegalStateException("buttonSize is already set");
        }
        this.buttonSize = buttonSize;
    }

    public PaintGridQuery getGridQuery(){
        if(gridQuery == null){
            throw new IllegalStateException("gridQuery is not set");
        }
        return gridQuery;
    }

    public void setGridQuery(PaintGridQuery gridQuery){
        if(this.gridQuery != null){
            throw new IllegalStateException("gridQuery is already set");
        }
        this.gridQuery = gridQuery;
    }

    public ImageQuery getImageQuery(){
        if(imageQuery == null){
            throw new IllegalStateException("imageQuery is not set");
        }
        return imageQuery;
    }

    public void setImageQuery(ImageQuery imageQuery){
        if(this.imageQuery != null){
            throw new IllegalStateException("imageQuery is already set");
        }
        this.imageQuery = imageQuery;
    }

    public Runnable getUndoCallable(){
        if(undoCallable == null){
            throw new IllegalStateException("undoCallable is not set");
        }
        return undoCallable;
    }

    public void setUndoCallable(Runnable undoCallable){
        if(this.undoCallable != null){
            throw new IllegalStateException("undoCallable is already set");
        }
        this.undoCallable = undoCallable;
    }

    public Runnable getRedoCallable(){
        if(redoCallable == null){
            throw new IllegalStateException("redoCallable is not set");
        }
        return redoCallable;
    }

    public void setRedoCallable(Runnable redoCallable){
        if(this.redoCallable != null){
            throw new IllegalStateException("redoCallable is already set");
        }
        this.redoCallable = redoCallable;
    }

    public SaveLocation getSaveLocation(){
        if(saveLocation == null){
            throw new IllegalStateException("saveLocation is not set");
        }
        return saveLocation;
    }

    public void setSaveLocation(SaveLocation saveLocation){
        if(this.saveLocation != null){
            throw new IllegalStateException("saveLocation is already set");
        }
        this.saveLocation = saveLocation;
    }

    public SaveAsButton getSaveAsButton(){
        if(saveAsButton == null){
            throw new IllegalStateException("saveAsButton is not set");
        }
        return saveAsButton;
    }

    public void setSaveAsButton(SaveAsButton saveAsButton){
        if(this.saveAsButton != null){
            throw new IllegalStateException("saveAsButton is already set");
        }
        this.saveAsButton = saveAsButton;
    }

    public SaveButton getSaveButton(){
        if(saveButton == null){
            throw new IllegalStateException("saveButton is not set");
        }
        return saveButton;
    }

    public void setSaveButton(SaveButton saveButton){
        if(this.saveButton != null){
            throw new IllegalStateException("saveButton is already set");
        }
        this.saveButton = saveButton;
    }

    public LoadButton getLoadButton(){
        if(loadButton == null){
            throw new IllegalStateException("loadButton is not set");
        }
        return loadButton;
    }

    public void setLoadButton(LoadButton loadButton){
        if(this.loadButton != null){
            throw new IllegalStateException("loadButton is already set");
        }
        this.loadButton = loadButton;
    }

    public ExportButton getExportButton(){
        if(exportButton == null){
            throw new IllegalStateException("exportButton is not set");
        }
        return exportButton;
    }

    public void setExportButton(ExportButton exportButton){
        if(this.exportButton != null){
            throw new IllegalStateException("exportButton is already set");
        }
        this.exportButton = exportButton;
    }

    public UndoButton getUndoButton(){
        if(undoButton == null){
            throw new IllegalStateException("undoButton is not set");
        }
        return undoButton;
    }

    public void setUndoButton(UndoButton undoButton){
        if(this.undoButton != null){
            throw new IllegalStateException("undoButton is already set");
        }
        this.undoButton = undoButton;
    }

    public RedoButton getRedoButton(){
        if(redoButton == null){
            throw new IllegalStateException("redoButton is not set");
        }
        return redoButton;
    }

    public void setRedoButton(RedoButton redoButton){
        if(this.redoButton != null){
            throw new IllegalStateException("redoButton is already set");
        }
        this.redoButton = redoButton;
    }

}
