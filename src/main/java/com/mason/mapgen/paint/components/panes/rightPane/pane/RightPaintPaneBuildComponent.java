package com.mason.mapgen.paint.components.panes.rightPane.pane;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.PaintLayersPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.otherButtons.CreateLayerButton;
import com.mason.mapgen.paint.components.panes.rightPane.referenceButtons.ReferenceTweakablesBuildComponent;

import java.util.Set;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.PAINT_GUI_PARAMS;
import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.CREATE_LAYER_UPDATE;

public class RightPaintPaneBuildComponent extends ComponentBuildResource<RightPaintPane>{


    public static final RightPaintPaneBuildComponent INSTANCE = new RightPaintPaneBuildComponent();
    public static final SettableBuildResource<PaneLayout> LAYOUT = new SettableBuildResource<>("RIGHT_PAINT_PANE_LAYOUT");


    private RightPaintPaneBuildComponent(){}


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PaintGUIStateLayoutBuildComponent.INSTANCE,
                PAINT_GUI_PARAMS,
                CREATE_LAYER_UPDATE
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                INSTANCE,
                LAYOUT
        );
    }

    @Override
    protected RightPaintPane build(){
        PaneLayout guiStateLayout = PaintGUIStateLayoutBuildComponent.INSTANCE.get();
        RectQuery boundary = guiStateLayout.getBounds("RIGHT_PAINT_PANE");
        LAYOUT.setResource(new RightPaintPaneLayout(boundary.getSize()));
        RightPaintPane pane = RightPaintPane.buildEmptyPane(boundary);
        pane.addComponent(buildCreateLayerButton());
        return pane;
    }

    private UIComponent buildCreateLayerButton(){
        PaneLayout layout = LAYOUT.get();
        Size buttonSize = PAINT_GUI_PARAMS.get().toggleSize();
        Coord coord = layout.centre("CREATE_LAYER_BUTTON", buttonSize);
        return new CreateLayerButton(
                HitboxRect.build(coord, buttonSize),
                CREATE_LAYER_UPDATE.get()
        );
    }


    @Override
    public void postBuild(){
        Pane pane = this.get();

        for(UIComponent component : ReferenceTweakablesBuildComponent.REFERENCE_TWEAKABLES.get()){
            pane.addComponent(component);
        }

        Pane paintLayersPane = PaintLayersPaneBuildComponent.INSTANCE.get();
        pane.addComponent(paintLayersPane);
    }


    @Override
    public String toString(){
        return "RIGHT_PAINT_PANE";
    }

}
