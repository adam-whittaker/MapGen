package com.mason.mapgen.paint.components.panes.bottomPane.pane;

import com.mason.libgui.components.buttons.Button;
import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.buttons.TopPaintPaneButtonsBuildComponent;

import java.util.Set;

public class BottomPaintPaneBuildComponent extends ComponentBuildResource<BottomPaintPane>{


    public static final BottomPaintPaneBuildComponent INSTANCE = new BottomPaintPaneBuildComponent();
    public static final SettableBuildResource<PaneLayout> LAYOUT = new SettableBuildResource<>("BOTTOM_PAINT_PANE");


    private BottomPaintPaneBuildComponent(){}


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
    protected BottomPaintPane build(){
        PaneLayout guiStateLayout = PaintGUIStateLayoutBuildComponent.INSTANCE.get();
        RectQuery boundary = guiStateLayout.getBounds("BOTTOM_PAINT_PANE");
        //@Unfinished
        //LAYOUT.setResource(new BottomPaintPaneLayout(boundary.getSize()));
        return BottomPaintPane.buildEmptyPane(boundary);
    }

    @Override
    public void postBuild(){
        Pane pane = this.get();
    }


    @Override
    public String toString(){
        return "BOTTOM_PAINT_PANE";
    }

}

