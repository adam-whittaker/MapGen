package com.mason.mapgen.paint.components.paintGUIState;

import com.mason.libgui.construction.BuildComponentCollection;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libstruct.geo.Size;
import com.mason.libvoronoi.algorithms.FloodFillAnnexQuery;
import com.mason.mapgen.paint.components.panes.imagePane.MainPaintGridBuildComponent;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.paint.components.paintGUIState.layout.PaintGUIStateLayoutBuildComponent;
import com.mason.mapgen.paint.components.panes.bottomPane.pane.BottomPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.PaintedImagePaneBuildComponent;
import com.mason.mapgen.paint.components.panes.imagePane.versionedCanvasController.VersionedCanvasControllerBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.brushColorDisplay.BrushColorDisplayBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.PaintControlSettingsBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.colorState.ColorStatesBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.colorSelector.ColorSelectorBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.paletteCanvas.PaletteCanvasControllerBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.pane.LeftPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.sliders.BrushSlidersBuildComponent;
import com.mason.mapgen.paint.components.panes.leftPane.toggles.LeftPaintPaneTogglesBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.pane.RightPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageAccessSlotsBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.referenceButtons.ReferenceTweakablesBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.buttons.TopPaintPaneButtonsBuildComponent;
import com.mason.mapgen.paint.components.panes.topPane.pane.TopPaintPaneBuildComponent;
import com.mason.mapgen.paint.logic.keyProcessor.PaintKeyProcessorBuildComponent;
import com.mason.mapgen.paint.logic.tools.PaintToolKitBuildComponent;

public class PaintGUIStateBuildCollection extends BuildComponentCollection{


    public static final SettableBuildResource<ChunkingGridParams> CHUNKING_GRID_PARAMS = new SettableBuildResource<>("CHUNKING_GRID_PARAMS");
    public static final SettableBuildResource<GUIParams> PAINT_GUI_PARAMS = new SettableBuildResource<>("PAINT_GUI_PARAMS");


    public PaintGUIStateBuildCollection(GUIParams guiParams, ChunkingGridParams chunkingGridParams){
        PAINT_GUI_PARAMS.setResource(guiParams);
        CHUNKING_GRID_PARAMS.setResource(chunkingGridParams);
        addResource(PAINT_GUI_PARAMS);
        addResource(CHUNKING_GRID_PARAMS);

        addComponent(PaintGUIStateBuildComponent.INSTANCE);
        addComponent(PaintToolKitBuildComponent.INSTANCE);
        addComponent(PaintGUIStateLayoutBuildComponent.INSTANCE);
        addComponent(PaintKeyProcessorBuildComponent.INSTANCE);

        addComponent(PaintedImagePaneBuildComponent.INSTANCE);
        addComponent(MainPaintGridBuildComponent.INSTANCE);
        addComponent(VersionedCanvasControllerBuildComponent.INSTANCE);
        addComponent(ReferenceImageBuildComponent.INSTANCE);
        addComponent(ReferenceImageAccessSlotsBuildComponent.INSTANCE);

        addComponent(LeftPaintPaneBuildComponent.INSTANCE);
        addComponent(BrushColorDisplayBuildComponent.INSTANCE);
        addComponent(ColorSelectorBuildComponent.INSTANCE);
        addComponent(LeftPaintPaneTogglesBuildComponent.INSTANCE);
        addComponent(BrushSlidersBuildComponent.INSTANCE);
        addComponent(PaletteCanvasControllerBuildComponent.INSTANCE);
        addComponent(PaintControlSettingsBuildComponent.INSTANCE);
        addComponent(ColorStatesBuildComponent.INSTANCE);

        addComponent(TopPaintPaneBuildComponent.INSTANCE);
        addComponent(TopPaintPaneButtonsBuildComponent.INSTANCE);

        addComponent(RightPaintPaneBuildComponent.INSTANCE);
        addComponent(ReferenceTweakablesBuildComponent.INSTANCE);

        addComponent(BottomPaintPaneBuildComponent.INSTANCE);
    }


    public PaintGUIState getPaintGUIState(){
        return PaintGUIStateBuildComponent.INSTANCE.get();
    }


    public record ChunkingGridParams(Size size, int numChunks, int lloydRelaxCount, FloodFillAnnexQuery<PaintCentroidData> query){}

    public record GUIParams(Size screenSize, int sidePaneWidth, Size toggleSize){}

}
