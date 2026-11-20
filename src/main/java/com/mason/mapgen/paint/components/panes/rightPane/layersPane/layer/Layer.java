package com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer;

import com.mason.libgui.components.buttons.Button;
import com.mason.libgui.components.deco.BasicSliderDeco;
import com.mason.libgui.components.sliders.Slider;
import com.mason.libgui.components.sliders.sliderPositionState.*;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIcons;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsBuilder;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsSkeleton;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.Movable;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.mapgen.paint.components.misc.GridImageComponent;
import com.mason.mapgen.paint.components.misc.PaintGrid;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.singleLayerPane.LayerThumbnail;
import com.mason.mapgen.paint.components.panes.topPane.buttons.ExportButton;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.Function;

public class Layer extends GridImageComponent{


    private static final Coord TOP_LEFT = new Coord(0, 0);
    private final PrebuiltSliderPositionState alphaState;
    private String name;


    protected Layer(
            PaintGrid grid,
            int layerID
    ){
        super(TOP_LEFT, grid, layerID);
        this.alphaState = new PrebuiltSliderPositionState(Movable.buildDefaultMovable(new Coord(-1, -1)));
        this.name = "LAYER_" + layerID;
    }


    @Override
    public void render(Graphics2D g){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                (float) alphaState.getPosition()
        ));

        super.render(g2);

        g2.dispose();
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    //Exists to broaden scope
    @Override
    public int getLayerID(){
        return super.getLayerID();
    }

    public Function<RectQuery, LayerThumbnail> getThumbnailBuilder(IntState currentLayerState){
        return (bounds) -> new LayerThumbnail(
                bounds,
                super::render,
                getSize(),
                () -> currentLayerState.setState(getLayerID())
        );
    }

    public Function<RectQuery, Slider> getAlphaSliderBuilder(){
        return (bounds) -> {
            int sliderHandleWidth = 12;
            SliderWithIconsBuilder sliderBuilder = new SliderWithIconsBuilder(6, new BasicSliderDeco(), new Size(sliderHandleWidth, 24));
            SliderWithIconsSkeleton skeleton = sliderBuilder.buildSkeleton(
                    bounds,
                    "assets/paintIcons/transparent.png",
                    "assets/paintIcons/opaque.png",
                    alphaState.packageIntoFactory(1, sliderHandleWidth));
            return new SliderWithIcons(skeleton);
        };
    }

    public Function<RectQuery, Button> getExportButtonBuilder(){
        return (bounds) -> new ExportButton(
                HitboxRect.fromRect(bounds),
                this::getImage
        );
    }


    public void writeToDataStream(DataOutputStream out) throws IOException{
        out.writeUTF(name);
    }

    public static Layer readFromDataStream(DataInputStream in, int layerID, PaintGrid grid) throws IOException{
        String name = in.readUTF();
        Layer layer = new Layer(grid, layerID);
        layer.setName(name);
        return layer;
    }

}
