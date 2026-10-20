package com.mason.mapgen.paint.components.panes.rightPane.reference;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.position.PositionQuery;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ReferenceImageComponent extends AbstractUIComponent{


    private BufferedImage image;
    private final PositionQuery alphaQuery;


    public ReferenceImageComponent(RectQuery bounds,
                                   PositionQuery alphaQuery,
                                   ImageUpdate.ImageUpdateSetter imageUpdateSetter){
        super(bounds);
        this.alphaQuery = alphaQuery;
        imageUpdateSetter.setImageUpdate(new ImageUpdate(){
            @Override
            public void unsetImage(){
                ReferenceImageComponent.this.unsetImage();
            }

            @Override
            public void setImage(BufferedImage image){
                ReferenceImageComponent.this.setImage(image);
            }
        });
    }


    private void unsetImage(){
        image = null;
    }

    private void setImage(BufferedImage image){
        this.image = image;
    }


    @Override
    public void render(Graphics2D g){
        if(image == null) return;
        Composite old = g.getComposite();

        g.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        (float) alphaQuery.getPosition()
                )
        );

        g.drawImage(image, getCoord().x(), getCoord().y(), null);
        g.setComposite(old);
    }

    @Override
    public void tick(){}

}
