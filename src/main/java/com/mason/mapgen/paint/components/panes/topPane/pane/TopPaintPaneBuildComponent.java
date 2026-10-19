package com.mason.mapgen.paint.components.panes.topPane.pane;

import com.mason.libgui.components.buttons.Button;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.buttons.TopPaintPaneButtonsBuildComponent;

import java.util.Set;

public class TopPaintPaneBuildComponent extends ComponentBuildResource<TopPaintPane>{


    public static final TopPaintPaneBuildComponent INSTANCE = new TopPaintPaneBuildComponent();
    public static final SettableBuildResource<TopPaintPaneLayout> LAYOUT = new SettableBuildResource<>("TOP_PAINT_PANE_LAYOUT");


    private TopPaintPaneBuildComponent(){}


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
    protected TopPaintPane build(){
        PaneLayout guiStateLayout = PaintGUIStateLayoutBuildComponent.INSTANCE.get();
        RectQuery boundary = guiStateLayout.getBounds("TOP_PAINT_PANE");
        LAYOUT.setResource(new TopPaintPaneLayout(boundary.getSize()));
        return TopPaintPane.buildEmptyPane(boundary);
    }

    @Override
    public void postBuild(){
        TopPaintPane pane = this.get();

        for(Button button : TopPaintPaneButtonsBuildComponent.INSTANCE.get()) pane.addComponent(button);
    }


    @Override
    public String toString(){
        return "TOP_PAINT_PANE";
    }

}
