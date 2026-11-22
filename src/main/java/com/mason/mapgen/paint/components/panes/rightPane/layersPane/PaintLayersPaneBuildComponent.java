package com.mason.mapgen.paint.components.panes.rightPane.layersPane;

import com.mason.libgui.components.behaviour.camera.ScrollPanBehaviour;
import com.mason.libgui.components.behaviour.camera.ViewportBehaviour;
import com.mason.libgui.components.panes.construction.ScrollPanPaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.componentManagement.UIComponentManager;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.setters.ConsumerSetter;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.singleLayerPane.SingleLayerPane;
import com.mason.mapgen.paint.components.panes.rightPane.pane.RightPaintPaneBuildComponent;

import java.awt.*;
import java.util.Set;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.PAINT_GUI_PARAMS;
import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.LAYER_COUNT_QUERY;
import static com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent.LAYER_MANAGER_QUERY;

public class PaintLayersPaneBuildComponent extends ComponentBuildResource<PaintLayersPane>{


    public static final PaintLayersPaneBuildComponent INSTANCE = new PaintLayersPaneBuildComponent();
    public static final BuildResource<IntState> CURRENT_LAYER_STATE = BuildResource.getPreBuiltResource(IntState.defaultInstance(), "CURRENT_LAYER_STATE");


    private PaintLayersPaneBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                RightPaintPaneBuildComponent.LAYOUT,
                LAYER_COUNT_QUERY,
                LAYER_MANAGER_QUERY
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                INSTANCE,
                CURRENT_LAYER_STATE
        );
    }

    @Override
    public PaintLayersPane build(){
        int minLayerNum = 4;
        IntQuery layerCountQuery = LAYER_COUNT_QUERY.get();
        HitboxRect boundary = buildPaneBoundary();
        SingleLayerPane.SizeParams singleLayerPaneSizeParams = buildSingleLayerPaneSizeParams(boundary, minLayerNum);
        RectQuery clampingRect = new PaintLayersPaneClampRect(boundary, layerCountQuery, minLayerNum);

        ScrollPanPaneSkeleton skeleton = buildPaneSkeleton(boundary, clampingRect, singleLayerPaneSizeParams);

        return new PaintLayersPane(skeleton);
    }

    private static HitboxRect buildPaneBoundary(){
        PaneLayout layout = RightPaintPaneBuildComponent.LAYOUT.get();
        return HitboxRect.fromRect(layout.getBounds("PAINT_LAYERS_PANE"));
    }

    private SingleLayerPane.SizeParams buildSingleLayerPaneSizeParams(RectQuery boundary, int minLayerNum){
        return new SingleLayerPane.SizeParams(
                new Size(boundary.width(), boundary.height()/minLayerNum),
                PAINT_GUI_PARAMS.get().toggleSize()
        );
    }

    private ScrollPanPaneSkeleton buildPaneSkeleton(HitboxRect boundary, RectQuery clampingRect, SingleLayerPane.SizeParams singleLayerPaneSizeParams){
        ScrollPanPaneSkeleton skeleton = new ScrollPanPaneSkeleton();
        skeleton.setBoundary(boundary);

        ConsumerSetter.ConsumerSlot<Graphics2D> renderableSlot = new ConsumerSetter.ConsumerSlot<>();
        skeleton.setRenderableSetter(renderableSlot);

        ScrollPanBehaviour behaviour = ScrollPanBehaviour.buildDefaultVerticalBehaviour(
                renderableSlot,
                clampingRect,
                Rect.buildRect(new Coord(0, 0), boundary.getSize())
        );
        skeleton.setBehaviour(behaviour);

        UIComponentManager componentManager = buildComponentManager(singleLayerPaneSizeParams, behaviour);
        skeleton.setComponentManager(componentManager);

        return skeleton;
    }

    private UIComponentManager buildComponentManager(SingleLayerPane.SizeParams singleLayerPaneSizeParams, ViewportBehaviour behaviour){
        return PaintLayersPaneComponentManager.build(
                LAYER_MANAGER_QUERY.get(),
                singleLayerPaneSizeParams,
                CURRENT_LAYER_STATE.get(),
                behaviour
        );
    }


    @Override
    public String toString(){
        return "PAINT_LAYERS_PANE";
    }

}
