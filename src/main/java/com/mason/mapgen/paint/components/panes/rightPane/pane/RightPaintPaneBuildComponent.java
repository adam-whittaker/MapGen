package com.mason.mapgen.paint.components.panes.rightPane.pane;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.referenceButtons.ReferenceTweakablesBuildComponent;

import java.util.Set;

public class RightPaintPaneBuildComponent extends ComponentBuildResource<RightPaintPane>{


    public static final RightPaintPaneBuildComponent INSTANCE = new RightPaintPaneBuildComponent();
    public static final SettableBuildResource<PaneLayout> LAYOUT = new SettableBuildResource<>("RIGHT_PAINT_PANE_LAYOUT");


    private RightPaintPaneBuildComponent(){}


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PaintGUIStateLayoutBuildComponent.INSTANCE
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
        return RightPaintPane.buildEmptyPane(boundary);
    }

    @Override
    public void postBuild(){
        Pane pane = this.get();

        for(UIComponent component : ReferenceTweakablesBuildComponent.REFERENCE_TWEAKABLES.get()){
            pane.addComponent(component);
        }
    }


    @Override
    public String toString(){
        return "RIGHT_PAINT_PANE";
    }

}
