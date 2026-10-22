package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.Button;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.*;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;

import java.util.Set;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.PAINT_GUI_PARAMS;
import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.*;

public class TopPaintPaneButtonsBuildComponent extends ComponentBuildResource<Button[]>{


    public static final TopPaintPaneButtonsBuildComponent INSTANCE = new TopPaintPaneButtonsBuildComponent();


    private TopPaintPaneButtonsBuildComponent(){}


    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PAINT_GUI_PARAMS,
                PAINT_IMAGE_QUERY,
                PAINT_GRID_LOADER,
                UNDO_CALLABLE,
                REDO_CALLABLE,
                TopPaintPaneBuildComponent.LAYOUT
        );
    }

    @Override
    protected Button[] build(){
        SaveLocation saveLocation = new SaveLocation();
        Button[] buttons = new Button[6];

        buttons[0] = buildSaveAsButton(saveLocation);
        buttons[1] = buildSaveButton(saveLocation, (SaveAsButton) buttons[0]);
        buttons[2] = buildLoadButton(saveLocation, (SaveButton) buttons[1]);
        buttons[3] = buildExportButton();
        buttons[4] = buildUndoButton();
        buttons[5] = buildRedoButton();

        return buttons;
    }

    private SaveAsButton buildSaveAsButton(SaveLocation saveLocation){
        String name = "SAVE_AS_BUTTON";
        return new SaveAsButton(
                getBoundary(name),
                saveLocation,
                PAINT_GRID_QUERY.get());
    }

    private SaveButton buildSaveButton(SaveLocation saveLocation, SaveAsButton saveAsButton){
        String name = "SAVE_BUTTON";
        return new SaveButton(
                getBoundary(name),
                saveLocation,
                saveAsButton,
                PAINT_GRID_QUERY.get());
    }

    private LoadButton buildLoadButton(SaveLocation saveLocation, SaveButton saveButton){
        String name = "LOAD_BUTTON";
        return new LoadButton(
                getBoundary(name),
                saveLocation,
                saveButton,
                PAINT_GRID_LOADER.get());
    }

    private ExportButton buildExportButton(){
        String name = "EXPORT_BUTTON";
        return new ExportButton(
                getBoundary(name),
                PAINT_IMAGE_QUERY.get());
    }

    private UndoButton buildUndoButton(){
        String name = "UNDO_BUTTON";
        return new UndoButton(
                getBoundary(name),
                UNDO_CALLABLE.get());
    }

    private RedoButton buildRedoButton(){
        String name = "REDO_BUTTON";
        return new RedoButton(
                getBoundary(name),
                REDO_CALLABLE.get());
    }

    private HitboxRect getBoundary(String name){
        PaneLayout layout = TopPaintPaneBuildComponent.LAYOUT.get();
        Size size = PAINT_GUI_PARAMS.get().toggleSize();
        Coord coord = layout.centre(name, size);
        return new BasicHitboxRect(coord, size);
    }


    @Override
    public String toString(){
        return "TOP_PAINT_PANE_BUTTONS";
    }

}
