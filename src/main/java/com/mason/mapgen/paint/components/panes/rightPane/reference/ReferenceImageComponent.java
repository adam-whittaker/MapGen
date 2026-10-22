package com.mason.mapgen.paint.components.panes.rightPane.reference;

import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.componentLayer.GUIInputRegister;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.InputDelegator;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.onOff.OnOffQuery;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.logic.tools.colorPicker.ColorPicker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class ReferenceImageComponent implements UIComponent, RectQuery, InputDelegator, HitboxRect{


    private BufferedImage image;
    private final PositionQuery alphaQuery;
    private final OnOffQuery canGrab;
    private final ZoomableDragBehaviour dragBehaviour;
    private final Consumer<ColorPicker.ColorSetterInput> colorSetter;

    private Coord coord = new Coord(0, 0);


    public ReferenceImageComponent(PositionQuery alphaQuery,
                                   ImageUpdate.ImageUpdateSetter imageUpdateSetter,
                                   RectQuery parentBounds,
                                   OnOffQuery canGrab,
                                   Consumer<ColorPicker.ColorSetterInput> colorSetter){
        this.alphaQuery = alphaQuery;
        this.canGrab = canGrab;
        this.dragBehaviour = new ZoomableDragBehaviour(this, this::setColorPickerColor);
        imageUpdateSetter.setImageUpdate(constructImageUpdate(parentBounds));
        this.colorSetter = colorSetter;
    }

    private void setColorPickerColor(MouseInputEvent mouseInputEvent){
        double zoom = dragBehaviour.getZoom();
        Coord mouseCoord = mouseInputEvent.getCoord();
        int pixelX = (int) ((mouseCoord.x() - x()) / zoom);
        int pixelY = (int) ((mouseCoord.y() - y()) / zoom);
        colorSetter.accept(new ColorPicker.ColorSetterInput(
                new Color(image.getRGB(pixelX, pixelY), false),
                mouseInputEvent
        ));
    }

    private ImageUpdate constructImageUpdate(RectQuery panePanClamp){
        return new ImageUpdate(){
            @Override
            public void clearImage(){
                image = null;
            }

            @Override
            public void setImage(BufferedImage image){
                ReferenceImageComponent.this.image = image;
                recentreImage();
            }

            @Override
            public void recentreImage(){
                int imgWidth = ReferenceImageComponent.this.image.getWidth();
                int imgHeight = ReferenceImageComponent.this.image.getHeight();
                ReferenceImageComponent.this.setCoord(new Coord(
                        centreDimension(imgWidth, panePanClamp.width()),
                        centreDimension(imgHeight, panePanClamp.height())
                ));
                dragBehaviour.resetZoom();
            }

            private static int centreDimension(int boundaryDim, int compDim){
                return Math.abs(boundaryDim - compDim)/2;
            }

        };
    }


    @Override
    public void render(Graphics2D g){
        if(image == null) return;
        Composite old = g.getComposite();
        double zoom = dragBehaviour.getZoom();

        g.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        (float) alphaQuery.getPosition()
                )
        );

        g.drawImage(image,
                getCoord().x(),
                getCoord().y(),
                (int) (image.getWidth()*zoom),
                (int) (image.getHeight()*zoom),
                null
        );
        g.setComposite(old);
    }

    @Override
    public void tick(){}

    @Override
    public Size getSize(){
        if(image == null){
            return new Size(1, 1);
        }
        double scale = dragBehaviour.getZoom();
        return new Size((int) (image.getWidth()*scale), (int) (image.getHeight()*scale));
    }

    @Override
    public boolean withinBounds(Coord coord){
        return canGrab.isOn() && image != null && HitboxRect.super.withinBounds(coord);
    }

    @Override
    public void setCoord(Coord coord){
        this.coord = coord;
    }

    @Override
    public Coord getCoord(){
        return coord;
    }


    @Override
    public void setInputSource(GUIInputRegister<BoundedMouseInputListener> inputSource){
        dragBehaviour.setInputSource(inputSource);
    }

    @Override
    public void unsetInputSource(GUIInputRegister<BoundedMouseInputListener> inputSource){
        dragBehaviour.unsetInputSource(inputSource);
    }

}
