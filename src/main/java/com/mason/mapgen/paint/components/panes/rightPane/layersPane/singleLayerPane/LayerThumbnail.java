package com.mason.mapgen.paint.components.panes.rightPane.layersPane.singleLayerPane;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.RectQuery;

import java.awt.*;
import java.util.function.Consumer;

public class LayerThumbnail extends AbstractUIComponent implements BoundedMouseInputListener{


    private final Consumer<Graphics2D> renderable;
    private final Size renderableSize;
    private final Runnable updateCurrentLayerNum;


    public LayerThumbnail(
            RectQuery bounds,
            Consumer<Graphics2D> renderable,
            Size renderableSize,
            Runnable updateCurrentLayerNum
    ){
        super(HitboxRect.fromRect(bounds));
        this.renderable = renderable;
        this.renderableSize = renderableSize;
        this.updateCurrentLayerNum = updateCurrentLayerNum;
    }


    @Override
    public void render(Graphics2D g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x(), y());
        g2.scale(
                (double) width() / renderableSize.width(),
                (double) height() / renderableSize.height()
        );
        renderable.accept(g2);

        g2.dispose();
    }


    @Override
    public void onMouseClicked(MouseInputEvent e){
        updateCurrentLayerNum.run();
    }

}
