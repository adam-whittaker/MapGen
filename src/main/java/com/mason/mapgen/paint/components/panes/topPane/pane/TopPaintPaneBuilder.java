package com.mason.mapgen.paint.components.panes.topPane.pane;

import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateSkeleton;
import com.mason.mapgen.paint.components.panes.topPane.resources.SaveLocation;
import com.mason.mapgen.paint.components.panes.topPane.save.*;

public class TopPaintPaneBuilder{


    public static TopPaintPaneSkeleton buildSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        TopPaintPaneSkeleton skeleton = buildSkeletonWithInitialFieldsFromPaintGUIStateSkeleton(paintGUIStateSkeleton);
        setUpPaneLayout(skeleton);
        setUpButtons(skeleton);
        return skeleton;
    }

    private static TopPaintPaneSkeleton buildSkeletonWithInitialFieldsFromPaintGUIStateSkeleton(PaintGUIStateSkeleton paintGUIStateSkeleton){
        TopPaintPaneSkeleton skeleton = new TopPaintPaneSkeleton();
        PaneLayout layout = paintGUIStateSkeleton.getLayout();
        RectQuery boundary = layout.getBounds("TOP_PAINT_PANE");
        skeleton.setBoundary(boundary);
        skeleton.setGridQuery(paintGUIStateSkeleton.getPaintGridQuery());
        skeleton.setGridReceiver(paintGUIStateSkeleton.getPaintGridReceiver());
        skeleton.setImageQuery(paintGUIStateSkeleton.getPaintImageQuery());
        return skeleton;
    }

    private static void setUpPaneLayout(TopPaintPaneSkeleton skeleton){
        PaneLayout layout = new TopPaintPaneLayout(skeleton.getBoundary().getSize());
        skeleton.setPaneLayout(layout);
    }

    private static void setUpButtons(TopPaintPaneSkeleton skeleton){
        skeleton.setButtonSize(new Size(36, 36));
        skeleton.setSaveLocation(new SaveLocation());

        SaveAsButton saveAsButton = SaveAsButton.build(skeleton);
        skeleton.setSaveAsButton(saveAsButton);
        skeleton.addComponent(saveAsButton);

        SaveButton saveButton = SaveButton.build(skeleton);
        skeleton.setSaveButton(saveButton);
        skeleton.addComponent(saveButton);

        LoadButton loadButton = LoadButton.build(skeleton);
        skeleton.setLoadButton(loadButton);
        skeleton.addComponent(loadButton);

        ExportButton exportButton = ExportButton.build(skeleton);
        skeleton.setExportButton(exportButton);
        skeleton.addComponent(exportButton);
    }

}
