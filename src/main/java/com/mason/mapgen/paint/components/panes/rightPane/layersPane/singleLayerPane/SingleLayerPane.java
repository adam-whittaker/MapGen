package com.mason.mapgen.paint.components.panes.rightPane.layersPane.singleLayerPane;

import com.mason.libgui.components.panes.Pane;
import com.mason.libgui.components.panes.construction.PaneSkeleton;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.Layer;

import java.awt.*;
import java.util.function.Function;

public class SingleLayerPane extends Pane{


    private static final Color SELECTED_COLOR = new Color(200, 200, 255, 50);
    private final OnOffQuery selectedQuery;


    private SingleLayerPane(PaneSkeleton skeleton, OnOffQuery selectedQuery){
        super(skeleton);
        this.selectedQuery = selectedQuery;
    }

    public static SingleLayerPane buildSingleLayerPane(
            SizeParams sizeParams,
            Layer layer,
            IntQuery orderQuery,
            IntState currentLayerState
    ){
        HitboxRect boundary = buildBoundary(sizeParams.paneSize, orderQuery);
        PaneSkeleton skeleton = new PaneSkeleton();
        skeleton.setBoundary(boundary);
        for(UIComponent component : buildComponents(sizeParams, layer, currentLayerState)){
            skeleton.addComponent(component);
        }
        return new SingleLayerPane(
                skeleton,
                () -> layer.getLayerID() == currentLayerState.getState()
        );
    }

    private static HitboxRect buildBoundary(Size size, IntQuery orderQuery){
        return new HitboxRect(){

            @Override
            public void setCoord(Coord coord){
                throw new IllegalStateException("Shouldn't be manually moving sourced pane");
            }

            @Override
            public Size getSize(){
                return size;
            }

            @Override
            public Coord getCoord(){
                return new Coord(0, orderQuery.getState() * size.height());
            }
        };
    }

    private static UIComponent[] buildComponents(SizeParams sizeParams, Layer layer, IntState currentLayerState){
        PaneLayout layout = new SingleLayerPaneLayout(sizeParams.paneSize);
        UIComponent[] components = new UIComponent[3];
        components[0] = buildThumbnail(layer, layout, currentLayerState);
        components[1] = buildAlphaSlider(layer, layout);
        components[2] = buildExportButton(layer, layout, sizeParams.buttonSize);
        return components;
    }

    private static UIComponent buildThumbnail(Layer layer, PaneLayout layout, IntState currentLayerState){
        Size thumbnailSize = new Size(48, 48);
        RectQuery thumbnailBox = Rect.buildRect(
                layout.centre("LAYER_THUMBNAIL", thumbnailSize),
                thumbnailSize
        );
        Function<RectQuery, ? extends UIComponent> thumbnailFactory = layer.getThumbnailBuilder(currentLayerState);
        return thumbnailFactory.apply(thumbnailBox);
    }

    private static UIComponent buildAlphaSlider(Layer layer, PaneLayout layout){
        RectQuery sliderBox = layout.getBounds("LAYER_ALPHA_SLIDER");
        Function<RectQuery, ? extends UIComponent> factory = layer.getAlphaSliderBuilder();
        return factory.apply(sliderBox);
    }

    private static UIComponent buildExportButton(Layer layer, PaneLayout layout, Size buttonSize){
        RectQuery buttonBox = Rect.buildRect(
                layout.centre("LAYER_EXPORT_BUTTON", buttonSize),
                buttonSize
        );
        Function<RectQuery, ? extends UIComponent> factory = layer.getExportButtonBuilder();
        return factory.apply(buttonBox);
    }


    //Exists to broaden scope.
    @Override
    public BoundedMouseInputListener getInputTranslator(){
        return super.getInputTranslator();
    }


    @Override
    protected void drawBackground(Graphics2D g){
        if(selectedQuery.isOn()){
            g.setColor(SELECTED_COLOR);
            g.fillRect(0, 0, width(), height());
        }
    }


    public record SizeParams(Size paneSize, Size buttonSize){}

}
