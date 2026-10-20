package com.mason.mapgen.paint.components.panes.topPane.buttons;

import com.mason.libgui.components.buttons.Button;
import com.mason.libgui.components.deco.BasicButtonDeco;
import com.mason.libgui.components.deco.ButtonDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.*;
import com.mason.libgui.core.component.hitbox.BasicHitboxRect;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;

import java.util.Set;

import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.*;

public class TopPaintPaneButtonsBuildComponent extends ComponentBuildResource<Button[]>{


    public static final TopPaintPaneButtonsBuildComponent INSTANCE = new TopPaintPaneButtonsBuildComponent();


    private TopPaintPaneButtonsBuildComponent(){}


    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
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
        return new SaveAsButton(name,
                getBoundary(name),
                buildDeco(name),
                saveLocation,
                PAINT_GRID_QUERY.get());
    }

    private SaveButton buildSaveButton(SaveLocation saveLocation, SaveAsButton saveAsButton){
        String name = "SAVE_BUTTON";
        return new SaveButton(name,
                getBoundary(name),
                buildDeco(name),
                saveLocation,
                saveAsButton,
                PAINT_GRID_QUERY.get());
    }

    private LoadButton buildLoadButton(SaveLocation saveLocation, SaveButton saveButton){
        String name = "LOAD_BUTTON";
        return new LoadButton(name,
                getBoundary(name),
                buildDeco(name),
                saveLocation,
                saveButton,
                PAINT_GRID_LOADER.get());
    }

    private ExportButton buildExportButton(){
        String name = "EXPORT_BUTTON";
        return new ExportButton(name,
                getBoundary(name),
                buildDeco(name),
                PAINT_IMAGE_QUERY.get());
    }

    private UndoButton buildUndoButton(){
        String name = "UNDO_BUTTON";
        return new UndoButton(name,
                getBoundary(name),
                buildDeco(name),
                UNDO_CALLABLE.get());
    }

    private RedoButton buildRedoButton(){
        String name = "REDO_BUTTON";
        return new RedoButton(name,
                getBoundary(name),
                buildDeco(name),
                REDO_CALLABLE.get());
    }

    private HitboxRect getBoundary(String name){
        PaneLayout layout = TopPaintPaneBuildComponent.LAYOUT.get();
        Size size = new Size(36, 36);
        Coord coord = layout.centre(name, size);
        return new BasicHitboxRect(coord, size);
    }

    private ButtonDeco buildDeco(String name){
        return BasicButtonDeco.build("assets/paintIcons/" + name + ".png");
    }


    @Override
    public String toString(){
        return "TOP_PAINT_PANE_BUTTONS";
    }

}
