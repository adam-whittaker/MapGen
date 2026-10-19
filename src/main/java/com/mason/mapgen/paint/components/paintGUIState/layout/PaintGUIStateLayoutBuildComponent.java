package com.mason.mapgen.paint.components.paintGUIState.layout;

import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.ComponentBuildResource;
import com.mason.libstruct.geo.Size;
import com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.GUIParams;

import java.util.Set;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.PAINT_GUI_PARAMS;
import static java.lang.Math.min;

public class PaintGUIStateLayoutBuildComponent extends ComponentBuildResource<PaintGUIStateLayout>{


    public final static PaintGUIStateLayoutBuildComponent INSTANCE = new PaintGUIStateLayoutBuildComponent();

    private static final int MIN_VERTICAL_PANE_HEIGHT = 48;
    private static final int MIN_HORIZONTAL_PANE_WIDTH = 396;


    private PaintGUIStateLayoutBuildComponent(){
        super();
    }


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(PAINT_GUI_PARAMS);
    }

    @Override
    public PaintGUIStateLayout build(){
        GUIParams guiParams = PAINT_GUI_PARAMS.get();
        PaintGUIStateLayoutParameters layoutParams = constructEvenLayoutParameters(guiParams.screenSize(), guiParams.sidePaneWidth());
        verifyParamsLargeEnough(layoutParams);
        return new PaintGUIStateLayout(layoutParams);
    }

    private static PaintGUIStateLayoutParameters constructEvenLayoutParameters(Size screenSize, int sidePaneWidth){
        double imageDimension = screenSize.width() - 2 * sidePaneWidth;
        double verticalPaneHeight = (screenSize.height() - imageDimension)/2;
        double horizontalRatio = (double)sidePaneWidth/screenSize.width();
        double verticalRatio = verticalPaneHeight/screenSize.height();

        PaintGUIStateLayoutParameters params = new PaintGUIStateLayoutParameters();
        params.setScreenSize(screenSize);
        params.setLeftPaneWidthRatio(horizontalRatio);
        params.setRightPaneWidthRatio(horizontalRatio);
        params.setTopPaneHeightRatio(verticalRatio);
        params.setBottomPaneHeightRatio(verticalRatio);
        return params;
    }

    private static void verifyParamsLargeEnough(PaintGUIStateLayoutParameters params){
        Size size = params.getScreenSize();
        double verticalPaneRatio = min(params.getBottomPaneHeightRatio(), params.getTopPaneHeightRatio());
        double horizontalPaneRatio = min(params.getLeftPaneWidthRatio(), params.getRightPaneWidthRatio());
        if(verticalPaneRatio * size.height() < MIN_VERTICAL_PANE_HEIGHT){
            throw new IllegalArgumentException("vertical pane too small.");
        }
        if(horizontalPaneRatio * size.width() < MIN_HORIZONTAL_PANE_WIDTH){
            throw new IllegalArgumentException("horizontal pane too small.");
        }
    }


    @Override
    public String toString(){
        return "PAINT_GUI_STATE_LAYOUT";
    }

}
