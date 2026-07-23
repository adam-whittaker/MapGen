package com.mason.mapgen.gui.components;

import com.mason.libgui.core.component.AbstractUIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.interfaces.RectQuery;

import java.awt.*;
import java.util.function.Consumer;

public class WorldUIComponent extends AbstractUIComponent{


    private final Consumer<Graphics2D> renderer;
    private final Runnable ticker;


    public WorldUIComponent(RectQuery box, Consumer<Graphics2D> renderer, Runnable ticker){
        super(HitboxRect.build(box.getCoord(), box.getSize()));
        this.renderer = renderer;
        this.ticker = ticker;
    }


    @Override
    public void render(Graphics2D g){
        renderer.accept(g);
    }

    @Override
    public void tick(){
        ticker.run();
    }

}
